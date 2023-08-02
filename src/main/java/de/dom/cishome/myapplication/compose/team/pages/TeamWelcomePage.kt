package de.dom.cishome.myapplication.compose.team.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.compose.rememberNavController
import de.dom.cishome.myapplication.R
import de.dom.cishome.myapplication.compose.home.TmComponents
import de.dom.cishome.myapplication.compose.shared.TmColors
import de.dom.cishome.myapplication.compose.team.components.TeamComponents
import de.dom.cishome.myapplication.compose.team.model.Team
import de.dom.cishome.myapplication.compose.team.model.TeamPersistencePort
import de.dom.cishome.myapplication.compose.team.model.TeamViewModel
import de.dom.cishome.myapplication.compose.team.shared.TeamTheme

@Composable
fun TeamWelcomePage(navBackStackEntry: NavBackStackEntry, model: TeamViewModel, nav: NavController){

    var onSelect: ( t: Team )->Unit = {
        model.selected = it;
        nav.navigate("team/detail" )
    }
    content( model , nav , onSelect );
}

@Composable
private fun content(model: TeamViewModel, nav: NavController, onSelect: (t: Team) -> Unit){
    var tm = TmComponents();
    var theme = TeamTheme();

    var showCreateDialog = remember { mutableStateOf(false) }

    var buttonCreateClick: ()->Unit = { showCreateDialog.value = true }

    Scaffold(
        topBar = { tm.stage1HeaderCustomize(title = "Team", nav = nav, color = theme.color) },
        floatingActionButton = { floatingButton(buttonCreateClick,theme) },
        floatingActionButtonPosition = FabPosition.End,
        bottomBar = { footer(theme = theme)}) {
            Box( modifier = Modifier.padding(it) ){

                Column {
                    Row{
                        Column {
                            Row{
                                Card(modifier = Modifier.padding(35.dp)) {
                                    Image(
                                        contentScale = ContentScale.FillBounds,
                                        painter = painterResource(R.drawable.trainer),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .heightIn(55.dp, 155.dp) , contentDescription = "")
                                    Row( modifier = Modifier.fillMaxWidth() ){
                                        Text(modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(15.dp) , text = "TRAINER" , textAlign = TextAlign.Center , fontSize = TextUnit(7f,
                                            TextUnitType.Em))
                                    }
                                }
                            }
                            Row{
                                Card(modifier = Modifier.padding(35.dp)) {
                                    Image(
                                        contentScale = ContentScale.FillBounds,
                                        painter = painterResource(R.drawable.teamplayer),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .heightIn(55.dp, 155.dp) , contentDescription = "")
                                    Row( modifier = Modifier.fillMaxWidth() ){
                                        Text(modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(15.dp) , text = "TEAMS" , textAlign = TextAlign.Center , fontSize = TextUnit(7f,
                                            TextUnitType.Em))
                                    }
                                }
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
                label = { Text("PROFILE" , color = TmColors.secondaryColor) }
            )
            NavigationBarItem(selected = false,
                onClick = { /*TODO*/ },
                icon = { Icon(Icons.Filled.Settings, tint= TmColors.secondaryColor, contentDescription = "Localized description") },
                label = { Text("SETTINGS" , color= TmColors.secondaryColor) }
            )

        }

}

@Composable
private fun floatingButton( click: () -> Unit, theme: TeamTheme){
    FloatingActionButton(onClick = click ,
        contentColor = theme.color.primaryText,
        shape = RoundedCornerShape(100.dp),
        containerColor = theme.color.primary ) {
        Text("+", fontSize = TextUnit(5f, TextUnitType.Em), fontWeight = FontWeight.Bold)
    }
}

data class NavBarItem( var label: String, val icon: Unit , val click: ()->Unit ){

}


@Composable
@Preview
fun TeamWelcomePagePreview(){
    class InternRepo : TeamPersistencePort{
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
    var nav = rememberNavController()
    var model = TeamViewModel( InternRepo() );

    content(model, nav, { } )

}