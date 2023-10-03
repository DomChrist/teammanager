package de.dom.cishome.myapplication.tm.adapter.`in`.compose.player.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.dom.cishome.myapplication.R
import de.dom.cishome.myapplication.compose.player.pages.JahrGang
import de.dom.cishome.myapplication.compose.player.pages.NewPlayerCommand
import de.dom.cishome.myapplication.compose.player.pages.RowField
import de.dom.cishome.myapplication.compose.shared.TmColors
import java.time.LocalDate


class AddPlayerScreen{

    private var FIRST_STEP = 0;
    private var MAX_STEP = 2;

    @Composable
    fun Screen(
        onPlayerAdded: (cmd: NewPlayerCommand) -> Unit,
        onBackClick: () -> Unit
    ){
        var cmd: NewPlayerCommand = NewPlayerCommand(
            remember { mutableStateOf(TextFieldValue()) },
            remember { mutableStateOf(TextFieldValue()) },
            remember { mutableStateOf(LocalDate.now()) },
            remember { mutableStateOf(TextFieldValue()) },
            remember { mutableStateOf(TextFieldValue()) },
            remember { mutableStateOf(TextFieldValue()) },
            remember { mutableStateOf(false) },
            remember { mutableStateOf(TextFieldValue()) }
        )
        Scaffold(
            topBar = { header(onBackClick) },
            bottomBar = { bottomBar( cmd , onPlayerAdded ) }
        ) {
            Box(modifier = Modifier.padding(it)){
                Body(cmd,onPlayerAdded)
            }
        }
    }



    @Composable
    private fun Body(cmd: NewPlayerCommand, onPlayerAdded: (cmd: NewPlayerCommand) -> Unit) {
        val colMod = Modifier
            .fillMaxWidth()
            .padding(10.dp)

        Box(){
            Box(){
                Image( modifier=Modifier.fillMaxWidth(), contentScale=ContentScale.FillWidth,
                    painter = painterResource(id = R.drawable.teamplayer), contentDescription = "")
            }
        }

        Column {
                when (cmd.step.value) {
                    FIRST_STEP -> {
                        step1(colMod = colMod, cmd = cmd)
                    }
                    1 -> {
                        step2(colMod = colMod, cmd = cmd)
                    }
                    MAX_STEP -> {
                        step3(colMod = colMod, cmd = cmd, onPlayerAdded = onPlayerAdded)
                    }

                }
                Row{
                    bottomBar(cmd = cmd, onPlayerAdded = onPlayerAdded)
                }

        }



    }

    @Composable
    private fun header(onBackClick: () -> Unit) {
        var title = "Spieler anlegen"
        var color = TmColors.App;
        var defaults = TopAppBarDefaults.topAppBarColors( containerColor = color.primary )
        TopAppBar(
            title = { Text(title , color=color.primaryText) },
            navigationIcon = {
                IconButton(onClick = onBackClick ) {
                    Icon(Icons.Filled.ArrowBack, tint=color.primaryText, contentDescription = null)
                }
            },
            actions = {
                // RowScope here, so these icons will be placed horizontally
                IconButton(onClick = onBackClick) {
                    Icon(Icons.Filled.Menu, tint=color.primaryText, contentDescription = "Localized description")
                }
            },
            colors = defaults
        )
    }

    @Composable
    private fun bottomBar(cmd: NewPlayerCommand, onPlayerAdded:(c:NewPlayerCommand)->Unit) {
        val app = TmColors.App;
        val colors = ButtonDefaults.buttonColors( containerColor = app.primary , contentColor = app.primaryText )


        Column {
            if( cmd.step.value > FIRST_STEP ){
                Row(){
                    Button( colors = colors, modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp), onClick = { cmd.dec() }) {
                        Text(text = "BACK")
                    }
                }
            }
            if( cmd.step.value < MAX_STEP ){
                Row(){
                    Button( colors = colors, modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp), onClick = { cmd.inc() }) {
                        Text(text = "NEXT")
                    }
                }
            }

            if( cmd.step.value == MAX_STEP){
                Row(){
                    Button( colors=colors, modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp), onClick = { onPlayerAdded(cmd) } ) {
                        Text(text = "SAVE")
                    }
                }
            }
        }





    }

    @Composable
    private fun step1(
        colMod: Modifier,
        cmd: NewPlayerCommand
    ) {
        val app = TmColors.App;
        val colors = ButtonDefaults.buttonColors( containerColor = app.primary , contentColor = app.primaryText )

        Column(modifier = colMod) {
                Column() {
                    OutlinedCard(modifier = colMod) {
                        Column(colMod){
                            Text(text = "SPIELER")
                            Divider()
                            RowField(name = "Vorname", input = cmd.givenName)
                            RowField(name = "Nachname", input = cmd.familyName)
                            JahrGang(value = cmd.jahrgang)
                        }
                    }
                }
        }
    }

    @Composable
    private fun step2( colMod: Modifier, cmd: NewPlayerCommand ) {
        val app = TmColors.App;
        val colors = ButtonDefaults.buttonColors( containerColor = app.primary , contentColor = app.primaryText )

        Column( colMod ) {
            Row(){
                Column() {
                    OutlinedCard(modifier = colMod) {
                        Column( modifier = colMod) {
                            RowField(name = "Vorname", input = cmd.contactGivenName)
                            RowField(name = "Nachname", input = cmd.contactGivenFamilyName)
                            RowField(name = "Handynummer", input = cmd.contactPhone)
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun step3( colMod: Modifier, cmd: NewPlayerCommand, onPlayerAdded: (cmd: NewPlayerCommand)->Unit ){
        val app = TmColors.App;
        val colorsBack = ButtonDefaults.buttonColors( containerColor = app.secondary , contentColor = app.secondaryText )
        val colors = ButtonDefaults.buttonColors( containerColor = app.primary , contentColor = app.primaryText )

        Column(colMod) {
            Row() {
                Column() {
                    OutlinedCard(modifier = colMod) {
                        Column(modifier = colMod) {
                            Row() {
                                Checkbox(checked = cmd.trial.value, onCheckedChange = { cmd.trial.value = it })
                                Text("Schnupperer")
                            }
                            Row() {
                                RowField(name = "Team", input = cmd.team)
                            }
                        }
                    }
                }
            }
            Row() {
                Button( colors=colors, modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp), onClick = { onPlayerAdded(cmd) } ) {
                    Text(text = "SAVE")
                }
            }
        }
    }

}










@Composable
@Preview
fun AddPlayerScreenPreview(){
    AddPlayerScreen().Screen({},{})
}


