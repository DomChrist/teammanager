package de.dom.cishome.myapplication

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import de.dom.cishome.myapplication.compose.player.pages.NewPlayerPage
import de.dom.cishome.myapplication.compose.player.pages.PlayerContactPersonPage
import de.dom.cishome.myapplication.compose.player.pages.PlayerDetailPage
import de.dom.cishome.myapplication.compose.player.pages.PlayerInfoPage
import de.dom.cishome.myapplication.compose.player.pages.PlayerWelcomePage
import de.dom.cishome.myapplication.compose.player.pages.TrialPlayerDetailPage
import de.dom.cishome.myapplication.compose.player.service.PlayerService
import de.dom.cishome.myapplication.compose.turnier.page.CompetitionDetailPage
import de.dom.cishome.myapplication.compose.turnier.page.TurnierWelcomePage


@ExperimentalUnitApi
@ExperimentalPermissionsApi
@ExperimentalMaterialApi
@ExperimentalMaterial3Api
fun NavGraphBuilder.playerGraph( navController: NavController, playerService: PlayerService ){
    navigation( startDestination = "start" , route = "player"){
        composable("start"){
            PlayerWelcomePage( playerService, navController)
        }
        composable("player/add"){
            NewPlayerPage( playerService , navController)
        }
        composable("player/detail/{id}" , arguments = listOf( navArgument("id"){type= NavType.StringType} )){
            PlayerDetailPage( it, playerService, navController )
        }
        composable("player/detail/{id}/info" , arguments = listOf( navArgument("id"){type= NavType.StringType} )){
            PlayerInfoPage( navController )
        }
        composable("player/detail/{id}/contacts" , arguments = listOf( navArgument("id"){type= NavType.StringType} )){
            PlayerContactPersonPage( navController )
        }

        composable("player/trial/detail/{id}" ){
            TrialPlayerDetailPage(navBackStackEntry = it, playerService=playerService , navController = navController)
        }

    }
}

@ExperimentalUnitApi
@ExperimentalPermissionsApi
@ExperimentalMaterialApi
@ExperimentalMaterial3Api
fun NavGraphBuilder.competitionGraph( navController: NavController, playerService: PlayerService ){
    navigation( startDestination = "start" , route = "competition"){
        composable("start"){
            TurnierWelcomePage( navController )
        }
        composable("competition/detail/{id}"){
            CompetitionDetailPage( navController )
        }


    }
}