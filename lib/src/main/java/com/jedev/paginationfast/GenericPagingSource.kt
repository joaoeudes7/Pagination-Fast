package com.jedev.paginationfast

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jedev.lib.PaginationItems
import com.jedev.lib.PaginationReq
import com.jedev.lib.PaginationRes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class GenericPagingSource<T : Any>(
    private val itemPerPage: Int = 5,
    private val enablePlaceholders: Boolean = false,
    private val defaultFirstPage: Int = 1,
    private val onFetchData: suspend (PaginationReq) -> PaginationItems<T>,
) : PagingSource<Int, T>() {

    private var pagingInfo = MutableStateFlow<PaginationRes?>(null)

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        try {
            val pageIndex = params.key ?: defaultFirstPage

            val response = onFetchData(
                PaginationReq(
                    page = pageIndex,
                    itemsPerPage = itemPerPage
                )
            )

            val items = response.items
            val headerPagination = response.pagination
            val totalPages = headerPagination.totalPages

            pagingInfo.value = PaginationRes(
                totalItems = headerPagination.totalItems,
                totalPages = totalPages,
            )

            return LoadResult.Page(
                data = items,
                prevKey = null,
                nextKey = if (items.isEmpty() || pageIndex == totalPages) null else pageIndex + 1
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, T>): Int = 1

    fun getPagingDataFlow() = generatePagingDataFlow(
        enablePlaceholders = enablePlaceholders
    )

    fun getPaginationInfo() = pagingInfo.asStateFlow()

    private fun generatePagingDataFlow(
        enablePlaceholders: Boolean = true,
    ) = generatePagingData(
        pageSizeItems = itemPerPage,
        enablePlaceholders = enablePlaceholders
    ).flow

    private fun <T : Any, G : Any> PagingSource<T, G>.generatePagingData(
        pageSizeItems: Int,
        enablePlaceholders: Boolean = true,
    ): Pager<T, G> {
        val pagingConfig = PagingConfig(
            pageSize = pageSizeItems,
            enablePlaceholders = enablePlaceholders
        )

        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { this }
        )
    }
}
