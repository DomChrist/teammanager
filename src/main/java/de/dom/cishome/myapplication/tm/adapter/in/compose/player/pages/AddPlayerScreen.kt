package de.dom.cishome.myapplication.tm.adapter.`in`.compose.player.pages

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import de.dom.cishome.myapplication.R
import de.dom.cishome.myapplication.compose.player.pages.JahrGang
import de.dom.cishome.myapplication.compose.player.pages.NewPlayerCommand
import de.dom.cishome.myapplication.compose.player.pages.RowField
import de.dom.cishome.myapplication.compose.shared.TmColors
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.player.components.TmDatePicker
import de.dom.cishome.myapplication.tm.application.domain.player.model.PlayersTeamModel
import java.time.LocalDate


class AddPlayerScreen(val team: PlayersTeamModel.Team? = null) {

    private lateinit var cmd: NewPlayerCommand;
    private lateinit var onBackClick: () -> Unit
    private lateinit var onPlayerAdded: (cmd: NewPlayerCommand) -> Unit

    private var FIRST_STEP = 0;
    private var MAX_STEP = 2;



    @Composable
    fun Screen(
        onPlayerAdded: (cmd: NewPlayerCommand) -> Unit,
        onBackClick: () -> Unit,
        startStep: Int = FIRST_STEP
    ){
        this.onPlayerAdded = onPlayerAdded;
        this.onBackClick = onBackClick;
        this.cmd = NewPlayerCommand(
            remember { mutableStateOf(TextFieldValue()) },
            remember { mutableStateOf(TextFieldValue()) },
            remember { mutableStateOf(LocalDate.now()) },
            remember { mutableStateOf(mutableListOf()) },
            remember { mutableStateOf(false) },
            remember { mutableStateOf(TextFieldValue()) },
            remember { mutableStateOf(startStep) }
        )
        /*
        Scaffold(
            topBar = { header(onBackClick) },
            bottomBar = { bottomBar( cmd , onPlayerAdded ) }
        ) {
            Box(modifier = Modifier.padding(it)){
                Body(cmd,onPlayerAdded)
            }
        }
         */
        layout( cmd )
    }


    @Composable
    private fun layout(cmd: NewPlayerCommand) {
        Scaffold(
            topBar = {},
            content = {content( it , cmd )}
        )
    }

    @Composable
    private fun content(it: PaddingValues, cmd: NewPlayerCommand) {
        var mod = Modifier.padding(it);
        var buttonColors = ButtonDefaults.buttonColors( TmColors.primaryColor , TmColors.secondaryColor, TmColors.primaryColor , TmColors.secondaryColor )
        Box( modifier=mod ){
            header()

            Column {
                Row {
                    Card( modifier=Modifier.padding(5.dp, 65.dp, 5.dp, 5.dp) ){
                        Row{
                            Column(Modifier.weight(2f)) {
                                RowField(name = "VORNAME", input = cmd.givenName)
                            }
                            Column(Modifier.weight(2f)) {
                                RowField(name = "NACHNAME", input = cmd.familyName)
                            }
                        }
                    }
                }
                        Row{
                            Column(Modifier.weight(2f)) {
                                Text(
                                    modifier= Modifier
                                        .fillMaxWidth()
                                        .padding(2.dp, 15.dp),
                                    fontWeight = FontWeight.Bold,
                                    text="TEILNAHMESTATUS",
                                    fontSize= TextUnit(4.1f, TextUnitType.Em),
                                    textAlign = TextAlign.Left
                                )
                            }

                            Column(Modifier.weight(2f)) {
                                var buttonModifier = Modifier.fillMaxWidth()
                                Card(modifier = buttonModifier){
                                    if( cmd.trial.value ){
                                        Button( colors=buttonColors, modifier = buttonModifier,onClick = { cmd.trial.value = false }) {
                                            Text("Schnupperer")
                                        }
                                    } else {
                                        Button( colors=buttonColors, modifier = buttonModifier, onClick = { cmd.trial.value = true }) {
                                            Text("Aktiv")
                                        }
                                    }
                                }
                            }
                        }
                        Row{
                            Column (Modifier.weight(2f))  {
                                Text(
                                    modifier= Modifier
                                        .fillMaxWidth()
                                        .padding(2.dp, 15.dp),
                                    fontWeight = FontWeight.Bold,
                                    text="GEBURTSTAG",
                                    fontSize= TextUnit(4.1f, TextUnitType.Em),
                                    textAlign = TextAlign.Left
                                )
                            }
                            Column(Modifier.weight(2f)) {
                                var show = remember{ mutableStateOf(false) }
                                var dateSet = remember{ mutableStateOf(false) }

                                if( dateSet.value ){
                                    Button( colors=buttonColors, modifier = Modifier.fillMaxWidth(),
                                        onClick = { show.value = true; }) {
                                        Text(text = "${cmd.jahrgang.value.toString()}")
                                    }
                                } else {
                                    Button( colors=buttonColors, modifier = Modifier.fillMaxWidth(),
                                        onClick = { show.value = true }) {
                                        Text(text = "Geburtsdatum")
                                    }
                                }

                                if( show.value ){
                                    var page = remember{ mutableStateOf(0) }
                                    var yearRange: Pair<Int,Int> = team?.ageGroup?.yearRange()
                                        ?: Pair( LocalDate.now().year-3 , LocalDate.now().year - 40 )
                                    Log.i("YearRange" , "${yearRange.first} .. ${yearRange.second}")
                                    ModalBottomSheet(onDismissRequest = { show.value = false }) {
                                        Box(modifier=Modifier.padding(25.dp)){
                                            TmDatePicker(page1 = page,
                                                yearRange = yearRange,
                                                onDateSelected ={
                                                cmd.jahrgang.value = it;
                                                show.value = false;
                                                dateSet.value = true
                                            } ) {

                                            }
                                        }

                                    }
                                }


                            }
                        }
                Row{
                    Divider()
                }
            }



        }
    }

    @Composable fun header(){
        Row{
            Column(modifier=Modifier.weight(1f)) {
                Icon(Icons.Filled.Close, modifier= Modifier
                    .size(55.dp)
                    .padding(0.dp, 10.dp)
                    .clickable { onBackClick() }, contentDescription = null)
            }
            Column(modifier=Modifier.weight(5f)) {
                Text(
                    modifier= Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 15.dp),
                    fontWeight = FontWeight.Bold,
                    text="NEUEN SPIELER REGISTRIEREN",
                    fontSize= TextUnit(4.4f, TextUnitType.Em),
                    textAlign = TextAlign.Center
                )
            }
            Column(modifier=Modifier.weight(1f)) {
                Icon(Icons.Filled.Check, tint=Color.Green, modifier= Modifier
                    .size(55.dp)
                    .padding(0.dp, 10.dp)
                    .clickable { onPlayerAdded(cmd) },  contentDescription = null)
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
        val saveColors = ButtonDefaults.buttonColors( containerColor = Color.Green , contentColor = Color.Black )


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
                    Button( colors=saveColors, modifier = Modifier
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
        val page = remember{ mutableStateOf(0) };

        Column(modifier = colMod) {
                Column() {
                    OutlinedCard(modifier = colMod) {
                        Column(colMod){
                            Text(text = "SPIELER")
                            Divider()
                            RowField(name = "Vorname", input = cmd.givenName)
                            RowField(name = "Nachname", input = cmd.familyName)



                            TmDatePicker( page, onDateSelected = {
                                cmd.jahrgang.value = it
                            }){
                                JahrGang(value = cmd.jahrgang)
                            }

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
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun step3( colMod: Modifier, cmd: NewPlayerCommand, onPlayerAdded: (cmd: NewPlayerCommand)->Unit ){
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
                                Text( modifier = Modifier.padding( 2.dp , 14.dp), text = "Schnupperer")
                            }
                            /*
                            Row() {
                                RowField(name = "Team", input = cmd.team)
                            }
                             */
                        }
                    }
                }
            }
            /*
            Row() {
                Button( colors=colors, modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp), onClick = { onPlayerAdded(cmd) } ) {
                    Text(text = "SAVE")
                }
            }
             */
        }
    }

}










@Composable
@Preview
fun AddPlayerScreenPreview(){
    AddPlayerScreen( null ).Screen(onPlayerAdded = {}, onBackClick = { /*TODO*/ } , startStep = 0)
}


