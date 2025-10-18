package com.example.s8140457_assignment2.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.s8140457_assignment2.data.Result
import com.example.s8140457_assignment2.data.repository.DashboardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repo: DashboardRepository
) : ViewModel() {

    private val _keypass = MutableLiveData<String?>()
    val keypass: LiveData<String?> = _keypass

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun login(username: String, password: String) {
        viewModelScope.launch {
            when (val res = repo.login(username.trim(), password.trim())) {
                is Result.Success -> _keypass.value = res.data.keypass
                is Result.Error   -> _error.value = res.message
            }
        }
    }
}
