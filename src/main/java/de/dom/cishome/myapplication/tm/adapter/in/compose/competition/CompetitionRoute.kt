package de.dom.cishome.myapplication.tm.adapter.`in`.compose.competition

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.competition.model.CompetitionsViewModel
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.competition.pages.CompetitionDetailPage
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.competition.pages.CompetitionPage
import de.dom.cishome.myapplication.tm.adapter.out.competition.CompetitionRepository
import de.dom.cishome.myapplication.tm.application.services.CompetitionApplicationService

@ExperimentalUnitApi
@ExperimentalPermissionsApi
@ExperimentalMaterialApi
@ExperimentalMaterial3Api
fun NavGraphBuilder.competitionGraph(navController: NavController){
    navigation( startDestination = "start" , route = "competition/team/{teamId}"){
        composable("start"){
            val repo = CompetitionRepository();
            val model = CompetitionsViewModel( it.savedStateHandle, CompetitionApplicationService( repo , repo ) )
            val teamId = it.arguments?.getString("teamId") ?: "";
            CompetitionPage( teamId ,model, model.clickModel(navController) ).Screen()
        }

        composable("competition/detail/{id}"){
            var id = it.arguments!!.getString("id")!!;
            CompetitionDetailPage().Screen( id );
        }
    }
}