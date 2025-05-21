package com.example.urlShortener.controller

import com.example.urlShortener.service.UrlService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class UrlController(@Autowired val urlService: UrlService) {

    @PostMapping("/shorten")
    fun shortenUrl(@RequestBody body: Map<String, String>): ResponseEntity<String> {
        val url = body["url"] ?: return ResponseEntity.badRequest().body("Missing URL")
        val hash = urlService.shortenUrl(url)
        return ResponseEntity.ok("https://short.ly/$hash")
    }

    @GetMapping("/{hash}")
    fun resolveUrl(@PathVariable hash: String): ResponseEntity<Any> {
        val url = urlService.getOriginalUrl(hash)
        return if (url != null)
            ResponseEntity.ok(mapOf("url" to url))
        else
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("URL not found")
    }
}
