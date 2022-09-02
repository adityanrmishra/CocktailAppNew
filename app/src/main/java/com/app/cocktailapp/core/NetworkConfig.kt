package com.app.cocktailapp.core

object NetworkConfig {
        const val BASE_URL = "https://www.thecocktaildb.com"

        const val CONNECTION_TIMEOUT = 10L
        const val READ_TIMEOUT = 10L
        const val WRITE_TIMEOUT = 10L
        const val CACHE_SIZE_BYTES = 10 * 1024 * 1024L // 10 MB
        const val CACHE_DIRECTORY = "HttpCache"

        const val CACHE_HEADER_PRAGMA = "Pragma"
        const val CACHE_HEADER_EXPIRES = "Expires"
        const val CACHE_HEADER_CACHE_CONTROL = "Cache-Control"

        const val DRINKS_FILTER = "/api/json/v1/1/list.php?c=list"
        const val DRINKS_CATEGORY_WISE = "/api/json/v1/1/filter.php"
        const val DRINKS_BY_ID = "/api/json/v1/1/lookup.php"

        const val UNKNOWN_ERROR = "An Unknown error occurred"
}