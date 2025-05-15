package com.example.userstask.ui.screens.tasks

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.userstask.UsersTaskApplication
import com.example.userstask.ui.components.PostItem

@Composable
fun TasksScreen(
    onPostClick: (Int) -> Unit,
    onUserClick: (Int) -> Unit
) {
    val context = LocalContext.current
    val repository = (context.applicationContext as UsersTaskApplication).container.postsRepository
    val viewModel: TasksListViewModel = viewModel(factory = TasksListViewModel.Factory(repository))
    val posts by viewModel.tasks.collectAsState()
    val loading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    when {
        loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        error != null -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error: $error")
            }
        }
        else -> {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(posts) { post ->
                    PostItem(
                        post = post,
                        onPostClick = { onPostClick(post.postId) },
                        onUserClick = { onUserClick(post.userId) }
                    )
                }
            }
        }
    }
}