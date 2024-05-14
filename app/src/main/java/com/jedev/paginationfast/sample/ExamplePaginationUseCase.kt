package com.jedev.paginationfast.sample

import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jedev.lib.PaginationItems
import com.jedev.lib.PaginationRes
import com.jedev.paginationfast.GenericPagingSource
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

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