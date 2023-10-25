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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.dom.cishome.myapplication.R
import de.dom.cishome.myapplication.compose.shared.MyColorTheme
import de.dom.cishome.myapplication.compose.shared.TmColors
import de.dom.cishome.myapplication.compose.team.model.Team
import de.dom.cishome.myapplication.compose.team.pages.NavBarItem
import de.dom.cishome.myapplication.compose.team.shared.TeamTheme
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.myteam.model.MyTeamViewModel
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.shared.CommonComponents
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.shared.Tm


class MyTeamWelcomePage(val teamId: String? ) {


    private var modules = listOf<CommonComponents.CardMenuItem>()

    fun colorScheme(): MyColorTheme = TeamTheme().color;
    fun theme(): TeamTheme = TeamTheme();

    @Composable
    fun WelcomeScreen(
        model: MyTeamViewModel = viewModel(factory=MyTeamViewModel.MyTeamViewFactory(LocalContext.current,teamId)),
        clicks: MyTeamWelcomePage.Clicks ){

        var selectedTeam = remember{ mutableStateOf<Team?>(model.selectedTeam.value) };
        model.selectedTeam.observeForever { selectedTeam.value = it }

        if( selectedTeam.value != null ){
            this.modules = modules( selectedTeam.value!! )
            this.content( selectedTeam.value!! , this.modules, clicks )
        }
    }

    @Composable
    private fun modules( t: Team ): List<CommonComponents.CardMenuItem> {
        var r = LocalContext.current.resources!!;
        return mutableListOf<CommonComponents.CardMenuItem>().apply {
            if (r.getBoolean(R.bool.myTeam_sub_players))
                add(CommonComponents.CardMenuItem("Spieler", "players?team=${t.id}", R.drawable.teamplayer))

            if (r.getBoolean(R.bool.myTeam_sub_training))
                add(CommonComponents.CardMenuItem("Training", "training", R.drawable.trainer))

            if (r.getBoolean(R.bool.myTeam_sub_competition))
                add(CommonComponents.CardMenuItem("Spieltag", "competition/team/${t.id}", R.drawable.tuniert))

            if (r.getBoolean(R.bool.myTeam_sub_locations))
                add(CommonComponents.CardMenuItem("Platz", "platz", R.drawable.platz))
        }
    }

    @Composable
    internal fun content(selectedTeam: Team, modules: List<CommonComponents.CardMenuItem> , clicks: Clicks) {
        this.modules = modules;
        Scaffold(topBar = { this.topBar(title = selectedTeam.label, clicks = clicks, color = colorScheme()) },
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
    var modules = listOf<CommonComponents.CardMenuItem>(
        CommonComponents.CardMenuItem( "Spieler" , "players?team" , R.drawable.teamplayer ),
        CommonComponents.CardMenuItem( "Trainer" , "trainer" , R.drawable.trainer ),
        CommonComponents.CardMenuItem( "Turniere" , "competition/team/" , R.drawable.tuniert ),
        CommonComponents.CardMenuItem( "Platz" , "platz" , R.drawable.platz )
    )
    MyTeamWelcomePage("1234").content( Team("1234" , "Bambini") , modules, MyTeamWelcomePage.Clicks({},{}) );

}