package de.dom.cishome.myapplication.compose.team

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
import de.dom.cishome.myapplication.compose.team.model.TeamRepository
import de.dom.cishome.myapplication.compose.team.model.TeamViewModel
import de.dom.cishome.myapplication.compose.team.pages.TeamDetailPage
import de.dom.cishome.myapplication.compose.team.pages.TeamWelcomePage
import de.dom.cishome.myapplication.tm.application.services.PlayerApplicationService

@ExperimentalUnitApi
@ExperimentalPermissionsApi
@ExperimentalMaterialApi
@ExperimentalMaterial3Api
fun NavGraphBuilder.teamGraph(navController: NavController){
    var app = PlayerApplicationService.inject();
    var viewModel = TeamViewModel( TeamRepository() );

    navigation( startDestination = "start" , route = "team"){
        composable("start"){
            TeamWelcomePage( it, viewModel , navController )
        }
        composable("start"){
            TeamWelcomePage( it, viewModel , navController )
        }
        composable("team/detail"){
            TeamDetailPage( it , navController, viewModel.selected!!, viewModel )
        }
        composable("team/{team}/detail/player" ,  arguments = listOf( navArgument("team"){type= NavType.StringType})  ){
            var id = navController.currentBackStackEntry?.arguments?.getString("team");
            var list = app.repo.players().filter { it.team == null || it.team.equals(id,true) }

            //PlayerOverviewPage( list , PlayerOverviewClick( {} , {navController.navigateUp()} , { navController.navigate("player/detail/${it.id}") }) )
        }


    }
}