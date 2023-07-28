package de.dom.cishome.myapplication.compose.team.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.rememberNavController
import de.dom.cishome.myapplication.compose.home.TmComponents
import de.dom.cishome.myapplication.compose.shared.TmColors
import de.dom.cishome.myapplication.compose.team.model.Team
import de.dom.cishome.myapplication.compose.team.model.TeamPersistencePort
import de.dom.cishome.myapplication.compose.team.model.TeamViewModel
import de.dom.cishome.myapplication.compose.team.shared.TeamTheme

@Composable
fun TeamDetailPage(entry: NavBackStackEntry,nav: NavController, team: Team, model: TeamViewModel){
        var backClick: ()->Unit = {nav.navigateUp()}
        content( model.selected!! , nav, backClick )
}

@Composable
private fun content(team: Team, nav:NavController, onSelect: () -> Unit) {
    var theme = TeamTheme();

    var list = listOf<NavBarItem>(
        NavBarItem("TRAINER", Icon(imageVector = Icons.Filled.Person , "9999")){
            nav.currentBackStackEntry!!.savedStateHandle["TEAM"] = mutableStateOf(team);
            nav.navigate("team/detail/player")
        },
        NavBarItem("SPIELER", Icon(imageVector = Icons.Filled.Person , "9999")) {
            nav.currentBackStackEntry!!.savedStateHandle["TEAM"] = mutableStateOf(team)
            nav.navigate("player" )
        },
    )

    Scaffold(
        topBar = {TmComponents().stage1HeaderCustomize(title = team.label, nav = nav, color = theme.color)},
        bottomBar = { footer(theme = theme)}
    ) {
        Box( modifier = Modifier.padding(it) ){

            Column() {
                for(  c in list ){
                    Row(){
                        Card( onClick={
                            c.click()
                            }, Modifier.padding(10.dp) ){
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(15.dp),
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                text="${c.label}"
                            )
                        }
                    }
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
@Preview
fun TeamDetailPagePreview(){
    class InternRepo : TeamPersistencePort {
        override fun write(m: Team) {
            TODO("Not yet implemented")
        }
        override fun readAll(): List<Team> {
            return listOf<Team>(Team("1234","F"), Team("b" , "Bambini")  )
        }
        override fun read(team: String): Team? {
            TODO("Not yet implemented")
        }
    }
    var t = Team("1234" , "Bambini")
    var m = TeamViewModel( InternRepo() )

    content(team = t, rememberNavController() , {})

}

