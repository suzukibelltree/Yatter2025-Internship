# [前の資料](./1_domain層実装.md)
# ツイート画面のinfra層実装
ツイート画面のinfra層実装を行います。
infra層では、domain層実装時に定義した`GetLoginUserService`と`GetLoginUsernameService`、`UserRepository`、`YweetRepository`の実装を行います。

まずは`UserRepository`の実装です。  
必要な引数と`impl`クラスの定義を`infra/domain/repository`に追加します。  

```Kotlin
class UserRepositoryImpl(
  private val yatterApi: YatterApi,
  private val getLoginUsernameService: GetLoginUsernameService,
) : UserRepository {
}
```
必要なメソッドをオーバーライドします。  

```Kotlin
class UserRepositoryImpl(
  private val yatterApi: YatterApi,
  private val getLoginUsernameService: GetLoginUsernameService,
) : UserRepository {
  override suspend fun findLoginUser(disableCache: Boolean): User? {
    TODO("Not yet implemented")
  }

  override suspend fun findByUsername(username: Username, disableCache: Boolean): User? {
    TODO("Not yet implemented")
  }

  override suspend fun create(username: Username, password: Password): User {
    TODO("Not yet implemented")
  }

  override suspend fun update(
    me: User,
    newDisplayName: String?,
    newNote: String?,
    newAvatar: URL?,
    newHeader: URL?
  ): User {
    TODO("Not yet implemented")
  }

  override suspend fun followings(): List<User> {
    TODO("Not yet implemented")
  }

  override suspend fun followers(): List<User> {
    TODO("Not yet implemented")
  }

  override suspend fun follow(me: User, username: Username) {
    TODO("Not yet implemented")
  }

  override suspend fun unfollow(me: User, username: Username) {
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

APIの定義ができたらツイート機能に必要な`findLoginUser`,`findByUsername`をまずは実装します。  
処理は次の手順を実装します。  

- getLoginUsernameServiceからログイン済みユーザー情報取得
  - ユーザー情報取得できなければ取得不可(null)
- ユーザー名からAPIを経由してアカウント情報取得
- `User`ドメインへ変換

コードは次のようになります。  

```Kotlin
  override suspend fun findLoginUser(disableCache: Boolean): User? = withContext(Dispatchers.IO) {
    val username = getLoginUsernameService.execute() ?: return@withContext null
    findByUsername(username = username, disableCache = disableCache)
  }

  override suspend fun findByUsername(
    username: Username,
    disableCache: Boolean,
  ): User? = withContext(Dispatchers.IO) {
    if (!disableCache) {
      userCache[username]?.let {
        return@withContext it
      }
    }
    try {
      val userJson = yatterApi.getUserByUsername(username = username.value)
      val user = UserConverter.convertToDomainModel(userJson)
      userCache[username] = user
      return@withContext user
    } catch (e: HttpException) {
      Log.d("UserRepositoryImpl", "HTTP error: ${e.code()} message:${e.message()}")
      null
    } catch (e: Exception) {
      Log.d("UserRepositoryImpl", "Error: ${e.message}")
      null
    }
  }
```

ここで、`userCache`という変数が出てきました。
これは、過去に取得したことのあるユーザー情報をキャッシュしておくためのMapです。  
キャッシュを利用することで、APIの呼び出し回数を減らすことができ、アプリのパフォーマンス向上や通信量の削減に役立ちます。

キャッシュの実装は次のように行います。
```Kotlin
class UserRepositoryImpl(
  private val yatterApi: YatterApi,
  private val loginUserPreferences: LoginUserPreferences,
) : UserRepository {
  private val userCache: MutableMap<Username, User> = mutableMapOf()
  ...
}
```



残りのメソッドは今回の機能では利用しないため、ひとまずTODOのままにしておきます。  
実装したメソッドには単体テストを書いてみましょう。  


<details>
<summary>UserRepositoryImplのテスト実装例</summary>

```Kotlin
class UserRepositoryImplSpec {
  private val yatterApi = mockk<YatterApi>()
  private val getLoginUsernameService = mockk<GetLoginUsernameService>()
  private val subject = UserRepositoryImpl(yatterApi, getLoginUsernameService)

  @Test
  fun findByUsername() = runTest {
    val username = Username("username")
    val userJson = UserJson(
      id = "id",
      username = "username",
      displayName = "display name",
      note = null,
      avatar = "https://www.google.com",
      header = "https://www.google.com",
      followingCount = 0,
      followersCount = 0,
      createdAt = ""
    )

    val expect = UserConverter.convertToDomainModel(userJson)

    coEvery {
      yatterApi.getUserByUsername(any())
    } returns userJson

    val result = subject.findByUsername(username, disableCache = false)

    coVerify {
      yatterApi.getUserByUsername(username.value)
    }

    assertThat(result).isEqualTo(expect)
  }

  @Test
  fun findLoginUser() = runTest {
    val username = "username"
    val userJson = UserJson(
      id = "id",
      username = "username",
      displayName = "display name",
      note = null,
      avatar = "https://www.google.com",
      header = "https://www.google.com",
      followingCount = 0,
      followersCount = 0,
      createdAt = ""
    )
    val expect = UserConverter.convertToDomainModel(userJson)

    coEvery {
      getLoginUsernameService.execute()
    } returns Username(username)
    coEvery {
      yatterApi.getUserByUsername(any())
    } returns userJson

    val result = subject.findLoginUser(disableCache = false)

    coVerify {
      yatterApi.getUserByUsername(username)
    }
    coVerify {
      getLoginUsernameService.execute()
    }

    assertThat(result).isEqualTo(expect)
  }
}
```

</details>

---

続いて`GetLoginUserService`の実装です。

`infra/domain/service`に`GetLoginUserServiceImpl`クラスを定義します。  
`UserRepository`を介して`User`クラスを取得するため引数に`UserRepository`も追加しておきます。  

```Kotlin
class GetLoginUserServiceImpl(
  private val userRepository: UserRepository,
) : GetLoginUserService {
  suspend fun execute(): User? {
    TODO("Not yet implemented")
  }
}
```

`UserRepository`を利用して`User`ドメインを取得します。  

```Kotlin
override suspend fun execute(): User? = withContext(Dispatchers.IO) {
  userRepository.findLoginUser(disableCache = false)
}
```

こちらもテストを実装して動作の担保をします。  

<details>
<summary>GetLoginUserServiceImplSpecのテスト実装例</summary>

```Kotlin
class GetLoginUserServiceImplSpec {
  private val userRepository = mockk<UserRepository>()
  private val subject = GetLoginUserServiceImpl(userRepository)

  @Test
  fun getLoginUser() {
    val user = User(
      id = UserId(value = ""),
      username = Username(value = ""),
      displayName = null,
      note = null,
      avatar = URL("https://www.google.com"),
      header = URL("https://www.google.com"),
      followingCount = 0,
      followerCount = 0,
    )

    coEvery { userRepository.findLoginUser(disableCache = any()) } returns user

    val result = runBlocking { subject.execute() }

    assertThat(result).isEqualTo(user)
  }
}
```

</details>

---

次は`GetLoginUsernameService`の実装です。
`infra/domain/service`に`GetLoginUsernameServiceImpl`クラスを定義します。
```Kotlin
class GetLoginUsernameServiceImpl(
  private val loginUserPreferences: LoginUserPreferences,
) : GetLoginUsernameService {
  override fun execute(): Username? = TODO("Not yet implemented")T
}
```

`LoginUserPreferences`を利用して、ログイン済みのユーザー名を取得します。

```Kotlin
class GetLoginUsernameServiceImpl(
  private val loginUserPreferences: LoginUserPreferences,
) : GetLoginUsernameService {
  override fun execute(): Username? = loginUserPreferences.getUsername()?.let { Username(it) }
}
```

こちらもテストを実装して動作の担保をします。
<details>
<summary>GetLoginUsernameServiceImplSpecのテスト実装例</summary>

```Kotlin
class GetLoginUsernameServiceImplSpec {
  private val loginUserPreferences = mockk<LoginUserPreferences>()
  private val subject = GetLoginUsernameServiceImpl(loginUserPreferences)

  @Test
  fun getLoginUsername() {
    val username = "username"

    every { loginUserPreferences.getUsername() } returns username

    val result = subject.execute()

    assertThat(result).isEqualTo(Username(value = username))
  }

  @Test
  fun getLoginUsernameNull() {
    every { loginUserPreferences.getUsername() } returns null

    val result = subject.execute()

    assertThat(result).isNull()
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
  val content: String,
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

ログイン済みか確認する方法として、`LoginUserPreferences.getUsername()`の実行時にユーザー名が返るかどうかで判定します。  
`getUsername()`がnull(=ログインしていない)であれば`AuthenticatorException`をスローします。  

`LoginUserPreferences`を利用するには`YweetRepositoryImpl`の引数に追加してあげる必要があるので、まずは引数の追加から行います。  

```Kotlin
class YweetRepositoryImpl(
  private val yatterApi: YatterApi,
  private val loginUserPreferences: LoginUserPreferences,
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
  val username = loginUserPreferences.getUsername() ?: throw AuthenticatorException()
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
package com.dmm.bootcamp.yatter2025.auth

interface TokenProvider {
  suspend fun provide(): String
}
```

利用方法  
```Kotlin
class YweetRepositoryImpl(
  private val yatterApi: YatterApi,
  private val tokenProvider: TokenProvider,
  private val loginUserPreferences: LoginUserPreferences,
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
class TokenProviderImpl(private val tokenPreferences: TokenPreferences) : TokenProvider {
  override suspend fun provide(): String {
    return tokenPreferences.getAccessToken()?.let { "username $it" } ?: throw AuthenticatorException()
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
  private val tokenPreferences = mockk<TokenPreferences>()
  private val subject = TokenProviderImpl(tokenPreferences)

  @Test
  fun getTokenSuccess() = runTest {
    val username = "username"

    val expect = "username $username"

    coEvery {
      tokenPreferences.getAccessToken()
    } returns username

    val result = subject.provide()

    coVerify {
      tokenPreferences.getAccessToken()
    }

    assertThat(result).isEqualTo(expect)
  }

  @Test
  fun getTokenFailure() = runTest {
    coEvery {
      tokenPreferences.getAccessToken()
    } returns null

    var error: Throwable? = null
    var result: String? = null

    try {
      result = subject.provide()
    } catch (e: Exception) {
      error = e
    }

    coVerify {
      tokenPreferences.getAccessToken()
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
class YweetRepositoryImplSpec {
  private val yatterApi = mockk<YatterApi>()
  private val tokenPreferences = mockk<TokenPreferences>()
  private val tokenProvider: TokenProvider = TokenProviderImpl(tokenPreferences)
  private val loginUserPreferences = mockk<LoginUserPreferences>()
  private val subject = YweetRepositoryImpl(yatterApi, tokenProvider, loginUserPreferences)
  
  ...
  
  @Test
  fun postYweetWhenLoggedIn() = runTest {
    val loginUsername = "token"
    val content = "content"
    val token = "username $loginUsername"

  val yweetJson = YweetJson(
      id = "id",
      user = UserJson(
        id = "id",
        username = loginUsername,
        displayName = "",
        note = null,
        avatar = "https://www.google.com",
        header = "https://www.google.com",
        followingCount = 0,
        followersCount = 0,
      createdAt = ""
      ),
      content = content,
    createdAt = ""
      attachmentMediaList = emptyList(),
    )

    coEvery {
      tokenPreferences.getAccessToken()
    } returns loginUsername

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
      tokenPreferences.getAccessToken()
      yatterApi.postYweet(
        token,
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
      loginUserPreferences.getUsername()
    } returns username
    coEvery {
      tokenProvider.provide()
    } throws AuthenticatorException()


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
      tokenProvider.provide()
    }
  }
}
```

</details>

---

テストが通ることまで確認できたらinfra層の実装は完了です。  

# [次の資料](./3_usecase層実装.md)