package com.jedev.paginationfast

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jedev.paginationfast.models.PaginatedItemsModel
import com.jedev.paginationfast.models.PaginationReq
import kotlinx.coroutines.flow.Flow

object PaginationFast {
        class GenericPagingSource<T : Any>(
            private val pageSizeItems: Int = 5,
            private val enablePlaceholders: Boolean = false,
            private val onFetchData: suspend (PaginationReq) -> PaginatedItemsModel<T>,
        ) : PagingSource<Int, T>() {

            companion object {
                const val DEFAULT_PAGE_INDEX = 1
            }

            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
                try {
                    val pageIndex = params.key ?: DEFAULT_PAGE_INDEX

                    val response = onFetchData(
                        PaginationReq(
                            page = pageIndex,
                            itemsPerPage = pageSizeItems
                        )
                    )

                    val items = response.items
                    val headerPagination = response.pagination
                    val totalPages = headerPagination.totalPages

                    return LoadResult.Page(
                        data = items,
                        prevKey = null,
                        nextKey = if (items.isEmpty() || pageIndex == totalPages) null else pageIndex + 1
                    )
                } catch (e: Exception) {
                    return LoadResult.Error(e)
                }
            }

            override fun getRefreshKey(state: PagingState<Int, T>): Int = 1

            fun getPagingDataFlow() = generatePagingDataFlow(
                pageSizeItems = pageSizeItems,
                enablePlaceholders = enablePlaceholders
            )
        }

        fun <T : Any, G : Any> PagingSource<T, G>.generatePagingDataFlow(
            pageSizeItems: Int,
            enablePlaceholders: Boolean = true,
        ): Flow<PagingData<G>> {
            val pagingConfig = PagingConfig(
                pageSize = pageSizeItems,
                enablePlaceholders = enablePlaceholders
            )

            return Pager(
                config = pagingConfig,
                pagingSourceFactory = { this }
            ).flow
        }
    }