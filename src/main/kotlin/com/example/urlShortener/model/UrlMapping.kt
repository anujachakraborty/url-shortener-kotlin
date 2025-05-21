package com.example.urlShortener.model

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class UrlMapping(
    val id: String,
    val originalUrl: String
)
