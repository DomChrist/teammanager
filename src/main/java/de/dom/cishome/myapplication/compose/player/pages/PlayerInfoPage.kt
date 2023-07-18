package de.dom.cishome.myapplication.compose.player.pages

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import de.dom.cishome.myapplication.compose.player.service.Player

@Composable
fun PlayerInfoPage(nav: NavController){

    val state = nav.previousBackStackEntry!!.savedStateHandle.get<MutableState<Player>>("player")

    content(p =  state )


}

@Composable
fun content( p: MutableState<Player>? ){

    if( p == null ){
        showEmpty();
    } else {
        showPlayer( p.value );
    }

}

@Composable
fun showPlayer(p: Player) {
    Text(text = "${p.id}")
}

@Composable
fun showEmpty() {
    Text("...Spieler nicht gefunden..")
}


@Composable
@Preview
fun PlayerInfoPagePreview(){
    var p = null; // remember{ mutableStateOf(Player("tst" , "test" , "test" , 2018)) }
    content(p = p)

}