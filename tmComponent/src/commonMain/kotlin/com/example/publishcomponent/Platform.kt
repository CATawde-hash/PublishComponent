package com.example.publishcomponent

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform