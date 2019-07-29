# AndroidSongApp

A sample app that implements a **clean architecture** pattern
 1. `repository` layer to get data from network and from local file. 
It uses **`Retrofit`** and **`Kotlin Coroutines`**. To reduce number of network calls a simple cashing was introduced.
(Note: there is a limitation that the whole file is loaded to memory every time).

2. `domain` layer with one use case.

3. `presentation` layer as mvvm with **`Architecture Components`** , e.g. **`LiveData`**.

There are also some **unit tests** for every layer. Dependency injection done with **`Koin`**
