# AndroidSongApp

A sample app that implements a **clean architecure** pattern
 1. `repository` layer to get data from network and from local file (Note: there is a limitation that the whole file is loaded to memeory every time).
To reduce nuber of network calls a simple cashing was introduced.
It uses **`Retrofit`** and **`Kotlin Coroutines`**.

2. `domain` layer with one use case.

3. `presentation` layer as mvvm with **`Architecure Components`** , e.g. **`LiveData`**.

There are also some **unit tests** for every layer. Dependency injection done with **`Koin`**
