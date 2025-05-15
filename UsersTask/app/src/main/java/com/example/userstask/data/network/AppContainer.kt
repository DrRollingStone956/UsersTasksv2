package com.example.userstask.data.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val postsRepository: DefaultPostsRepository
}

class DefaultAppContainer : AppContainer {

    private val apiBaseUrl = "https://jsonplaceholder.typicode.com/"

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    private val tasksService: PostsService by lazy {
        Retrofit.Builder()
            .baseUrl(apiBaseUrl)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(PostsService::class.java)
    }

    override val postsRepository: DefaultPostsRepository by lazy {
        DefaultPostsRepository(tasksService)
    }
}
