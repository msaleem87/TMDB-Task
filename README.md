# TMDB Task
A simple project using [The Movie DB](https://www.themoviedb.org) based on Kotlin MVVM architecture and material designs & animations.<br>

## Tech stack & Open-source libraries
- Minimum SDK level 16
- 100% [Kotlin](https://kotlinlang.org/) based + [Coroutines](https://github.com/Kotlin/kotlinx.coroutines)
- JetPack
  - LiveData - notify domain layer data to views.
  - Lifecycle - dispose observing data when lifecycle state changes.
  - ViewModel - UI related data holder, lifecycle aware.
  - Room Persistence - construct database.
- Architecture
  - MVVM Architecture (View - DataBinding - ViewModel - Model)
  - Repository pattern
  - [Koin](https://github.com/InsertKoinIO/koin) - dependency injection
- Material Design & Animations
- [Retrofit2 & Gson](https://github.com/square/retrofit) - constructing the REST API
- [OkHttp3](https://github.com/square/okhttp) - implementing interceptor, logging and mocking web server
- [Sandwich] - constructing lightweight API response and handling error responses
- [Glide](https://github.com/bumptech/glide) - loading images
- [BaseRecyclerViewAdapter] - implementing adapters and viewHolders
- [WhatIf] -checking nullable object and empty collections more fluently
- [Mockito-kotlin](https://github.com/nhaarman/mockito-kotlin) - Junit mock test
- [Timber](https://github.com/JakeWharton/timber) - logging
- [Stetho](https://github.com/facebook/stetho) - debugging persistence data & network packets
- Ripple animation, Shared element transition
- Custom Views [AndroidTagView](https://github.com/whilu/AndroidTagView), [ExpandableTextView](https://github.com/Manabu-GT/ExpandableTextView)