package de.dom.cishome.myapplication.tm.adapter.compose.lab

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
import de.dom.cishome.myapplication.ui.MainControl

@ExperimentalUnitApi
@ExperimentalPermissionsApi
@ExperimentalMaterialApi
@ExperimentalMaterial3Api
fun NavGraphBuilder.labGraph(navController: NavController, mainControl: MainControl){

    navigation( startDestination = "start" , route = "lab") {

        composable(
            "start",
            listOf(navArgument("team") { defaultValue = ""; type = NavType.StringType })) {

            DemoPullToRefresh()

        }

    }


}
