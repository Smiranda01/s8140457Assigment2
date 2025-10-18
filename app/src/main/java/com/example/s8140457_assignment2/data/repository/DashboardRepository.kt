package com.example.s8140457_assignment2.data.repository

import com.example.s8140457_assignment2.data.Result
import com.example.s8140457_assignment2.data.remote.DashboardResponse
import com.example.s8140457_assignment2.data.remote.LoginResponse

interface DashboardRepository {
    suspend fun login(username: String, password: String): Result<LoginResponse>
    suspend fun getDashboard(keypass: String): Result<DashboardResponse>
}
