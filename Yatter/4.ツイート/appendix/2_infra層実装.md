# [前の資料](./1_domain層実装.md)
# ツイート画面のinfra層実装
ツイート画面のinfra層実装を行います。
infra層では、domain層実装時に定義した`GetMeService`と`UserRepository`、`YweetRepository`の実装を行います。

まずは、`Me`の実装から始めます。
`infra/domina`に`MeImpl`クラスを作成し実装します。  
基本的には`UserImpl`と同じような内容になります。  

```Kotlin
class MeImpl(
  id: UserId,
  username: Username,
  displayName: String?,
  note: String?,
  avatar: URL,
  header: URL,
  followingCount: Int,
  followerCount: Int,
) : Me(
  id = id,
  username = username,
  displayName = displayName,
  note = note,
  avatar = avatar,
  header = header,
  followingCount = followingCount,
  followerCount = followerCount,
) {
  override suspend fun follow(username: Username) {
    TODO("Not yet implemented")
  }

  override suspend fun unfollow(username: Username) {
    TODO("Not yet implemented")
  }

  override suspend fun followings(): List<User> {
    TODO("Not yet implemented")
  }

  override suspend fun followers(): List<User> {
    TODO("Not yet implemented")
  }
}
```

`MeImpl`が実装できたところでコンバーターも実装します。  
`UserConverter`と同じディレクトリに`MeConverter`を定義します。  

```Kotlin
object MeConverter {
  fun convertToMe(user: User): Me {
    return MeImpl(
      id = user.id,
      username = user.username,
      displayName = user.displayName,
      note = user.note,
      avatar = user.avatar,
      header = user.header,
      followingCount = user.followingCount,
      followerCount = user.followerCount,
    )
  }
}
```

続いては`UserRepository`の実装です。  
必要な引数と`impl`クラスの定義を`infra/domain/repository`に追加します。  

```Kotlin
class UserRepositoryImpl(
  private val yatterApi: YatterApi,
  private val mePreferences: MePreferences
) : UserRepository {
}
```
必要なメソッドをオーバーライドします。  

```Kotlin
class UserRepositoryImpl(
  private val yatterApi: YatterApi,
  private val mePreferences: MePreferences
) : UserRepository {
  override suspend fun findMe(): Me? {
    TODO("Not yet implemented")
  }

  override suspend fun findByUsername(username: Username): User? {
    TODO("Not yet implemented")
  }

  override suspend fun create(username: Username, password: Password): Me {
    TODO("Not yet implemented")
  }

  override suspend fun update(
    me: Me,
    newDisplayName: String?,
    newNote: String?,
    newAvatar: URL?,
    newHeader: URL?
  ): Me {
    TODO("Not yet implemented")
  }
}
```

`UserRepositoryImpl`では、ユーザー名をもとにAPIからユーザー情報を取得します。  

そのためまずは、`YatterApi`の定義から始めます。  
`getUserByUsername`を追加してユーザー名からユーザー情報を取得できるようにします。  

```Kotlin
@GET("users/{username}")
suspend fun getUserByUsername(
  @Path("username") username: String
): UserJson
```

APIの定義ができたらツイート機能に必要な`findMe`と`findByUsername`をまずは実装します。  
処理は次の手順を実装します。  

- mePreferencesからログイン済みユーザー情報取得
  - ユーザー情報取得できなければ取得不可(null)
- ユーザー名からAPIを経由してアカウント情報取得
- `Me`ドメインへ変換

コードは次のようになります。  

```Kotlin
override suspend fun findMe(): Me? = withContext(Dispatchers.IO) {
  val username = mePreferences.getUsername() ?: return@withContext null
  if (username.isEmpty()) return@withContext null

  val user = findByUsername(username = Username(username)) ?: return@withContext null
  MeConverter.convertToMe(user)
}

override suspend fun findByUsername(username: Username): User? = withContext(Dispatchers.IO) {
  val userJson = yatterApi.getUserByUsername(username = username.value)
  UserConverter.convertToDomainModel(userJson)
}
```

残りのメソッドは今回の機能では利用しないため、ひとまずTODOのままにしておきます。  
実装したメソッドには単体テストを書いてみましょう。  


<details>
<summary>UserRepositoryImplのテスト実装例</summary>

```Kotlin
class UserRepositoryImplSpec {
  val yatterApi = mockk<YatterApi>()
  val mePreferences = mockk<MePreferences>()
  val subject = UserRepositoryImpl(yatterApi, mePreferences)

  @Test
  fun getUserByUsername() = runTest {
    val username = Username("username")
    val userJson = UserJson(
      id = "id",
      username = "username",
      displayName = "display name",
      note = null,
      avatar = "",
      header = "",
      followingCount = 0,
      followersCount = 0,
      createdAt = ""
    )

    val expect = UserConverter.convertToDomainModel(userJson)

    coEvery {
      yatterApi.getUserByUsername(any())
    } returns userJson

    val result = subject.findByUsername(username)

    coVerify {
      yatterApi.getUserByUsername(username.value)
    }

    assertThat(result).isEqualTo(expect)
  }

  @Test
  fun getMe() = runTest {
    val username = "username"
    val userJson = UserJson(
      id = "id",
      username = "username",
      displayName = "display name",
      note = null,
      avatar = "",
      header = "",
      followingCount = 0,
      followersCount = 0,
      createdAt = ""
    )

    val expect = UserConverter.convertToDomainModel(userJson)

    coEvery {
      yatterApi.getUserByUsername(any())
    } returns userJson

    coEvery {
      mePreferences.getUsername()
    } returns username

    val result = subject.findMe()

    coVerify {
      yatterApi.getUserByUsername(username)
    }

    coVerify {
      mePreferences.getUsername()
    }

    assertThat(result).isEqualTo(expect)
  }
}
```

</details>

---

続いて`GetMeService`の実装です。

`infra/domain/service`に`GetMeServiceImpl`クラスを定義します。  
`UserRepository`を介して`Me`クラスを取得するため引数に`UserRepository`も追加しておきます。  

```Kotlin
class GetMeServiceImpl(
  private val userRepository: UserRepository,
) : GetMeService {
  suspend fun execute(): Me? {
    TODO("Not yet implemented")
  }
}
```

`UserRepository`を利用して`Me`ドメインを取得します。  

```Kotlin
override suspend fun execute(): Me? = withContext(Dispatchers.IO) {
  userRepository.findMe()
}
```

こちらもテストを実装して動作の担保をします。  

<details>
<summary>GetMeServiceImplSpecのテスト実装例</summary>

```Kotlin
class GetMeServiceImplSpec {
  private val userRepository = mockk<UserRepository>()
  private val subject = GetMeServiceImpl(userRepository)

  @Test
  fun getMe() {
    val user = UserImpl(
      id = UserId(value = ""),
      username = Username(value = ""),
      displayName = null,
      note = null,
      avatar = URL("https://www.google.com"),
      header = URL("https://www.google.com"),
      followingCount = 0,
      followerCount = 0
    )
    val expect = MeConverter.convertToMe(user)

    coEvery { userRepository.findMe() } returns MeConverter.convertToMe(user)

    val result = runBlocking { subject.execute() }

    assertThat(result).isEqualTo(expect)
  }
}
```

</details>

---

`YweetRepository`の実装です。  
`YweetRepository`でのツイート処理はAPI処理が必要になるため、まずはYatterApiの定義から始めます。  

APIの定義を確認するとツイート用のAPIを実行する際にはBodyJsonが必要となっていますので、`PostYweetJson`クラスを定義します。  
今回の実装ではテキストのみのツイート機能ですが後々に画像の投稿も行うため、先に定義しておきます。  

```Kotlin
data class PostYweetJson(
  val yweet: String,
  @Json(name = "image_ids") val imageIds: List<Int>
)
```

Jsonクラスが定義できたら`YatterApi`の定義です。  
ツイート用の`postYweet`を追加します。  
BodyJsonを利用する場合は`@Body`を使用します。  

```Kotlin
@POST("yweets")
suspend fun postYweet(
  @Header("Authentication") token: String,
  @Body yweetJson: PostYweetJson
): YweetJson
```

APIの定義ができたらRepositoryの実装に戻ります。  
既に`YweetRepositoryImpl`クラスは定義されているため、クラスを開くだけです。  

`create`メソッドでは次の処理を実施します。  

- ログイン済みか確認
  - ログイン済みでなかったら例外スロー
- APIを通じて投稿
  - 画像ファイルの投稿は今は考えない
- 投稿完了したらYweetを返す

ログイン済みか確認する方法として、`MePreferences.getUsername()`の実行時にユーザー名が返るかどうかで判定します。  
`getUsername()`がnull(=ログインしていない)であれば`AuthenticatorException`をスローします。  

`MePreferences`を利用するには`YweetRepositoryImpl`の引数に追加してあげる必要があるのでまずは引数の追加から行います。  

```Kotlin
class YweetRepositoryImpl(
  private val yatterApi: YatterApi,
  private val mePreferences: MePreferences,
) : YweetRepository {...}
```

YweetRepositoryImplの引数を増やしたら、DI側の設定も更新が必要になります。  
`DomainImplModule`を開き、`YweetRepositoryImpl`のインスタンス化箇所にも引数追加します。　　

```Kotlin
val domainImplModule = module {
  single<YweetRepository> { YweetRepositoryImpl(get(), get()) } // 引数2つ目にもget()を追加
}
```

引数に`get()`を追加することで対応したインスタンスをDIが解決して引数に渡してくれます。  

引数の準備ができたら実際に`create`の実装に入ります。  
Yweetの投稿APIでは、`username ${ユーザー名}`という形式で値をHeaderに追加する必要があるためそれ用のメソッドも`private`で用意します。  

```Kotlin
override suspend fun create(
  content: String,
  attachmentList: List<File>
): Yweet = withContext(IO) {
  val username = mePreferences.getUsername() ?: throw AuthenticatorException()
  val yweetJson = yatterApi.postYweet(
    getToken(username),
    PostYweetJson(
      content,
      emptyList()
    )
  )
  YweetConverter.convertToDomainModel(yweetJson)
}

private fun getToken(username: String) = "username $username"
```

<details>
<summary>トークン生成箇所の追加課題(スキップ可)</summary>

今回の`YweetRepostiroyImpl#create`実装では、`getToken`というメソッドを用意して、`username ${ユーザー名}`という形式の文字列を生成するように実装しました。  

このままでも動作自体は問題ないのですが、コードや設計的に懸念が残ります。  
それは、他の箇所でも同様のトークンが必要になる場合があるからです。  

同じ`YweetRepositoryImpl`内であればこの`getToken`を使いまわせますが、`UserRepositoryImpl`などの他のクラスで必要になった場合はまた実装が必要になってきます。  

その解決法方法として`TokenProvider`が作成されることがあります。  
他のクラスでもトークンが必要になったり手が空いたりしている方は実装に挑戦してみましょう。(とりあえず最後まで実装したい人はスキップしても問題ありません)  

実装例も記載しますがまずは自分で挑戦してみて回答を見たり参考にしたりしてください。  

---

`TokenProvider`は認証に関わる値ですのでそれ用のパッケージ`auth`を作成してその中に作成するとよいでしょう。  

ヒントとして、interfaceと利用方法を記載しておきますので、`TokenProviderImpl`の実装方法やDIの設定、テストなどは各自で書いてみましょう。  
`TokenProviderImpl`は`auth/impl`パッケージ内で作成してみましょう。  
詰まったりさらにヒントが欲しかったりしたら講師に聞いたりチームで相談したりして進めてください。  

interface定義  
```Kotlin
package com.dmm.bootcamp.yatter2024.auth

interface TokenProvider {
  suspend fun provide(): String
}
```

利用方法  
```Kotlin
class YweetRepositoryImpl(
  private val yatterApi: YatterApi,
  private val tokenProvider: TokenProvider,
) : YweetRepository {
  ...
  override suspend fun create(
    content: String,
    attachmentList: List<File>
  ): Yweet = withContext(IO) {
    val yweetJson = yatterApi.postYweet(
      tokenProvider.provide(),
      PostYweetJson(
        content,
        listOf()
      )
    )
    YweetConverter.convertToDomainModel(yweetJson)
  }
  ...
}
```

<details>
<summary>TokenProviderImpl実装例</summary>

auth/impl/TokenProviderImpl.kt  

```Kotlin
class TokenProviderImpl(private val getMeService: GetMeService) : TokenProvider {
  override suspend fun provide(): String {
    val me = getMeService.execute()
    return me?.username?.value?.let { "username $it" } ?: throw AuthenticatorException()
  }
}
```

di/InfraModule.kt  

```Kotlin
internal val infraModule = module {
  ...
  factory<TokenProvider> { TokenProviderImpl(get()) }
}
```

TokenProviderImplのテスト

```Kotlin
class TokenProviderImplSpec {
  private val getMeService = mockk<GetMeService>()
  private val subject = TokenProviderImpl(getMeService)

  @Test
  fun getTokenSuccess() = runTest {
    val username = "username"
    val me = MeImpl(
      id = UserId(value = "id"),
      username = Username(value = username),
      displayName = null,
      note = null,
      avatar = URL("https:www.google.com"),
      header = URL("https:www.google.com"),
      followingCount = 0,
      followerCount = 0
    )
    
    val expect = "username $username"

    coEvery {
      getMeService.execute()
    } returns me
    
    val result = subject.provide()
    
    coVerify { 
      getMeService.execute()
    }
    
    assertThat(result).isEqualTo(expect)
  }
  
  @Test
  fun getTokenFailure() = runTest { 
    coEvery { 
      getMeService.execute()
    } returns null
    
    var error: Throwable? = null 
    var result: String? = null
    
    try {
      result = subject.provide()
    } catch (e: Exception) {
      error = e
    }

    coVerify {
      getMeService.execute()
    }
    
    assertThat(result).isNull()
    assertThat(error).isInstanceOf(AuthenticatorException::class.java)
  }
}
```

</details>

</details>

---

`create`メソッドが実装できたら単体テストを実施しましょう。  
次に示す項目のテストを書いてみてください。  

- ログイン済み時に呼び出すと投稿に成功する
- 未ログイン時に実行すると例外が発生する

<details>
<summary>YweetRepositoryImpl#createのテスト実装例</summary>

```Kotlin
@Test
fun postYweetWhenLoggedIn() = runTest {
  val username = "username"
  val content = "content"

  val yweetJson = YweetJson(
    id = "id",
    user = UserJson(
      id = "id",
      username = username,
      displayName = "",
      note = null,
      avatar = "",
      header = "",
      followingCount = 0,
      followersCount = 0,
      createdAt = ""
    ),
    content = content,
    createdAt = ""

  )

  coEvery {
    mePreferences.getUsername()
  } returns username

  coEvery {
    yatterApi.postYweet(any(), any())
  } returns yweetJson

  val expect = YweetConverter.convertToDomainModel(yweetJson)

  val result = subject.create(
    content,
    emptyList()
  )

  assertThat(result).isEqualTo(expect)

  coVerifyAll {
    mePreferences.getUsername()
    yatterApi.postYweet(
      username,
      PostYweetJson(
        content = content,
        imageIds = emptyList()
      )
    )
  }
}

@Test
fun postYweetWhenNotLoggedIn() = runTest {
  val username = null
  val content = "content"

  coEvery {
    mePreferences.getUsername()
  } returns username


  var error: Throwable? = null
  var result: Yweet? = null

  try {
    result = subject.create(
      content,
      emptyList()
    )
  } catch (e: Exception) {
    error = e
  }


  assertThat(result).isNull()
  assertThat(error).isInstanceOf(AuthenticatorException::class.java)

  coVerify {
    mePreferences.getUsername()
  }
}
```

</details>

---

テストが通ることまで確認できたらinfra層の実装は完了です。  

# [次の資料](./3_usecase層実装.md)