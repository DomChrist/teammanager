package de.dom.cishome.myapplication.compose.player.pages

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavHostController
import de.dom.cishome.myapplication.compose.player.service.PlayerService
import java.lang.NumberFormatException
import java.time.LocalDate
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import de.dom.cishome.myapplication.compose.home.TmComponents
import de.dom.cishome.myapplication.compose.player.service.Player


@Composable
fun PlayerWelcomePage( playerService: PlayerService, nc: NavController ){
    player(playerService = playerService, nc = nc)
}

@Composable
fun player(playerService: PlayerService, nc: NavController) {
    val materialBlue700= Color(0xFF1976D2)

    val tm = TmComponents();

    Scaffold(
        topBar = { tm.stage1Header(title = "Spielerübersicht", nav = nc)  },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = { FloatingActionButton(onClick = { nc.navigate("player/add") }){
            Text("+")
        } },
        bottomBar = { BottomAppBar( modifier = Modifier.background(materialBlue700) ) { Text("BottomAppBar") } }
    ){contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) { content(playerService,nc) }
    }


}

@Composable
fun content( playerService: PlayerService, nc: NavController ){
    Text(text = "ÜBERSICHT")

    var tabIndex: MutableState<Int> = remember { mutableStateOf(0) }

    val tabs = listOf("SPIELER", "SCHNUPPERER")

    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(selectedTabIndex = tabIndex.value) {
            tabs.forEachIndexed { index, title ->
                Tab(text = { Text(title) },
                    selected = tabIndex.value == index,
                    onClick = { tabIndex.value = index }
                )
            }
        }
        when (tabIndex.value) {
            0 -> {
                LazyColumn(modifier = Modifier.padding(PaddingValues(5.dp , 45.dp))){
                    items( playerService.player().filter { !it.trial } ){
                        PlayerItemView( it , nc )
                    }
                }
            }
            1 -> {
                LazyColumn(modifier = Modifier.padding(PaddingValues(5.dp , 45.dp))){
                    items( playerService.player().filter { it.trial } ){
                        PlayerItemView( it , nc )
                    }
                }
            }
        }
    }



}

@Composable
fun PlayerItemView( p: Player , nc: NavController ){

    var m: MutableState<Player> = remember{ mutableStateOf( p ) };
    val onClick = {
        nc.saveState()!!.putString("playerId" , p.id)
        nc.currentBackStackEntry
            ?.savedStateHandle
            ?.set("player" , m);
        nc.navigate("player/detail/" + p.id )
    }


    Card( onClick=onClick,  modifier = Modifier
        .padding(PaddingValues(15.dp, 5.dp, 15.dp, 5.dp))
        .fillMaxWidth() ) {
        Box(modifier = Modifier.padding(PaddingValues(15.dp))){
            Column() {
                Icon(
                    Icons.Rounded.Person,
                    contentDescription = stringResource(id = androidx.compose.ui.R.string.in_progress)
                )
            }
            Column(Modifier.padding(30.dp , 0.dp, 0.dp, 0.dp)) {
                Text(  p.givenName.plus(" ").plus(p.familyName), fontWeight = FontWeight.Bold )
            }
        }
    }
}

@Composable
@Preview
fun PlayerWelcomePagePreview(){
    var s = PlayerService(null);
    var nc = rememberNavController();
    player(playerService = s, nc = nc)
}

fun alter( t: TextFieldValue): String{
    try{
        var year = LocalDate.now().year;
        var jahrgang = Integer.parseInt( t.text );
        var r = year - jahrgang;
        return "$r";
    }catch ( n: NumberFormatException ){
        Log.i("NFE" , "Value is not a number")
    }
    return "";
}

@Composable
fun playerMinus(){

    var t = "Fußball Star";
    var zahl1 = remember { mutableStateOf(TextFieldValue()) }
    var zahl2 = remember { mutableStateOf(TextFieldValue()) }
    var result = remember { mutableStateOf(TextFieldValue()) }

    Column() {
        Row(){
            Text("Hello")
        }

        Row(){
            TextField( value = zahl1.value,
                onValueChange = {zahl1.value = it } )
        }

        Row(){
            TextField( value = zahl2.value,
                onValueChange = {zahl2.value = it } )
        }

        Row(){
            TextField( value = result.value,
                onValueChange = {result.value = it } )
        }

        Row(){
            Button( onClick = {
                try{
                    var i1 = Integer.parseInt(zahl1.value.text)
                    var i2 = Integer.parseInt(zahl2.value.text)
                    result.value = TextFieldValue(minus( i1 , i2))
                }catch (e: NumberFormatException){
                    e.printStackTrace()
                }
            } ){
                Text(text = "Berechne")
            }
        }
    }



}

fun minus( dominik: Int, aurelian:Int ): String{
    var r = dominik - aurelian
    return "Du bist $r Jahre alt";
}