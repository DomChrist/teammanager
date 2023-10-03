package de.dom.cishome.myapplication.tm.adapter.`in`.compose.myteam.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.dom.cishome.myapplication.compose.shared.TmColors
import de.dom.cishome.myapplication.compose.team.model.Team
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.myteam.model.MyTeamListViewModel
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.shared.Tm
import de.dom.cishome.myapplication.ui.MainControl

class MyTeamSelectionScreen() {

    @Composable fun Screen(
        model: MyTeamListViewModel = viewModel(factory=MyTeamListViewModel.Factory(LocalContext.current)),
        clicks: MainControl ){

        var teams = remember { mutableStateOf<List<Team>>( emptyList() ) }
        model.teams.observeForever { teams.value = it }
        if( teams.value.isEmpty() ){
            Tm.components().Loading()
        } else {
            content( teams.value , clicks );
        }
    }


    @Composable internal fun content(teams: List<Team>, clicks: MainControl) {
        Scaffold(
            topBar = { Tm.components().TmTopBar( title = "TEAMS" , showBackArrow = true , clickModel = clicks) },
            bottomBar = { this.bottomBar() },
            content = { this.body(it , teams , clicks) }
            )
    }

    @Composable
    private fun body(it: PaddingValues, teams: List<Team>, clicks: MainControl) {
        Box( Modifier.padding(it)){
            LazyVerticalGrid(columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(15.dp),
                verticalArrangement = Arrangement.spacedBy(25.dp),
                horizontalArrangement = Arrangement.spacedBy(25.dp) ){
                items( teams.size , key = {it}){
                    var t = teams[ it ];
                    TeamCard( t , select = { clicks.navTo( "myteams/teams/team?team=${it.label}" ) } );
                }
            }
        }

    }

    @Composable private fun TeamCard(t: Team, select: (t: Team) -> Unit) {
        Card(Modifier.clickable { select(t) }  ) {
            Row(modifier= Modifier
                .padding(15.dp)
                .fillMaxWidth()){
                Text( modifier=Modifier.fillMaxWidth(), text = t.label , textAlign = TextAlign.Center , fontWeight = FontWeight.Bold)
            }
        }
    }

    @Composable
    private fun topBar(clicks: MainControl) {
        var color = TmColors.App;
        var defaults = TopAppBarDefaults.topAppBarColors( containerColor = color.primary )
        TopAppBar(
            title = { Text("TEAMS" , color=color.primaryText) },
            navigationIcon = {
                IconButton(onClick = clicks.back ) {
                    Icon(Icons.Filled.ArrowBack, tint=color.primaryText, contentDescription = null)
                }
            },
            actions = {
                // RowScope here, so these icons will be placed horizontally
                IconButton(onClick = {} ) {
                    Icon(Icons.Filled.Menu, tint=color.primaryText, contentDescription = "Localized description")
                }
            },
            colors = defaults
        )
    }

    @Composable
    private fun bottomBar() {
    }

    data class ClickModel( val back:()->Unit , val navTo: (r: String) -> Unit,
        val select: (t: Team)->Unit ){
        companion object Factory{
            fun default( m: MainControl , select: (t:Team)->Unit ): ClickModel = ClickModel({ m.back() },{m.navTo(it)},select)
        }
    }

}



@Composable
@Preview
fun MyTeamSelectionScreenPreview(){

    var state = remember {
        mutableStateOf(
            listOf(
                Team("Ba", "Bambini"),
                Team("F", "F")
            )
        )
    }

    MyTeamSelectionScreen().content(teams = state.value, clicks = MainControl({},{},{}))

}