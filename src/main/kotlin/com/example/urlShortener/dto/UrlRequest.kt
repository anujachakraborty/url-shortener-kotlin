package com.example.urlShortener.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class UrlRequest(
    @field:NotBlank(message = "URL cannot be blank")
    @field:Pattern(
        regexp = "https?://.*",
        message = "URL must start with http:// or https://"
    )
    val url: String
)
