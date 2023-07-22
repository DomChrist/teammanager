package de.dom.cishome.myapplication.compose.player.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import de.dom.cishome.myapplication.compose.player.component.PlayerDetailViewLayout
import de.dom.cishome.myapplication.compose.player.service.Player
import de.dom.cishome.myapplication.compose.player.service.PlayerService
import java.time.LocalDate

@Composable
@ExperimentalMaterialApi
fun TrialPlayerDetailPage(
    playerService: PlayerService,
    navController: NavController,
    navBackStackEntry: NavBackStackEntry
){
    var p = playerService.selected;
    var counter = remember{ mutableStateOf(0) }



    if( p != null ){
        var membershipCall: ()->Unit = {
            playerService.startMembership( p )
        }
        var l = listOf<PlayerNavItem>(
            PlayerNavItem(first = "Kontaktpersonen" , second = "Bearbeiten" , click = {}),
            PlayerNavItem(first = "Mitgliedschaft" , second = "Starten" , click = membershipCall )
        )
        TrialPlayerContent(
            p = p!!, list = l,
            nav = navController,
            counter)
    } else {
        Text("No player selected")
    }

}

@Composable
@ExperimentalMaterialApi
private fun TrialPlayerContent(
    p: Player,
    list: List<PlayerNavItem>,
    nav: NavController,
    click: MutableState<Int>
){

    BottomSheetScaffold(sheetContent = {
        BottomSheet(click)
    }) {
        PlayerDetailViewLayout( p,list, nav );
    }


}

@Composable
private fun BottomSheet(counter: MutableState<Int>) {
    Box( modifier = Modifier.padding(0.dp , 15.dp) ) {
        Row(){
            Column() {
                Row( modifier = Modifier.padding(15.dp , 15.dp) ) {
                    Column(modifier = Modifier.weight(1f)) {
                        FilledIconButton( modifier = Modifier.padding(5.dp , 0.dp).fillMaxWidth(), onClick = { counter.value = counter.value.inc() }) {
                            Icon( Icons.Filled.Clear , "" )
                        }
                    }
                    Column(modifier = Modifier.weight(3f)) {
                        Button( modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth(), onClick = { counter.value = counter.value.inc() }) {
                            Text(text = "# ${counter.value}")
                        }
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        FilledIconButton( modifier = Modifier.padding(5.dp , 0.dp).fillMaxWidth(), onClick = { counter.value = counter.value.dec() }) {
                            Icon(Icons.Filled.Clear , "")
                        }
                    }
                }
                Row( modifier = Modifier.padding( 15.dp , 15.dp) ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Button( modifier = Modifier.fillMaxWidth(), onClick = { /*TODO*/ }) {
                            Text("BEENDEN")
                        }
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Button( modifier = Modifier.fillMaxWidth(), onClick = { /*TODO*/ }) {
                            Text("ZURÜCKSETZEN")
                        }
                    }
                }
            }
        }


    }
}

@Composable
@Preview
@ExperimentalMaterialApi
private fun TrialPlayerDetailPreview(){
    var nav = rememberNavController()
    var p = Player("1234", "Dominik", "Christ" , LocalDate.now() , true)
    var l = listOf<PlayerNavItem>( PlayerNavItem(first = "Kontaktpersonen" , second = "Bearbeiten" , click = {}) )
    var click = remember{ mutableStateOf(0) }
    TrialPlayerContent(p, l, nav, click)
}
