package com.example.s8140457_assignment2.data.repository

import com.example.s8140457_assignment2.data.Result
import com.example.s8140457_assignment2.data.remote.ApiService
import com.example.s8140457_assignment2.data.remote.DashboardResponse
import com.example.s8140457_assignment2.data.remote.LoginRequest
import com.example.s8140457_assignment2.data.remote.LoginResponse
import javax.inject.Inject

class DashboardRepositoryImpl @Inject constructor(
    private val api: ApiService
) : DashboardRepository {

    override suspend fun login(username: String, password: String): Result<LoginResponse> =
        try {
            val res = api.login(LoginRequest(username, password))
            if (res.isSuccessful && res.body() != null) {
                Result.Success(res.body()!!)
            } else {
                Result.Error("Login failed: ${res.code()}")
            }
        } catch (e: Exception) {
            Result.Error(e.localizedMessage ?: "Network error")
        }

    override suspend fun getDashboard(keypass: String): Result<DashboardResponse> =
        try {
            val res = api.getDashboard(keypass)
            if (res.isSuccessful && res.body() != null) {
                Result.Success(res.body()!!)
            } else {
                Result.Error("Fetch failed: ${res.code()}")
            }
        } catch (e: Exception) {
            Result.Error(e.localizedMessage ?: "Network error")
        }
}
