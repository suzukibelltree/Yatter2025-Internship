# [前の資料](./4_DI実装.md)
# ViewModelのテスト実装
ツイート画面で利用しているViewModelのテストを書いてみましょう。

`PostViewModel`のテストを実装します。  
今回のテストは次の観点を確認します。  

- onCreate時にユーザー情報が取得できていること
- テキスト入力するとUiStateが更新されること
- 投稿ボタン押下で投稿完了すること
- 投稿ボタン押下でエラー発生時に何も発生しないこと
- ナビゲーションの戻るボタン押下時に戻る用の値が流れていること

実際にテストを書いてみて、テストコード例も載せていますので見比べながら動作を確認しましょう。

<details>
<summary>PostViewModelのテストコード例</summary>

```Kotlin
class PostViewModelSpec {
  private val getLoginUserService = mockk<GetLoginUserService>()
  private val postYweetUseCase = mockk<PostYweetUseCase>()
  private val subject = PostViewModel(
    postYweetUseCase,
    getLoginUserService,
  )

  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  @get:Rule
  val rule: TestRule = InstantTaskExecutorRule()

  @Test
  fun getMeWhenOnCreate() = runTest {
    val avatarUrl = URL("https://www.dmm.com")
    val me = User(
      id = UserId(value = "me user"),
      username = Username(value = ""),
      displayName = null,
      note = null,
      avatar = avatarUrl,
      header = URL("https://www.google.com"),
      followingCount = 0,
      followerCount = 0
    )
    coEvery {
      getLoginUserService.execute()
    } returns me

    subject.onCreate()

    assertThat(subject.uiState.value.bindingModel.avatarUrl).isEqualTo(avatarUrl.toString())
  }


  @Test
  fun changeYweetAndCanPost() = runTest {
    val newYweetText = "new"

    subject.onChangedYweetText(newYweetText)

    assertThat(subject.uiState.value.bindingModel.yweetText).isEqualTo(newYweetText)
    assertThat(subject.uiState.value.canPost).isTrue()
  }

  @Test
  fun changeYweetAndCannotPost() = runTest {
    val oldYweetText = "old"
    val newYweetText = ""

    subject.onChangedYweetText(oldYweetText)
    assertThat(subject.uiState.value.bindingModel.yweetText).isEqualTo(oldYweetText)
    assertThat(subject.uiState.value.canPost).isTrue()

    subject.onChangedYweetText(newYweetText)

    assertThat(subject.uiState.value.bindingModel.yweetText).isEqualTo(newYweetText)
    assertThat(subject.uiState.value.canPost).isFalse()
  }

  @Test
  fun postSuccess() = runTest {
    val yweet = "yweet"
    subject.onChangedYweetText(yweet)

    coEvery {
      postYweetUseCase.execute(any(), any())
    } returns PostYweetUseCaseResult.Success

    subject.onClickPost()

    coVerify {
      postYweetUseCase.execute(yweet, emptyList())
    }

    assertThat(subject.destination.value).isInstanceOf(PopBackDestination::class.java)
  }

  @Test
  fun postFailure() = runTest {
    val yweet = "yweet"
    subject.onChangedYweetText(yweet)

    coEvery {
      postYweetUseCase.execute(any(), any())
    } returns PostYweetUseCaseResult.Failure.OtherError(Exception())

    subject.onClickPost()

    coVerify {
      postYweetUseCase.execute(yweet, emptyList())
    }

    assertThat(subject.destination.value).isNull()
  }

  @Test
  fun clickBack() = runTest {
    subject.onClickNavIcon()

    assertThat(subject.destination.value).isInstanceOf(PopBackDestination::class.java)
  }
}
```

</details>

# [次の章へ](../../5.その次は/1_その次は.md)
