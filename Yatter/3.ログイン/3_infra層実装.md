# ログイン画面のinfra層実装
ログイン画面のinfra層実装を行います。

今回infra層で実装する内容は次の2つです。
- MePreferencesの実装
- LoginServiceの実装

Yatterアプリでは認証情報としてユーザー名を扱います。
ログインしているユーザーの`username`をアプリを実行しているデバイスに保存するために`MePreferences`を実装します。

`MePrefrences`では、内部実装として[`SharedPreferences`](https://developer.android.com/training/data-storage/shared-preferences?hl=ja)というものを利用します。
`SharedPreferences`とは、Key-Value形式でデバイス内にデータを保存するための仕組みです。
アプリを終了した後も残り続け、アプリデータ削除もしくはアンインストールしない限り残るためサーバーに保存しないようなアプリの設定を扱うために用いられます。

`MePreferences`クラスを定義します。
`SharedPreferences`ではPreferences名とKey-Value形式で扱うためのキー情報が必要なため固定値として持っておきます。

`SharedPreferences`のインスタンスは`Context`から取得できるため引数にも追加します。

```Kotlin
class MePreferences(context: Context) {
  companion object {
    private const val PREF_NAME = "me"
    private const val KEY_USERNAME = "username"
  }
}
```

実際にcontextからインスタンスを取得します。
`Context.MODE_PRIVATE`とというモード指定をしていますが現状はこのモードしかサポートされていないため特に気にする必要はありません。

```Kotlin
class MePreferences(context: Context) {
  ...
  private val sharedPreferences = context.getSharedPreferences(
    PREF_NAME,
    Context.MODE_PRIVATE
  )
}
```

SharedPreferencesに値を書き込み・取り出し・データ削除するためのメソッドを実装します。

値を取り出すときは、`getString`のように`get~`なメソッド呼び出しだけで問題ありませんが、書き込みやデータ削除の場合は最後に`apply()`する必要があるためお気を付けてください。

```Kotlin
class MePreferences(...) {
  ...
  fun getUsername(): String? = sharedPreferences.getString(
    KEY_USERNAME,
    ""
  )

  fun putUserName(username: String?) {
    sharedPreferences.edit().putString(
      KEY_USERNAME,
      username
    ).apply()
  }

  fun clear() = sharedPreferences.edit().clear().apply()

}
```

ログインユーザーのユーザー名を記録する`MePreferences`を実装したため、`LoginService`の実装を進めます。

まずは、`LoginServiceImpl`ファイルの作成です。

```Kotlin
package com.dmm.bootcamp.yatter2023.infra.domain.service

class LoginServiceImpl(
  private val mePreferences: MePreferences
) : LoginService {
  override suspend fun execute(
    username: Username,
    password: Password,
  ) {
    TODO("Not yet implemented")
  }
}
```

DomainServiceは純粋な振る舞いを定義しますので、`LoginService`では渡されたユーザー名とパスワードをもとにログイン処理を行うことに注力して、ユーザー名とパスワードの値が問題ないかという部分は呼び出し元に移譲します。

現状のYatterではユーザー名とパスワードが登録済みの情報と一致しているかは確認しておりません。
そのため、`LoginServiceImpl`の処理としてもアプリ終了後でも再ログインが不要にするためにユーザー名を`MePreferences`に保存するのみにします。

今後、API接続してさらに厳格なログイン処理を行う場合は`LoginServiceImpl`の処理を変更してください。

```Kotlin
mePreferences.putUserName(username.value)
```

ここまでで`MePreferences`と`LoginService`とが属するinfra層の実装は完了しました。

続いてusecase層の実装に移ります。
