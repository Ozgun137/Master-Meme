package com.example.mastermeme

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.mastermeme.presentation.memeList.MemeListScreenRoot
import kotlinx.serialization.Serializable

@Composable
fun NavigationRoot(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Memes
    ) {
        memeListGraph(navController)
    }

}

private fun NavGraphBuilder.memeListGraph(
    navController: NavHostController
) {
    navigation<Memes>(startDestination = MemeList) {
        composable<MemeList> {
            MemeListScreenRoot()
        }
    }
}

@Serializable
object Memes

@Serializable
object MemeList