package com.jedev.lib

import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems

val <T : Any> LazyPagingItems<T>.isLoading
    get() = loadState.append is LoadState.Loading || loadState.refresh is LoadState.Loading