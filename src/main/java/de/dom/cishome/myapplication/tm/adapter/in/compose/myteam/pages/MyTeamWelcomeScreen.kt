package de.dom.cishome.myapplication.tm.adapter.`in`.compose.myteam.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.dom.cishome.myapplication.R
import de.dom.cishome.myapplication.compose.shared.MyColorTheme
import de.dom.cishome.myapplication.compose.shared.TmColors
import de.dom.cishome.myapplication.compose.team.model.Team
import de.dom.cishome.myapplication.compose.team.pages.NavBarItem
import de.dom.cishome.myapplication.compose.team.shared.TeamTheme
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.shared.CommonComponents
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.shared.Tm


class MyTeamWelcomePage( val selectedTeam: Team) {

    private val modules = listOf<CommonComponents.CardMenuItem>(
        CommonComponents.CardMenuItem( "Spieler" , "players?team=${selectedTeam.label}" , R.drawable.teamplayer ),
        CommonComponents.CardMenuItem( "Trainer" , "trainer" , R.drawable.trainer ),
        CommonComponents.CardMenuItem( "Turniere" , "competition/team/${selectedTeam.id}" , R.drawable.tuniert ),
        CommonComponents.CardMenuItem( "Platz" , "platz" , R.drawable.platz )
    )

    fun colorScheme(): MyColorTheme = TeamTheme().color;
    fun theme(): TeamTheme = TeamTheme();

    @Composable
    fun WelcomeScreen( clicks: MyTeamWelcomePage.Clicks ){
        this.content( clicks )
    }

    @Composable
    private fun content( clicks: MyTeamWelcomePage.Clicks  ) {
        Scaffold(topBar = { this.topBar(title = this.selectedTeam.label, clicks = clicks, color = colorScheme()) },
            bottomBar = { footer(theme = theme()) }) {
            Box(Modifier.padding(it)) {
                Box(modifier = Modifier.align(Alignment.TopCenter)) {
                    body( clicks )
                }
            }
        }
    }

    @Composable
    private fun body( click: Clicks) {
            Column() {
                Row() {
                    LazyVerticalGrid(columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(15.dp),
                        verticalArrangement = Arrangement.spacedBy(25.dp),
                        horizontalArrangement = Arrangement.spacedBy(25.dp) ){
                        items( modules.size , key = {it}){
                            var m = modules[ it ];
                            Tm.components().MenuCard(i = m, navigate = { click.navTo( m.dest ) })
                        }
                    }
                }
            }
    }

    @Composable
    private fun footer( theme: TeamTheme){
        var ctx = LocalContext.current;

        NavBarItem("", Icon(imageVector = Icons.Filled.Menu, tint = TmColors.secondaryColor, contentDescription = "Localized description"), click = {} )

        NavigationBar( contentColor = TmColors.primaryColor , containerColor = TmColors.primaryColor ) {


            NavigationBarItem(selected = false,
                onClick = { /*TODO*/ },
                icon = { Icon(Icons.Filled.Person, tint = TmColors.secondaryColor, contentDescription = "Localized description") },
                label = { Text("TRAINER" , color = TmColors.secondaryColor) }
            )
            NavigationBarItem(selected = false,
                onClick = { /*TODO*/ },
                icon = { Icon(Icons.Filled.Settings, tint= TmColors.secondaryColor, contentDescription = "Localized description") },
                label = { Text("SPIELER" , color= TmColors.secondaryColor) }
            )

        }

    }

    @Composable
    private fun topBar( title: String, clicks: Clicks, color: MyColorTheme ){
        var defaults = TopAppBarDefaults.topAppBarColors( containerColor = color.primary )
        TopAppBar(
            title = { Text(title , color=color.primaryText) },
            navigationIcon = {
                IconButton(onClick = clicks.navBack ) {
                    Icon(Icons.Filled.ArrowBack, tint=color.primaryText, contentDescription = null)
                }
            },
            actions = {
                // RowScope here, so these icons will be placed horizontally
                IconButton(onClick = {clicks.navTo("home")} ) {
                    Icon(Icons.Filled.Menu, tint=color.primaryText, contentDescription = "Localized description")
                }
            },
            colors = defaults
        )
    }


    data class Clicks( var navBack: ()->Unit, var navTo: (route: String)->Unit ){

    }

}





@Composable
@Preview
fun MyTeamWelcomeScreenPreview(){

    MyTeamWelcomePage( Team("1234" , "Bambini") ).WelcomeScreen( MyTeamWelcomePage.Clicks({},{}) );

}