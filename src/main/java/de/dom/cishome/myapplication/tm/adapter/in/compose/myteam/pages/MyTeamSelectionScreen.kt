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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.dom.cishome.myapplication.compose.shared.TmColors
import de.dom.cishome.myapplication.compose.team.model.Team

class MyTeamSelectionScreen(val teams: MutableState<List<Team>>) {

    @Composable fun Screen( clicks: ClickModel = ClickModel.default() ){
        content( teams , clicks );
    }


    @Composable private fun content(teams: MutableState<List<Team>>, clicks: ClickModel) {
        Scaffold(
            topBar = { this.topBar(clicks) },
            bottomBar = { this.bottomBar() },
            content = { this.body(it , teams , clicks) }
            )
    }

    @Composable
    private fun body(it: PaddingValues, teams: MutableState<List<Team>>, clicks: ClickModel) {
        Box( Modifier.padding(it)){
            LazyVerticalGrid(columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(15.dp),
                verticalArrangement = Arrangement.spacedBy(25.dp),
                horizontalArrangement = Arrangement.spacedBy(25.dp) ){
                items( teams.value.size , key = {it}){
                    var t = teams.value[ it ];
                    TeamCard( t , clicks.select );
                }
            }
        }

    }

    @Composable private fun TeamCard(t: Team, select: (t: Team) -> Unit) {
        Card(Modifier.clickable { select(t) }  ) {
            Row(modifier=Modifier.padding(15.dp).fillMaxWidth()){
                Text( modifier=Modifier.fillMaxWidth(), text = t.label , textAlign = TextAlign.Center , fontWeight = FontWeight.Bold)
            }
        }
    }

    @Composable
    private fun topBar(clicks: ClickModel) {
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
            fun default(): ClickModel = ClickModel({},{},{})
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

    MyTeamSelectionScreen( state ).Screen();

}