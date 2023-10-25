package de.dom.cishome.myapplication.tm.adapter.`in`.compose.contactperson.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import de.dom.cishome.myapplication.compose.shared.TmDevice
import de.dom.cishome.myapplication.compose.shared.TmDeviceShare
import de.dom.cishome.myapplication.tm.application.domain.contactperson.model.ContactModel
import de.dom.cishome.myapplication.tm.application.domain.player.model.PlayerContactDetail

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ContactModalSheet(model: PlayerContactDetail, state: MutableState<Boolean>){
    if( state.value ){
        ModalBottomSheet( onDismissRequest = { state.value = false }) {
            ModalBottomSheetLayout(sheetContent = {}) {
                content(model = model)
            }
        }
    }
}

@Composable
inline fun content(model: PlayerContactDetail){
    var ctx = LocalContext.current;
    Box( modifier=Modifier.fillMaxWidth() ){
        Column(Modifier.fillMaxWidth()) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 45.dp)){
                Text(text="${model.description}" , modifier=Modifier.fillMaxWidth(), fontSize= TextUnit(7f,
                    TextUnitType.Em),
                    textAlign = TextAlign.Center)
                Divider()
            }
            Row(Modifier.fillMaxWidth()){
                Column(Modifier.weight(1f) , horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Filled.Call,
                        modifier = Modifier.size(30.dp).clickable { TmDevice.Call(ctx,model.value) },
                        contentDescription = "")
                }
                Column( Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Filled.Email, modifier = Modifier.size(30.dp).clickable { TmDeviceShare.share(ctx,model.value) }
                        , contentDescription = "")
                }
                Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Filled.Call, modifier = Modifier.size(30.dp) , contentDescription = "")
                }
            }
        }
    }
}


@Preview
@Composable
private fun Preview(){
    content(model = PlayerContactDetail("1235","Dominik" , "0178523762"))
}