package com.example.carfinder.reponse


data class ModelResponse(
    val model: Int,
    val plate_number: String,
    val brand: String,
    val unit_price: String,
    val currency: String,
    val color: String,
)