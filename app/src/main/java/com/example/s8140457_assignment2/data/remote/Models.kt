package com.example.s8140457_assignment2.data.remote

data class LoginRequest(
    val username: String,
    val password: String
)

data class LoginResponse(
    val keypass: String
)

data class Entity(
    val artworkTitle: String?,
    val artist: String?,
    val medium: String?,
    val year: Int?,
    val description: String?
)

data class DashboardResponse(
    val entities: List<Entity>,
    val entityTotal: Int? = null
)
