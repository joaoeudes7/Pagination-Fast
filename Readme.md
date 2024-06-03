# PaginationFast

PaginationFast is a utility library for Android that provides a fast and generic solution for pagination using the Paging 3 library. It simplifies the process of implementing pagination in your Android app by abstracting away the complexity of managing data loading and pagination.

## Features

- **Generic Pagination**: Easily paginate any type of data fetched from a remote data source.
- **Customizable Configuration**: Configure page size and enable/disable placeholders according to your requirements.
- **Simple Integration**: Integrate pagination functionality into your app with just a few lines of code.

## Getting Started

To integrate PaginationFast into your Android project, follow these steps:

1. Add the JitPack repository to your project's `build.gradle` file:

    ```groovy
    allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }
    ```

2. Add the dependency to your app's `build.gradle` file:

    ```groovy
    dependencies {
        implementation("com.github.joaoeudes7:Pagination-Fast:Tag")
    }
    ```

   Replace `YourUsername` with your GitHub username and `Tag` with the version tag of the library you want to use.

3. Sync your project to fetch the new dependency.

## Usage

To use PaginationFast in your app, follow these steps:

1. Create a `PagingSource` by `GenericPagingSource` and implement the `onFetchData` lambda function to fetch data from your backend.

    ```kotlin
         @Suppress("MemberVisibilityCanBePrivate", "unused")
         class ViewModelExample(
            private val useCase: ExamplePaginationUseCase
         ) : ViewModel() {
            var itemsPagination by mutableStateOf(flowOf(PagingData.empty<Any>()))
            private set
            
             fun fetchData() = viewModelScope.launch {
                 useCase(search = null)
                     .catch {
                         // handle exception
                     }
                     .collect {
                         itemsPagination = it.cachedIn(viewModelScope)
                     }
             }
         }
         
         @Suppress("unused")
         class ExamplePaginationUseCase {
            @VisibleForTesting
            fun getPagingSource(search: String?) = GenericPagingSource(
               enablePlaceholders = false,
               itemPerPage = 20,
               defaultFirstPage = 1,
               onFetchData = { fakeFetchFromRepository(search, it.page, it.itemsPerPage) }
            )
            
             operator fun invoke(search: String?) = flow {
                 val pagingSource = getPagingSource(search)
         
                 emit(pagingSource.getPagingDataFlow())
             }
         }
            
         @Suppress("UNUSED_PARAMETER")
         fun fakeFetchFromRepository(search: String?, pag: Int, itemsPerPage: Int): PaginationItems<Any> {
            return PaginationItems(
               items = listOf(),
               pagination = PaginationRes(
                  totalItems = 1,
                  totalPages = 1
                )
            )
         }
    ```
## License

This library is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Contributing

Contributions are welcome! Feel free to open an issue or submit a pull request for any improvements or bug fixes.

## Acknowledgements

PaginationFast is inspired by [Paging 3 library](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) and aims to provide a simplified and generic approach to pagination in Android apps.
