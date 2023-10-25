package de.dom.cishome.myapplication.tm.adapter.`in`.compose.player.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.window.Dialog
import de.dom.cishome.myapplication.tm.application.domain.player.model.PlayerContactDetail

@Composable
fun ContactDataDialog( onDismissRequest: () -> Unit = {}, onNewContact: ( d: PlayerContactDetail ) -> Unit ){

    var descriptionTextField = remember{ mutableStateOf( TextFieldValue("") ) };
    var valueTextField = remember{ mutableStateOf( TextFieldValue("") ) };

    Dialog( onDismissRequest , content = {

        Column {
            Row{
                Column {
                    Text("Beschreibung")
                }
                Column {
                    TextField(value = descriptionTextField.value, onValueChange = { descriptionTextField.value = it}  )
                }
            }
            Row{
                Column {
                    Text("Wert")
                }
                Column {
                    TextField(value = valueTextField.value, onValueChange = {valueTextField.value = it})
                }
            }
            Row{
                Column {
                    Button(onClick = onDismissRequest) {
                        Text("ABBRUCH")
                    }
                }
                Column {
                    Button(
                        onClick = { onNewContact( PlayerContactDetail("", descriptionTextField.value.text , valueTextField.value.text) ) }
                    ){
                        Text("SPEICHERN")
                    }
                }
            }
        }

    });


}