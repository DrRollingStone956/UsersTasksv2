package com.example.userstask.ui.screens.tasks


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.userstask.data.network.PostsRepository
import com.example.userstask.data.model.Post
import com.example.userstask.data.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class TasksListViewModel(
    private val postsRepository: PostsRepository
) : ViewModel() {

    private val _tasks = MutableStateFlow<List<Post>>(emptyList())
    val tasks: StateFlow<List<Post>> = _tasks

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        loadTasks()
    }
    private fun loadTasks() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _tasks.value = postsRepository.getPosts()
            }
            catch (e: Exception) {
                _error.value = e.message
            }
            finally {
                _isLoading.value = false
            }

        }
    }
    class Factory(
        private val postsRepository: PostsRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun<T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TasksListViewModel::class.java)) {
                return TasksListViewModel(postsRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")

        }
    }
}