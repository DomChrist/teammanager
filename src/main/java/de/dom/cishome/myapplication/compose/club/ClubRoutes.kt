package de.dom.cishome.myapplication.compose.club

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
import de.dom.cishome.myapplication.compose.club.model.ClubViewModel
import de.dom.cishome.myapplication.compose.club.pages.ClubMainPage
import de.dom.cishome.myapplication.compose.team.model.TeamRepository
import de.dom.cishome.myapplication.compose.team.model.TeamViewModel
import de.dom.cishome.myapplication.compose.team.pages.TeamDetailPage
import de.dom.cishome.myapplication.compose.team.pages.TeamWelcomePage
import de.dom.cishome.myapplication.tm.application.PlayerApplicationService
import de.dom.cishome.myapplication.tm.application.TeamApplicationService

@ExperimentalUnitApi
@ExperimentalPermissionsApi
@ExperimentalMaterialApi
@ExperimentalMaterial3Api
fun NavGraphBuilder.clubGraph(navController: NavController){
    var app = PlayerApplicationService.inject();
    var teamApp = TeamApplicationService();
    var viewModel = TeamViewModel( TeamRepository() );
    var club = ClubViewModel( teamApp );
    navigation( startDestination = "start" , route = "club"){
        composable("start"){
            ClubMainPage(club).Screen();
        }

    }
}