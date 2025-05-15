package com.example.userstask.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.userstask.UsersTaskApplication
import com.example.userstask.ui.screens.postdetails.PostDetailsScreen
import com.example.userstask.ui.screens.tasks.TasksListViewModel
import com.example.userstask.ui.screens.tasks.TasksScreen
import com.example.userstask.ui.screens.userdetails.UserDetailsScreen

sealed class Screen(val route: String) {
    object Tasks : Screen("TasksScreen")
    object PostDetail : Screen("post_detail/{postId}") {
        fun createRoute(postId: Int) = "post_detail/$postId"
    }
    object UserDetail : Screen("user_detail/{userId}") {
        fun createRoute(userId: Int) = "user_detail/$userId"
    }
}

@Composable
fun NavDestination() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Tasks.route) {
        composable(Screen.Tasks.route) {
            val context = LocalContext.current
            val application = context.applicationContext as UsersTaskApplication
            val repository = application.container.postsRepository
            val viewModel = remember { TasksListViewModel(repository) }

            TasksScreen(
                onPostClick = { postId ->
                    navController.navigate(Screen.PostDetail.createRoute(postId))
                },
                onUserClick = { userId ->
                    navController.navigate(Screen.UserDetail.createRoute(userId))
                },
            )
        }

        composable(
            route = Screen.PostDetail.route,
            arguments = listOf(navArgument("postId") { type = NavType.IntType })
        ) { backStackEntry ->
            val postId = backStackEntry.arguments?.getInt("postId")
            if (postId != null) {
                PostDetailsScreen(postId = postId)
            }
        }

        composable(
            route = Screen.UserDetail.route,
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId")
            if (userId != null) {
                UserDetailsScreen(userId = userId)
            }
        }
    }
}
