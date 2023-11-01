package de.dom.cishome.myapplication.tm.adapter.compose.player.contact

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.dom.cishome.myapplication.compose.shared.TmColors
import de.dom.cishome.myapplication.tm.application.domain.player.model.PlayerContactDetail

@Composable
fun AddContactDataComponent( onDismissRequest: () -> Unit = {}, onNewContact: ( d: PlayerContactDetail) -> Unit ){
    var descriptionTextField = remember{ mutableStateOf( TextFieldValue("") ) };
    var valueTextField = remember{ mutableStateOf( TextFieldValue("") ) };

    var closeButtonColors = ButtonDefaults.buttonColors( Color.Transparent )
    var saveButtonColors = ButtonDefaults.buttonColors( Color.Green )

    Box{
        Row{
            Column(modifier= Modifier.weight(6f)) {
                // Text("Kontaktdaten" , textAlign = TextAlign.Center , modifier= Modifier.fillMaxWidth())
            }
        }
        Row{
            Column(modifier= Modifier.weight(6f)) {
                Button(onClick = onDismissRequest , colors=closeButtonColors) {
                    Text("ABBRUCH" , color = TmColors.primaryColor )
                }
            }
            Column(modifier= Modifier.weight(6f)) {
                Button(
                    colors=saveButtonColors,
                    modifier = Modifier.align(Alignment.End),
                    onClick = { onNewContact( PlayerContactDetail("", descriptionTextField.value.text , valueTextField.value.text) ) }
                ){
                    Text("SPEICHERN")
                }
            }
        }
    }

    Box(modifier= Modifier.padding(2.dp,30.dp)){
        Column {


            Row(modifier = Modifier.padding(2.dp,10.dp)){
                Column {
                    Text("Beschreibung")
                }
            }
            Row{
                Column {
                    TextField( label = { Text("Mama Handy") }, placeholder = { Text("Mama Handy") },
                        singleLine=true, modifier = Modifier.fillMaxWidth(), maxLines = 1,
                        value = descriptionTextField.value, onValueChange = { descriptionTextField.value = it})
                }
            }
            Row(modifier = Modifier.padding(2.dp,10.dp)){
                Column {
                    Text("Wert")
                }
            }
            Row{
                Column {
                    TextField( label = { Text("XXXX - XXXXX XXXXX") }, placeholder = { Text("XXXX - XXXXX XXXXX") },
                        singleLine=true, modifier = Modifier.fillMaxWidth(), maxLines = 1,
                        value = valueTextField.value, onValueChange = { valueTextField.value = it})
                }
            }
        }
    }



}

@Composable
@Preview
private fun preview(){
    AddContactDataComponent( {},{} )
}
