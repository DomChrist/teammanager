package de.dom.cishome.myapplication.tm.adapter.`in`.compose.myteam

import android.util.Log
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import de.dom.cishome.myapplication.compose.team.model.Team
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.competition.model.CompetitionsViewModel
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.competition.pages.CompetitionPage
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.myteam.pages.MyTeamSelectionScreen
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.myteam.pages.MyTeamWelcomePage
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.player.pages.PlayerOverviewPage
import de.dom.cishome.myapplication.tm.adapter.out.PlayerRepository
import de.dom.cishome.myapplication.tm.adapter.out.competition.CompetitionRepository
import de.dom.cishome.myapplication.tm.application.CompetitionApplicationService
import de.dom.cishome.myapplication.tm.application.MyTeamApplicationService
import de.dom.cishome.myapplication.tm.application.TeamApplicationService
import de.dom.cishome.myapplication.tm.application.domain.player.model.Player
import java.lang.Exception

@ExperimentalUnitApi
@ExperimentalPermissionsApi
@ExperimentalMaterialApi
@ExperimentalMaterial3Api
fun NavGraphBuilder.myTeamGraph(nav: NavController, app: MyTeamApplicationService, teamApp: TeamApplicationService){



    navigation( startDestination = "start" , route = "myteam"){

        composable("start"){
            var r = remember { mutableStateOf<List<Team>>(emptyList()) }
            teamApp.all { r.value = it }
            val clickModel = MyTeamSelectionScreen.ClickModel(
                { nav.navigateUp() },
                { nav.navigate(it) },
                {
                    app.select(it);
                    nav.navigate("myteams/teams/team?team=${it.label}")
                }
            )
            MyTeamSelectionScreen( r ).Screen( clickModel );
        }

        composable("myteams/teams"){
            var r = remember { mutableStateOf<List<Team>>(emptyList()) }
            teamApp.all { r.value = it }
            MyTeamSelectionScreen( r ).Screen();
        }

        composable(route = "myteams/teams/team?team={team}" , arguments = listOf( navArgument("team"){defaultValue=""; type = NavType.StringType} )){
            MyTeamWelcomePage(app.selectedTeam!!).WelcomeScreen( clicks = MyTeamWelcomePage.Clicks( navBack = {nav.navigateUp()} , navTo = {
                try{ nav.navigate(it) } catch (e: Exception){ Log.w("Navigation" , e.message ?: "") }
            } ) );
        }

        composable(route = "myteams/teams/team/players"){
            PlayerOverviewPage().Screen( clicks = PlayerOverviewPage.PlayerOverviewClick.clicks(nav))
        }


    }
}