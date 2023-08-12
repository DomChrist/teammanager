package de.dom.cishome.myapplication.tm.adapter.`in`.compose.player

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
import de.dom.cishome.myapplication.compose.player.pages.PlayerContactPersonPage
import de.dom.cishome.myapplication.compose.player.pages.PlayerInfoPage
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.player.model.PlayerViewModel
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.player.pages.AddPlayerScreen
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.player.pages.PlayerDetailClick
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.player.pages.PlayerDetailPage
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.player.pages.PlayerListFilter
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.player.pages.PlayerOverviewPage
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.shared.TmViewModel

@ExperimentalUnitApi
@ExperimentalPermissionsApi
@ExperimentalMaterialApi
@ExperimentalMaterial3Api
fun NavGraphBuilder.playerGraph(navController: NavController, tm: TmViewModel){



    navigation( startDestination = "start" , route = "player"){

        composable("start" , listOf( navArgument("team"){defaultValue=""; type = NavType.StringType} )){
            var team = navController.currentBackStackEntry?.arguments?.getString("team") ?: "";
            var filter = if( team.isNotBlank() ){
                PlayerListFilter.byTeam(team)
            } else {
                PlayerListFilter.none()
            }
            PlayerOverviewPage().Screen( filter=filter, clicks = PlayerOverviewPage.PlayerOverviewClick.clicks(navController) )
        }

        composable("players?team={team}" , listOf( navArgument("team"){defaultValue=""; type = NavType.StringType} )){
            var team = navController.currentBackStackEntry?.arguments?.getString("team") ?: "";
            var filter = if( team.isNotBlank() ){
                PlayerListFilter.byTeam(team)
            } else {
                PlayerListFilter.none()
            }
            PlayerOverviewPage().Screen( filter=filter, clicks = PlayerOverviewPage.PlayerOverviewClick.clicks(navController) )
        }

        /*
        composable("player/add"){
            AddPlayerScreen(onPlayerAdded = {
                                            tm.playerViewModel.handle(it)
                                            navController.navigate("player")
                                            } , onBackClick = {navController.navigateUp()})
        }
        */

        composable("player/detail/{id}" , arguments = listOf( navArgument("id"){type= NavType.StringType} )){
            var id = navController.currentBackStackEntry?.arguments?.getString("id");
            val clicks = PlayerDetailClick( {navController.navigateUp()} , {navController.navigate(it)} )
            PlayerDetailPage().Screen( id!! , clicks );
        }
        composable("player/detail/{id}/info" , arguments = listOf( navArgument("id"){type= NavType.StringType} )){
            PlayerInfoPage( navController )
        }
        composable("player/detail/{id}/contacts" , arguments = listOf( navArgument("id"){type= NavType.StringType} )){
            PlayerContactPersonPage( navController )
        }

        composable("player/trial/detail/{id}" ){
            //TrialPlayerDetailPage(navBackStackEntry = it, playerService=playerService , navController = navController)
        }

    }
}