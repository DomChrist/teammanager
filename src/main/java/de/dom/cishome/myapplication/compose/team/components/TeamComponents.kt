package de.dom.cishome.myapplication.compose.team.components

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import de.dom.cishome.myapplication.compose.team.model.CreateTeamCommand

class TeamComponents {

    companion object Comp {

        @Composable
        fun CreateTeamDialog( onDismissRequest: ()->Unit , onCreate: (c: CreateTeamCommand)->Unit ){

            var club = remember { mutableStateOf(TextFieldValue("")) }

            Dialog( onDismissRequest = onDismissRequest , content = {

                var mod = Modifier.fillMaxWidth()

                Box(modifier = Modifier.background(Color.White)  )
                Column( modifier = Modifier
                    .background(Color.White)
                    .padding(15.dp) ) {
                    Row {
                        Column {
                            Row {
                                Text("Team anlegen")
                            }
                            Row {
                                OutlinedTextField( modifier=mod, placeholder = { Text("TEAM") },
                                    singleLine=true,
                                    value = club.value, onValueChange =  {club.value = it} )
                            }
                            Row( modifier = Modifier.padding(0.dp   ,5.dp)){
                                OutlinedButton( modifier = Modifier.fillMaxWidth(), onClick = onDismissRequest) {
                                    Text(text = "CLOSE")
                                }
                            }
                            Row( modifier = Modifier.padding(0.dp   ,5.dp)){
                                Button( modifier = Modifier.fillMaxWidth(), onClick = {
                                    var cmd = CreateTeamCommand( club.value.text )
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

@Composable
@Preview
fun TeamComponentsPreview(){
    TeamComponents.Comp.CreateTeamDialog(onDismissRequest = { /*TODO*/ }, onCreate ={})
}