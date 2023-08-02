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
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.player.pages.PlayerDetailPage
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.player.pages.PlayerOverviewClick
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.player.pages.PlayerOverviewPage
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.shared.TmViewModel

@ExperimentalUnitApi
@ExperimentalPermissionsApi
@ExperimentalMaterialApi
@ExperimentalMaterial3Api
fun NavGraphBuilder.playerGraph(navController: NavController, tm: TmViewModel){



    navigation( startDestination = "start" , route = "player"){
        composable("start"){
            PlayerOverviewPage(model = tm.playerApp.repo.players(), clicks = PlayerOverviewClick.clicks(navController) )
        }

        composable("player/add"){
            AddPlayerScreen(onPlayerAdded = {
                                            tm.playerViewModel.handle(it)
                                            navController.navigate("player")
                                            } , onBackClick = {navController.navigateUp()})
        }
        composable("player/detail/{id}" , arguments = listOf( navArgument("id"){type= NavType.StringType} )){
            var id = navController.currentBackStackEntry?.arguments?.getString("id");
            if( id != null ){
                val player = tm.playerViewModel.player(id)
                if( player != null ){
                    var items = PlayerViewModel.navItemsBy(player, navController);
                    PlayerDetailPage(player, items = items) { navController.navigateUp() }
                }
            }
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