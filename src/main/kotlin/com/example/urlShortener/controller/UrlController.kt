package com.example.urlShortener.controller

import com.example.urlShortener.dto.UrlRequest
import com.example.urlShortener.model.UrlMapping
import com.example.urlShortener.service.UrlService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.slf4j.LoggerFactory

@RestController
@RequestMapping("/api")
@Validated
class UrlController(@Autowired val urlService: UrlService) {

    val logger = LoggerFactory.getLogger(UrlController::class.java)

    @PostMapping("/shorten")
    fun shortenUrl(@Valid @RequestBody urlRequest: UrlRequest): ResponseEntity<String> {
        //val url = body["url"] ?: return ResponseEntity.badRequest().body("Missing URL")
        val hash = urlService.shortenUrl(urlRequest.url)
        logger.info("Hash value of the corresponding url: $hash")
        return ResponseEntity.ok("https://short.ly/$hash")
    }

    @GetMapping("/{hash}")
    fun resolveUrl(@PathVariable hash: String): ResponseEntity<Any> {
        val url = urlService.getOriginalUrl(hash)
        logger.debug("Fetching original URL for hash: $hash")
        return if (url != null)
            ResponseEntity.ok(mapOf("url" to url))
        else
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("URL not found")
    }
}
