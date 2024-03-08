package com.example.carfinder.reponse

data class ResponseError(
    val status: Status,
)

data class Status(
    val code: Long,
    val message: String,
)