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
        implementation 'com.github.joaoeudes7:PaginationFast:Tag'
    }
    ```

   Replace `YourUsername` with your GitHub username and `Tag` with the version tag of the library you want to use.

3. Sync your project to fetch the new dependency.

## Usage

To use PaginationFast in your app, follow these steps:

1. Create a `PagingSource` by extending `PaginationFast.GenericPagingSource` and implement the `onFetchData` lambda function to fetch data from your backend.

    ```kotlin
    val pagingSource = PaginationFast.GenericPagingSource<MyData>(
        pageSizeItems = 10,
        enablePlaceholders = false
    ) { paginationReq ->
        // Fetch data from your backend using paginationReq
        // Return PaginatedItemsModel containing the fetched data
    }
    ```

2. Generate a `Flow` of `PagingData` using the `generatePagingDataFlow` extension function.

    ```kotlin
    val pagingDataFlow = pagingSource.generatePagingDataFlow(
        pageSizeItems = 10,
        enablePlaceholders = false
    )
    ```

3. Use the generated `Flow` to observe the paginated data in your UI.

    ```kotlin
    lifecycleScope.launch {
        pagingDataFlow.collectLatest { pagingData ->
            // Update your UI with the paginated data
        }
    }
    ```

For more detailed usage instructions and examples, refer to the [Wiki](https://github.com/joaoeudes7/PaginationFast/wiki).

## License

This library is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Contributing

Contributions are welcome! Feel free to open an issue or submit a pull request for any improvements or bug fixes.

## Acknowledgements

PaginationFast is inspired by [Paging 3 library](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) and aims to provide a simplified and generic approach to pagination in Android apps.
