package com.jedev.paginationfast.models

data class PaginatedItemsModel<T>(
    val items: List<T>,
    val pagination: PaginationRes
)
