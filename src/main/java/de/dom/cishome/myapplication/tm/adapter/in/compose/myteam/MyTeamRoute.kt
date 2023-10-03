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
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.myteam.model.MyTeamViewModel
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.myteam.pages.MyTeamSelectionScreen
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.myteam.pages.MyTeamWelcomePage
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.player.pages.PlayerOverviewPage
import de.dom.cishome.myapplication.ui.MainControl
import java.lang.Exception

@ExperimentalUnitApi
@ExperimentalPermissionsApi
@ExperimentalMaterialApi
@ExperimentalMaterial3Api
fun NavGraphBuilder.myTeamGraph(nav: NavController, mainControl: MainControl){

    navigation( startDestination = "start" , route = "myteam"){

        composable("start"){
            var r = remember { mutableStateOf<List<Team>>(emptyList()) }
            val clickModel = MyTeamSelectionScreen.ClickModel.default(mainControl , { mainControl.navTo("myteams/teams/team?team=${it}")} );
            MyTeamSelectionScreen().Screen( clicks = mainControl );
        }

        composable("myteams/teams"){
            MyTeamSelectionScreen().Screen( clicks = mainControl );
        }

        composable(route = "myteams/teams/team?team={team}" , arguments = listOf( navArgument("team"){defaultValue=""; type = NavType.StringType} )){
            val selectedTeam = nav.currentBackStackEntry?.arguments?.getString("team") ?: null;
            MyTeamWelcomePage( selectedTeam ).WelcomeScreen( clicks = MyTeamWelcomePage.Clicks( navBack = {nav.navigateUp()} , navTo = {
                try{ nav.navigate(it) } catch (e: Exception){ Log.w("Navigation" , e.message ?: "") }
            } ) );
        }

        composable(route = "myteams/teams/team/players"){
            PlayerOverviewPage().Screen( clicks = PlayerOverviewPage.PlayerOverviewClick.clicks(nav))
        }


    }
}