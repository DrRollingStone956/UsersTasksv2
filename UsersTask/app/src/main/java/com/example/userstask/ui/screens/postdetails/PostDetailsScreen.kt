package com.example.userstask.ui.screens.postdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.userstask.UsersTaskApplication

@Composable
fun PostDetailsScreen(
    postId: Int
){
    val context = LocalContext.current
    val repository = (context.applicationContext as UsersTaskApplication).container.postsRepository
    val viewModel: PostDetailsViewModel = viewModel(factory = PostDetailsViewModel.Factory(repository))
    val post by viewModel.post.collectAsState()
    val loading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(postId) {
        viewModel.loadPost(postId)
    }

    when{
        loading -> {
            CircularProgressIndicator()
        }
        error != null -> {
            Text("Error: $error")
        }
        post != null -> {
            Column(Modifier.padding(16.dp)){
                Text(post!!.title, style= MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(16.dp))
                Text(post!!.body)
                Spacer(modifier = Modifier.height(16.dp))
                Text("AuthorID: ${post!!.userId}")
            }
        }
    }
}