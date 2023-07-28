package de.dom.cishome.myapplication.compose.player.pages

import android.os.VibrationEffect
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import de.dom.cishome.myapplication.compose.player.service.Player
import de.dom.cishome.myapplication.compose.player.service.PlayerService
import java.lang.Exception
import java.time.Instant
import java.time.LocalDate
import java.time.Period
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.UUID


@Composable
fun NewPlayerPage( playerService: PlayerService, nav: NavController){

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
        topBar = {AddPlayerTopBar(service = playerService, cmd = cmd, nav = nav )}
    ) {
        Box(modifier = Modifier.padding(it)){
            Body( playerService = playerService , nav = nav , cmd )
        }
    }

}

@Composable
private fun Body( playerService: PlayerService, nav: NavController, cmd: NewPlayerCommand ){

    val step = remember{ mutableStateOf(0) };

    val colMod = Modifier
        .fillMaxWidth()
        .padding(10.dp)


    when (cmd.step.value) {
        0 -> {
            Column(modifier = colMod) {
                Row(){
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
                        Row(){
                            Button( modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp), onClick = { cmd.inc() }) {
                                Text(text = "NEXT")
                            }
                        }

                    }
                }
            }
        }
        1 -> {
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
                        Row(){
                            Button( modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp), onClick = { cmd.inc() }) {
                                Text(text = "NEXT")
                            }
                        }
                    }
                }
            }
        }
        2 -> {
            
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
                    Button( modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp), onClick = { go(playerService , cmd , nav ) }) {
                        Text(text = "SAVE")
                    }
                }
            }
            

        }

    }



}



@Composable
fun RowField( name: String, input: MutableState<TextFieldValue>){
    Row(
        Modifier
            .padding(PaddingValues(5.dp))
            .fillMaxWidth() ) {
        Column() {
            TextField( label = {Text(name)}, placeholder = {Text(name)},
                singleLine=true, modifier = Modifier.fillMaxWidth(), maxLines = 1,
                value = input.value, onValueChange = { input.value = it})
        }
    }
}

fun go( playerService: PlayerService, cmd: NewPlayerCommand, nav: NavController ){
    var p = Player(
        UUID.randomUUID().toString(),
        cmd.givenName.value.text,
        cmd.familyName.value.text,
        cmd.jahrgang.value,
        cmd.team.value.text,
        cmd.trial.value
    )
    playerService.add( p );
    VibrationEffect.createPredefined( VibrationEffect.EFFECT_CLICK )
    nav.navigateUp()
}

data class NewPlayerCommand(
    var givenName: MutableState<TextFieldValue>,
    var familyName: MutableState<TextFieldValue>,
    var jahrgang: MutableState<LocalDate>,
    var contactGivenName: MutableState<TextFieldValue>,
    var contactGivenFamilyName: MutableState<TextFieldValue>,
    var contactPhone: MutableState<TextFieldValue>,
    var trial: MutableState<Boolean>,
    var team: MutableState<TextFieldValue>,
    var step: MutableState<Int> = mutableStateOf(0)
    ){
        fun inc(){
            this.step.value = this.step.value.inc();
        }
    }

@Composable
fun JahrGang( value: MutableState<LocalDate> ){
    var alter = remember { mutableStateOf(TextFieldValue()) }
    var input = remember{ mutableStateOf(TextFieldValue()) }

    var showDatePicker = remember{ mutableStateOf(false) }

    Row( Modifier.padding(PaddingValues(5.dp )) ) {
        ReadonlyTextField(value = TextFieldValue(value.value.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))), onValueChange = {input.value = it},
            placeholder = { Text(text = "Geburtsdatum")},
            label = { Text(text = "Geburtsdatum")},
            onClick = { showDatePicker.value = true })
        TextField( enabled=false, value = alter.value, onValueChange = { alter.value = it})
    }

    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = 1578096000000, initialDisplayMode = DisplayMode.Input )
    if( showDatePicker.value  ){
        DatePickerDialog(onDismissRequest = {  }, confirmButton = {
            Button(onClick = {
                val date = Instant.ofEpochMilli(datePickerState.selectedDateMillis!!).atZone(ZoneId.systemDefault()).toLocalDate()
                input.value = TextFieldValue( date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) )
                var alt = Period.between(date , LocalDate.now() ).years;
                alter.value = TextFieldValue( alt.toString() )
                showDatePicker.value = false
                value.value = date;
            }) {
                Text("OK")
            }
        }) {
            DatePicker(
                state = datePickerState,
                modifier = Modifier.padding(16.dp),
                showModeToggle = true,
                dateFormatter = DatePickerFormatter("dd.MM.yyyy" , "dd.MM.yyyy" , "dd.MM.yyyy")
            )
        }
    }



}

@Composable
fun AddPlayerTopBar( service: PlayerService, cmd: NewPlayerCommand, nav: NavController ){
    MediumTopAppBar(
        title = {Text("Spieler hinzufügen")},
        navigationIcon = {
            IconButton(onClick = { nav.navigateUp() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Localized description"
                )
            }
        },
        actions = {
            IconButton(onClick = { go(service, cmd, nav) }) {
                Icon(
                    imageVector = Icons.Filled.Send,
                    contentDescription = "Localized description"
                )
            }
        },
    )
}


@Composable
@Preview
fun NewPlayerPreview(){

    var service = PlayerService( null );
    var nav = rememberNavController()

    NewPlayerPage(playerService = service, nav = nav);
}

@Composable
fun ReadonlyTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    label: @Composable () -> Unit,
    placeholder: @Composable () -> Unit
) {
    Box {
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier,
            placeholder = placeholder,
            label = label
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .alpha(0f)
                .clickable(onClick = onClick),
        )
    }
}