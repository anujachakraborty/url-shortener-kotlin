package com.example.urlShortener.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import org.slf4j.LoggerFactory

@Service
class UrlService(@Autowired val redisTemplate: StringRedisTemplate) {

    val logger = LoggerFactory.getLogger(UrlService::class.java)

    private val valueOps = redisTemplate.opsForValue()
    private val counterKey = "url_counter"

    fun shortenUrl(originalUrl: String): String {
        val id = redisTemplate.opsForValue().increment(counterKey) ?: 1L
        val shortId = encodeBase62(id)
        logger.info("Shortened the URL: $originalUrl to base-62 encoded $shortId")
        valueOps.set(shortId, originalUrl)
        return shortId
    }

    fun getOriginalUrl(shortId: String): String? {
        return valueOps.get(shortId)
    }

    private fun encodeBase62(num: Long): String {
        val chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
        var n = num
        val sb = StringBuilder()
        while (n > 0) {
            sb.append(chars[(n % 62).toInt()])
            n /= 62
        }
        return sb.reverse().toString()
    }
}
