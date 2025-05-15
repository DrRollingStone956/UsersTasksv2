package com.example.userstask.ui.screens.userdetails

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.userstask.ui.components.TodoItem
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.userstask.UsersTaskApplication

@Composable
fun UserDetailsScreen(
    userId: Int
) {
    val context = LocalContext.current
    val repository = (context.applicationContext as UsersTaskApplication).container.postsRepository
    val viewModel: UserDetailsViewModel = viewModel(factory = UserDetailsViewModel.Factory(repository))
    val user by viewModel.user.collectAsState()
    val todos by viewModel.todos.collectAsState()
    val loading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(userId) {
        viewModel.loadUser(userId)
    }

    when{
        loading -> {
            CircularProgressIndicator()
        }
        error != null -> {
            Text("Błąd: $error")
        }
        user != null -> {
            LazyColumn(Modifier.padding(16.dp)){
                item{
                    Text(user!!.name, style = MaterialTheme.typography.headlineMedium)
                    Text("Username: ${user!!.username}")
                    Text("Email: ${user!!.email}")
                    Text("Phone: ${user!!.phone}")
                    Text("Website: ${user!!.website}")
                    Text("Company: ${user!!.company.name}")
                    Text("Address: ${user!!.address}")
                    Spacer(Modifier.height(16.dp))
                    Text("Todos:", style = MaterialTheme.typography.titleMedium)
                }
                items(todos){ todo ->
                    TodoItem(todo)
                }
            }
        }
    }
}