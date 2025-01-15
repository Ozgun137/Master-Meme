package com.example.mastermeme

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.mastermeme.presentation.memeEditor.MemeEditorScreenRoot
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
            MemeListScreenRoot(
                onMemeSelected = {
                    navController.navigate(MemeEditor(it))
                }
            )
        }

        composable<MemeEditor> {
            val args = it.toRoute<MemeEditor>()
            MemeEditorScreenRoot(
                memeUri = args.memeUri,
                onLeaveEditor = {
                    navController.navigateUp()
                }
            )
        }
    }
}

@Serializable
object Memes

@Serializable
object MemeList

@Serializable
data class MemeEditor(val memeUri: String)