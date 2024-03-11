package com.example.domain.domain.entity


data class ModelResponse(
    val id: Int,
    val model: Int,
    val plate_number: String,
    val brand: String,
    val unit_price: Double,
    val currency: String,
    val color: String,
)
