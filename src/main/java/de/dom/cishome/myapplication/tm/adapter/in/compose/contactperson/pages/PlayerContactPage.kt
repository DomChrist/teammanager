package de.dom.cishome.myapplication.tm.adapter.`in`.compose.contactperson.pages

import android.graphics.drawable.shapes.Shape
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.dom.cishome.myapplication.compose.shared.MyColorTheme
import de.dom.cishome.myapplication.compose.shared.TmColors
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.contactperson.components.ContactModalSheet
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.contactperson.model.PlayerContactsViewModel
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.shared.Tm
import de.dom.cishome.myapplication.tm.application.domain.contactperson.model.ContactModel
import de.dom.cishome.myapplication.tm.application.services.ContactPersonApplicationService
import de.dom.cishome.myapplication.ui.MainControl
import kotlin.contracts.contract

class PlayerContactPage( val app: ContactPersonApplicationService = ContactPersonApplicationService.inject() ) {

    private var color: MyColorTheme = TmColors.App;

    @Composable
    fun Screen(
        playerId: String,
        control: MainControl,
        model: PlayerContactsViewModel = viewModel( factory = PlayerContactsViewModel.Factory(playerId) )
    ){
        val contacts = remember{ mutableStateOf<List<ContactModel>>(emptyList()) }
        model.data.observeForever { contacts.value = it }

        if( contacts.value.isEmpty() ){
            Tm.components().Loading()
        } else {
            this.content( contacts.value , control );
        }

    }

    @Composable
    internal fun content(value: List<ContactModel>, control: MainControl) {
        Scaffold(
            topBar = {Tm.components().TmTopBar(title="Kontaktpersonen" , showBackArrow = true , clickModel = control )},
            content = {Box(Modifier.padding(it)){body( value , control )}}
            )
    }

    @Composable
    private fun body(value: List<ContactModel>, control: MainControl){
        LazyColumn(modifier = Modifier.padding(PaddingValues(5.dp , 25.dp))){
            items( value ){
                ContactPersonCard( it )
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    private fun ContactPersonCard(contactModel: ContactModel) {
        var showBottomSheet = remember{ mutableStateOf(false) }
        ContactModalSheet(model = contactModel, state = showBottomSheet)

        Box(Modifier.padding(8.dp)){
            Row( modifier = Modifier
                .padding(2.dp, 2.dp)
                .clickable { showBottomSheet.value = true }  ){
                Column(Modifier.weight(2f)) {
                    Icon( Icons.Filled.Person , contentDescription = "" ,
                        tint=color.secondary,
                        modifier= Modifier
                            .size(45.dp)
                            .background(color = color.primary, shape = RoundedCornerShape(75.dp)) )
                }
                Column(
                    Modifier
                        .weight(6f)
                        .padding(5.dp, 0.dp)) {
                    Row(){
                        Text( fontWeight = FontWeight.Bold, text="${contactModel.full()}")
                    }
                    Row(){
                        Text("${contactModel.phone}")
                    }
                }
                Column(Modifier.weight(1f)) {
                    Icon( Icons.Filled.Menu , contentDescription = "" )
                }

            }

        }

    }

}

@Composable
@Preview
private fun Preview(){
    var list = listOf<ContactModel>(
        ContactModel("1234" , "Dominik" , "Christ" , "01785237162"),
        ContactModel("1234" , "Dominik" , "Christ" , "01785237162"),
        ContactModel("1234" , "Dominik" , "Christ" , "01785237162")
    )
    PlayerContactPage().content( list , MainControl({},{},{}) );
}