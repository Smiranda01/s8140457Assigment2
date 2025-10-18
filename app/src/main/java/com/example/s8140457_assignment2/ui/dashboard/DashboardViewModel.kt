package com.example.s8140457_assignment2.ui.dashboard

import androidx.lifecycle.*
import com.example.s8140457_assignment2.data.Result
import com.example.s8140457_assignment2.data.remote.Entity
import com.example.s8140457_assignment2.data.repository.DashboardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repo: DashboardRepository
) : ViewModel() {

    private val _items = MutableLiveData<List<Entity>>(emptyList())
    val items: LiveData<List<Entity>> = _items

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun load(keypass: String) {
        viewModelScope.launch {
            when (val res = repo.getDashboard(keypass)) {
                is Result.Success -> _items.value = res.data.entities
                is Result.Error   -> _error.value = res.message
            }
        }
    }
}
