package de.dom.cishome.myapplication.tm.adapter.compose.player.notes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AddNoteComponent(
    visible: Boolean = false,
    onSave: (title: String, txt: String)->Unit = { title: String, text: String -> },
    onDismiss: ()->Unit = {}
){

    if( visible ){

        ModalBottomSheet(onDismissRequest = { onDismiss() }) {
            AddNoteComponentContent( onSave,onDismiss )
        }

    }

}

@Composable
fun AddNoteComponentContent(onSave: (title: String, txt: String) -> Unit = {t1: String ,t2:String ->}, onDismiss: () -> Unit = {}) {
    var title = remember{ mutableStateOf(TextFieldValue("")) }
    var txt = remember{ mutableStateOf(TextFieldValue("")) }

    Column {
        Row {
            Column(Modifier.weight(2f)) {
                Button(onClick = {onDismiss()}) {
                    Icon(Icons.Filled.Clear , contentDescription = "")
                }
            }
            Column(Modifier.weight(6f)) {
                Text("PLAYER NOTE")
            }
            Column(Modifier.weight(2f)) {
                Button(onClick = { onSave(title.value.text , txt.value.text) }) {
                    Icon(Icons.Filled.Check , contentDescription = "")
                }
            }
        }
        Row{
            Column {
                Row {
                    Column {
                        Text( text = "TITLE")
                        Row{
                            TextField(value = title.value,
                                modifier=Modifier.fillMaxWidth(),
                                onValueChange = { title.value = it} )
                        }
                    }
                }
                Row {
                    Column {
                        Text("NOTE")
                        Row{
                            TextField(value = txt.value,
                                singleLine = false,
                                modifier=Modifier.fillMaxWidth(),
                                minLines = 3,
                                onValueChange = { txt.value = it} )
                        }
                    }
                }
            }
        }
    }

}

@Composable
@Preview
private fun PlayerNotePreview(){
    AddNoteComponentContent({ t:String,t2:String-> })
}

