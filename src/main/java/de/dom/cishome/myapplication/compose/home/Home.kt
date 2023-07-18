package de.dom.cishome.myapplication.compose.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import de.dom.cishome.myapplication.R




@Composable
fun home( nav: NavController ){

    var d = MenuData();

    content(d = d, nav = nav)

}

@Composable
fun NavigationBoxes( d: MenuData, nav: NavController ){
    Box() {
        Box( modifier = Modifier.align(Alignment.TopCenter)){
            Column() {
                Row() {
                    LazyVerticalGrid(columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(15.dp),
                        verticalArrangement = Arrangement.spacedBy(25.dp),
                        horizontalArrangement = Arrangement.spacedBy(25.dp) ){
                        items( d.list.size , key = {it}){
                            menuCard(i = d.list[it] , nav)
                        }
                    }
                }
            }

        }
    }
}

@Composable
private fun content( d: MenuData, nav: NavController ){
    var tm = TmComponents();
    Scaffold (
        topBar = { tm.header() },
        bottomBar = {TmBottomBar()}
    ){
        Box(modifier = Modifier.padding(it)){
            NavigationBoxes(d,nav)
        }
    }
}

@Composable
fun TmBottomBar(){



    NavigationBar() {

        NavigationBarItem(selected = false,
            onClick = { /*TODO*/ },
            icon = { Icon(Icons.Filled.Menu, contentDescription = "Localized description") },
            label = { Text("HOME") }
        )
        NavigationBarItem(selected = false,
            onClick = { /*TODO*/ },
            icon = { Icon(Icons.Filled.Person, contentDescription = "Localized description") },
            label = { Text("PROFILE") }
        )
        NavigationBarItem(selected = false,
            onClick = { /*TODO*/ },
            icon = { Icon(Icons.Filled.Settings, contentDescription = "Localized description") },
            label = { Text("SETTINGS") }
        )

    }
}

@Composable
fun menuCard( i: HomeScreenMenuItem, nav: NavController){
    ElevatedCard( onClick = {nav.navigate(i.dest)} ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(125.dp)
                    .heightIn(55.dp, 125.dp),
                contentScale = ContentScale.FillBounds,
                painter = painterResource( i.res ),
                contentDescription = "Fussballer"
            )
        Row(  Modifier.padding(15.dp) ){
            Box( modifier=Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                Text(text = i.name.uppercase(), textAlign = TextAlign.Center, fontWeight = FontWeight.Bold )
            }
        }
    }
}

class MenuData {

    var list = listOf<HomeScreenMenuItem>(
        HomeScreenMenuItem("Verein" , "club", R.drawable.clublogo),
        HomeScreenMenuItem("Player" , "player", R.drawable.club),
        HomeScreenMenuItem("Turnier" , "turnier", R.drawable.tuniert),
        HomeScreenMenuItem("Mitglieder" , "membership", R.drawable.member),
        HomeScreenMenuItem("Platz" , "platz", R.drawable.platz),
    )


}

data class HomeScreenMenuItem(var name: String, var dest: String, var res: Int)

@Composable
@Preview
fun HomePreview(){
    var d = MenuData();
    var nav = rememberNavController();

    content(d,nav = nav)
}