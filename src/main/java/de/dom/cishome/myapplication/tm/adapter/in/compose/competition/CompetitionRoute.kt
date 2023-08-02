package de.dom.cishome.myapplication.tm.adapter.`in`.compose.competition

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import de.dom.cishome.myapplication.compose.player.service.PlayerService
import de.dom.cishome.myapplication.compose.turnier.page.CompetitionDetailPage
import de.dom.cishome.myapplication.compose.turnier.page.TurnierWelcomePage

@ExperimentalUnitApi
@ExperimentalPermissionsApi
@ExperimentalMaterialApi
@ExperimentalMaterial3Api
fun NavGraphBuilder.competitionGraph(navController: NavController){
    navigation( startDestination = "start" , route = "competition"){
        composable("start"){
            TurnierWelcomePage( navController )
        }
        composable("competition/detail/{id}"){
            CompetitionDetailPage( navController )
        }
    }
}