# ツイート画面のdomain層実装

ツイート画面実装に必要なdomain層実装を行います。  

まずはQuery部分から実装します。  

必要なドメインモデルとして、ログインしているユーザーの情報を扱う`Me`ドメインも実装します。  
`Me`ドメインの実態は`User`ドメインと同じ値を保持しますが、自身のユーザーであるという情報を扱いやすくするために、`User`ドメインを継承して定義します。  

```Kotlin
package com.dmm.bootcamp.yatter2024.domain

abstract class Me(
  id: UserId,
  username: Username,
  displayName: String?,
  note: String?,
  avatar: URL,
  header: URL,
  followingCount: Int,
  followerCount: Int,
) : User(
  id,
  username,
  displayName,
  note,
  avatar,
  header,
  followingCount,
  followerCount,
) {

  abstract suspend fun follow(username: Username)

  abstract suspend fun unfollow(username: Username)
}

```

続いてDomainServiceの実装を行います。  
必要なDomainServiceは`GetMeService`です。  
このDomainServiceを利用して、ログイン済みのアカウント情報を取得し、ツイート画面のアイコン表示やトークン取得に活用します。  

```Kotlin
package com.dmm.bootcamp.yatter2024.domain.service

interface GetMeService {
  suspend fun execute(): Me?
}
```

最後にRepositoryの実装を行います。  

ユーザー情報を保持するための`UserRepository`を`domin/repository`に追加します。  

`UserRepository`でユーザーの検索や追加、更新といったユーザー周りの制御・操作を行えるようにします。  

```Kotlin
interface UserRepository {
  suspend fun findMe(): Me?

  suspend fun findByUsername(username: Username): User?

  suspend fun create(
    username: Username,
    password: Password
  ): Me

  suspend fun update(
    me: Me,
    newDisplayName: String?,
    newNote: String?,
    newAvatar: URL?,
    newHeader: URL?
  ): Me
}
```

---

続いて、Command部分の実装です。  

ツイート処理は`YweetRepository`にメソッドを追加して実装します。  

ツイート処理専用の`DomainService`を作ることも可能ですが、Yweetを`YweetRepository`で集約することによりキャッシュを有効活用したりRepositoryパターンの概念としても合っていたりするため、`YweetRepository#create`で実施します。  

`YweetRepository`インターフェースを確認し、`create`メソッドが定義されていない場合は次のメソッドを追加します。  
`Yweet`を投稿(=新規作成)するため、`create`というメソッド名にしています。  

```Kotlin
interface YweetRepository {
  ...
  suspend fun create(
    yweet: String,
    attachmentList: List<File>,
  ): Yweet
}
```

これにてツイート機能に必要なdomain層の実装は完了です。  

# [次の資料](./2_infra層実装.md)