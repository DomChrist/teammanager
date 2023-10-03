package de.dom.cishome.myapplication.compose.club.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.dom.cishome.myapplication.R
import de.dom.cishome.myapplication.compose.club.model.ClubViewModel
import de.dom.cishome.myapplication.compose.shared.TmColors
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.shared.CommonComponents
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.shared.Tm
import de.dom.cishome.myapplication.tm.application.services.TeamApplicationService
import de.dom.cishome.myapplication.ui.MainControl


class ClubMainPage( var mainControl: MainControl, var model: ClubViewModel){



    @Composable
    fun Screen(){
        this.Layout()
    }

    @Composable
    private fun Layout(){

        Scaffold(
            topBar = { Tm.components().TmTopBar( title="VEREIN", clickModel = mainControl , showBackArrow = true ) },
            bottomBar = { bottomBar() },
            content = {this.content(it)}
            )

    }

    @Composable private fun content(it: PaddingValues) {
        Box(Modifier.padding(it)){
            Column {
                Row(Modifier.height(220.dp)) {
                    Column(
                        Modifier
                            .weight(1f)
                            .padding(15.dp)) {
                        Tm.components().MenuCard(i = CommonComponents.CardMenuItem("TEAMS" , "teams" , R.drawable.teamplayer), navigate = {})
                    }
                    Column(
                        Modifier
                            .weight(1f)
                            .padding(15.dp)) {
                        Tm.components().MenuCard(i = CommonComponents.CardMenuItem("TRAINER" , "coach" , R.drawable.trainer), navigate = {})
                    }
                }

                Row( Modifier.fillMaxWidth()){
                    Button( modifier= Modifier
                        .fillMaxWidth()
                        .padding(15.dp), onClick = { model.createAllTeams() }) {
                        Text("Teams anlegen")
                    }
                }

                Row(Modifier.height(220.dp)){
                    Column(
                        Modifier
                            .weight(1f)
                            .padding(15.dp)) {
                        Tm.components().MenuCard(i = CommonComponents.CardMenuItem("PLÄTZE" , "ground" , R.drawable.platz), navigate = {})
                    }
                    Column(
                        Modifier
                            .weight(1f)
                            .padding(15.dp)) {
                        Tm.components().MenuCard(i = CommonComponents.CardMenuItem("MITGLIEDER" , "members" , R.drawable.member), navigate = {})
                    }
                }
            }
        }
    }

    @Composable
    private fun topBar(){
        var color = TmColors.App;
        var defaults = TopAppBarDefaults.topAppBarColors( containerColor = color.primary )
        TopAppBar(
            title = { Text("VEREIN" , color=color.primaryText) },
            navigationIcon = {
                IconButton(onClick = {  }) {
                    Icon(Icons.Filled.ArrowBack, tint=color.primaryText, contentDescription = null)
                }
            },
            actions = {
                // RowScope here, so these icons will be placed horizontally
                IconButton(onClick = {  }) {
                    Icon(Icons.Filled.Menu, tint=color.primaryText, contentDescription = "Localized description")
                }
            },
            colors = defaults
        )
    }
    @Composable
    private fun bottomBar(){
        NavigationBar() {

            NavigationBarItem(selected = false,
                onClick = { /*TODO*/ },
                icon = { Icon(Icons.Filled.Menu, contentDescription = "Localized description") },
                label = { Text("HOME") }
            )
            NavigationBarItem(selected = false,
                onClick = { /*TODO*/ },
                icon = { Icon(Icons.Outlined.Person, contentDescription = "Localized description") },
                label = { Text("TRAINER") }
            )
            NavigationBarItem(selected = false,
                onClick = { /*TODO*/ },
                icon = { Icon(Icons.Filled.Person, contentDescription = "Localized description") },
                label = { Text("SETTINGS") }
            )

        }
    }


}





@Composable
@Preview
fun ClubMainPagePreview(){
    ClubMainPage( MainControl({},{},{}) , ClubViewModel(TeamApplicationService())).Screen();
}
