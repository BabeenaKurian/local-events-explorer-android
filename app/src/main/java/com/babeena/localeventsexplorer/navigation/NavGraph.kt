
package com.babeena.localeventsexplorerandroidapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.babeena.localeventsexplorer.ui.events.EventViewModel
import com.babeena.localeventsexplorer.ui.events.eventScreen
import com.babeena.localeventsexplorerandroidapp.ui.bookmarks.BookmarksScreen
import com.babeena.localeventsexplorerandroidapp.ui.bookmarks.BookmarksViewModel
import com.babeena.localeventsexplorerandroidapp.ui.details.EventDetailsScreen
import com.babeena.localeventsexplorerandroidapp.ui.details.EventDetailsViewModel

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "events"
    ) {
        composable("events") {
            val viewModel: EventViewModel = hiltViewModel()

            eventScreen(
                viewModel = viewModel,
                onEventClick = { eventId ->
                    navController.navigate("eventDetails/$eventId")
                },
                onBookmarksClick = {
                    navController.navigate("bookmarks")
                }
            )
        }


        composable(
            route = "eventDetails/{eventId}",
            arguments = listOf(
                navArgument("eventId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->

            val eventId = backStackEntry.arguments?.getString("eventId")

            val viewModel: EventDetailsViewModel = hiltViewModel()

            if (eventId != null) {
                LaunchedEffect(eventId) {
                    viewModel.loadEventDetails(eventId)
                }

                EventDetailsScreen(
                    viewModel = viewModel,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
        }

        composable("bookmarks") {
            val viewModel: BookmarksViewModel = hiltViewModel()

            BookmarksScreen(
                viewModel = viewModel,
                onEventClick = { eventId ->
                    navController.navigate("eventDetails/$eventId")
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
