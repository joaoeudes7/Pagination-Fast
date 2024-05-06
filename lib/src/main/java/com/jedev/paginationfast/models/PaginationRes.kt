package com.jedev.paginationfast.models

data class PaginationRes(
    val totalItems: Int = 0,
    val totalPages: Int = 0,
    val pageSize: Int = 0,
    val pageNumber: Int = 0,
)
