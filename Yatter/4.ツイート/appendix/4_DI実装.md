# [前の資料](./3_usecase層実装.md)
# ツイート画面のDI実装
ツイート画面のDI実装をして、これまで実装した各クラスのつなぎ込みを行い、ツイート画面を動作させるようにします。  

ツイート画面用に新規作成したDI実装を行う対象は次になります。  

- `GetLoginUserService`
- `UserRepository`
- `PostYweetUseCase`
- `PostViewModel`

それぞれ的したモジュールにインスタンス化方法を定義します。  

```Kotlin
// DomainImplModule
internal val domainImplModule = module {
  single<UserRepository> { UserRepositoryImpl(get(), get()) }
  ...

  factory<GetLoginUserService> { GetLoginUserServiceImpl(get()) }
}

// UseCaseModule
internal val useCaseModule = module {
  factory<PostYweetUseCase> { PostYweetUseCaseImpl(get()) }
}

// ViewModelModule
internal val viewModelModule = module {
  viewModel { PostViewModel(get(), get()) }
}
```

これでツイート画面実装は完了になります。  

次にツイート画面までの導線を実装します。

# [次の資料](./5_ViewModelのテスト実装.md)
