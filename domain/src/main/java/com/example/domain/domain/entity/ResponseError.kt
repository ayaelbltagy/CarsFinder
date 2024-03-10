package com.example.carfinder.domain

data class ResponseError(
    val status: Status,
)

data class Status(
    val code: Long,
    val message: String,
)