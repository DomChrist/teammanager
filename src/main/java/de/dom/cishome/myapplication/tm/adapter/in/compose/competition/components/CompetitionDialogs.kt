package de.dom.cishome.myapplication.tm.adapter.`in`.compose.competition.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import de.dom.cishome.myapplication.compose.turnier.model.NewCompetitionCommand



class CompetitionDialogs{

    companion object Factory {

        @Composable
        fun CreateDialog( onDismissRequest: ()->Unit , onCreate: (c: NewCompetitionCommand)->Unit ){

            var club = remember { mutableStateOf(TextFieldValue("")) }
            var location = remember { mutableStateOf(TextFieldValue("")) }
            var date = remember { mutableStateOf(TextFieldValue("")) }
            var time = remember { mutableStateOf(TextFieldValue("")) }

            Dialog( onDismissRequest = onDismissRequest , content = {

                var mod = Modifier.fillMaxWidth()

                Box(modifier = Modifier.background(Color.White)  )
                Column( modifier = Modifier
                    .background(Color.White)
                    .padding(15.dp) ) {
                    Row {
                        Column {
                            Row {
                                Text("Turnier anlegen")
                            }
                            Row {
                                OutlinedTextField( singleLine=true, modifier=mod, label = { Text("Verein") }, value = club.value, onValueChange =  {club.value = it} )
                            }
                            Row {
                                OutlinedTextField( singleLine=true, modifier=mod, label = { Text("Ort") }, value = location.value, onValueChange =  {location.value = it} )
                            }
                            Row {
                                OutlinedTextField(singleLine=true, modifier=mod, label = { Text("Datum") }, value = date.value, onValueChange =  {date.value = it} )
                            }
                            Row {
                                OutlinedTextField(singleLine=true, modifier=mod, label = { Text("Zeit") }, value = time.value, onValueChange =  {time.value = it} )
                            }
                            Row( modifier = Modifier.padding(0.dp   ,5.dp)){
                                OutlinedButton( modifier = Modifier.fillMaxWidth(), onClick = onDismissRequest) {
                                    Text(text = "CLOSE")
                                }
                            }
                            Row( modifier = Modifier.padding(0.dp   ,5.dp)){
                                Button( modifier = Modifier.fillMaxWidth(), onClick = {
                                    var cmd = NewCompetitionCommand(
                                        club.value.text,
                                        location.value.text,
                                        date.value.text,
                                    )
                                    onCreate(cmd)
                                }) {
                                    Text(text = "ANLEGEN")
                                }
                            }
                        }
                    }

                }
            })


        }

    }

}