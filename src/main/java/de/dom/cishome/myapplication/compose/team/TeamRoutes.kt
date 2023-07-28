package de.dom.cishome.myapplication.compose.team

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import de.dom.cishome.myapplication.compose.team.model.Team
import de.dom.cishome.myapplication.compose.team.model.TeamRepository
import de.dom.cishome.myapplication.compose.team.model.TeamViewModel
import de.dom.cishome.myapplication.compose.team.pages.TeamDetailPage
import de.dom.cishome.myapplication.compose.team.pages.TeamDetailPlayerPage
import de.dom.cishome.myapplication.compose.team.pages.TeamWelcomePage
import java.lang.IllegalArgumentException

@ExperimentalUnitApi
@ExperimentalPermissionsApi
@ExperimentalMaterialApi
@ExperimentalMaterial3Api
fun NavGraphBuilder.teamGraph(navController: NavController){

    var viewModel = TeamViewModel( TeamRepository() );

    navigation( startDestination = "start" , route = "team"){
        composable("start"){
            TeamWelcomePage( it, viewModel , navController )
        }
        composable("team/detail"){
            TeamDetailPage( it , navController, viewModel.selected!!, viewModel )
        }
        composable("team/detail/player"){
            TeamDetailPlayerPage( navController.previousBackStackEntry!! , navController, viewModel )
        }


    }
}