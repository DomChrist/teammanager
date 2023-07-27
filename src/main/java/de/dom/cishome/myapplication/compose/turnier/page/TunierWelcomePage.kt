package de.dom.cishome.myapplication.compose.turnier.page

import android.content.Context
import android.os.CombinedVibration
import android.os.VibrationEffect
import android.os.VibratorManager
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import de.dom.cishome.myapplication.R
import de.dom.cishome.myapplication.compose.home.TmComponents
import de.dom.cishome.myapplication.compose.shared.TmColors
import de.dom.cishome.myapplication.compose.turnier.model.CompetitionModel
import de.dom.cishome.myapplication.compose.turnier.model.CompetitionRepository
import de.dom.cishome.myapplication.compose.turnier.model.NewCompetitionCommand
import java.util.UUID


@Composable
fun TurnierWelcomePage( nav: NavController ){
    var ctx = LocalContext.current
    val haptic = LocalHapticFeedback.current
    var dialogState = remember{ mutableStateOf(false) }
    val repository = CompetitionRepository()
    var list = remember{ mutableStateOf( repository.loadAll() ) }
    val onCreate: (c: NewCompetitionCommand)->Unit = {
        var model = CompetitionModel( UUID.randomUUID().toString() , it.club , it.place , it.date )
        repository.write( model )
        list.value = list.value.plus( model )
        dialogState.value = false
        var v = ctx.getSystemService( Context.VIBRATOR_MANAGER_SERVICE ) as VibratorManager
        var c = CombinedVibration.startParallel().addVibrator( 0 , VibrationEffect.createOneShot(2000 ,5) ).combine()
        v.vibrate( c );
    }

    val onOpen: (id: String)->Unit = {
        Log.i("TurnierWelcomePage.onOpen","on open")

        nav.navigate("competition/detail/${it}")
    }


    MaterialTheme() {
        content(nav = nav , dialogState, list, onCreate, onOpen )
    }
}


@Composable
private fun content(
    nav: NavController,
    dialogState: MutableState<Boolean>,
    list: MutableState<List<CompetitionModel>>,
    onCreate: (c: NewCompetitionCommand) -> Unit,
    onOpen: (id: String) -> Unit
){
    var c = TmColors.Competition;
    if( dialogState.value ){
        createDialog( onDismissRequest = { dialogState.value = false }, onCreate = onCreate )
    }

    Scaffold(topBar = { TmComponents().stage1HeaderCustomize(title = "Turnier", nav = nav , c) },
    floatingActionButtonPosition = FabPosition.Center, floatingActionButton = {
            FloatingActionButton( shape = RoundedCornerShape(100.dp), containerColor = c.primary, onClick = { dialogState.value = true }){
                Text("+" , color = c.primaryText)
            }
        }) {
        it -> Box(modifier = Modifier
        .padding(it)
        .fillMaxSize()){ body( list, onOpen ) }
    }



}

@Composable
private fun body(list: MutableState<List<CompetitionModel>>, onOpen: (id: String) -> Unit) {

    Box(modifier = Modifier.fillMaxSize() ){
        Image( contentScale=ContentScale.FillBounds, modifier= Modifier
            .fillMaxWidth()
            .height(200.dp), painter = painterResource(id = R.drawable.tuniert), contentDescription = "9999" )
    }

    Box(modifier = Modifier
        .padding(25.dp, 50.dp, 25.dp, 80.dp)
        .fillMaxSize()
        .background(Color(R.color.competition_primary).copy(0.01f))) {

        LazyColumn{
            items( list.value.size, key = {it} ){
                val model = list.value[it]
                Row(
                    Modifier
                        .fillMaxWidth()
                        .clickable(onClick = {
                            onOpen!!(model.id)
                        }) ) {
                    CompetitionView(c = model, onOpen)
                }
            }
        }

    }


    Column(Modifier.fillMaxWidth()) {
        Row(Modifier.fillMaxWidth()){
        }
        Row {
        }
    }

    Row( modifier = Modifier.fillMaxWidth() ){
        Column(modifier = Modifier.fillMaxWidth()) {
            Text("test")

        }
    }

}

@Composable
private fun CompetitionView(c: CompetitionModel, onOpen: (id: String) -> Unit){
    var colors = CardDefaults.cardColors(containerColor = TmColors.Competition.primary, contentColor = Color.White)
    val paddingModifier = Modifier.padding(10.dp)
    val clickModifier = Modifier
        .clickable { onOpen(c.id) }
        .fillMaxWidth()
        .padding(15.dp)
    Card(
        colors=colors,
        modifier = clickModifier ) {
            Column( modifier = paddingModifier) {
            Text( fontWeight = FontWeight.ExtraBold, fontSize = TextUnit(5f, TextUnitType.Em) , text = "${c.club}")
            Text(fontWeight = FontWeight.Light , fontSize = TextUnit(4f,TextUnitType.Em) , text = "${c.location}")
        }
    }
}

@Composable
private fun createDialog( onDismissRequest: ()->Unit , onCreate: (c: NewCompetitionCommand)->Unit ){

    var club = remember { mutableStateOf(TextFieldValue("")) }
    var location = remember { mutableStateOf(TextFieldValue("")) }
    var date = remember { mutableStateOf(TextFieldValue("")) }

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
                        OutlinedTextField( modifier=mod, label = {Text("Verein")}, value = club.value, onValueChange =  {club.value = it} )
                    }
                    Row {
                        OutlinedTextField( modifier=mod, label = {Text("Ort")}, value = location.value, onValueChange =  {location.value = it} )
                    }
                    Row {
                        OutlinedTextField(modifier=mod, label = {Text("Datum")}, value = date.value, onValueChange =  {date.value = it} )
                    }
                    Row( modifier = Modifier.padding(0.dp   ,5.dp)){
                        OutlinedButton( modifier = Modifier.fillMaxWidth(), onClick = onDismissRequest) {
                            Text(text = "CLOSE")
                        }
                    }
                    Row( modifier = Modifier.padding(0.dp   ,5.dp)){
                        Button( modifier = Modifier.fillMaxWidth(), onClick = {
                            var cmd = NewCompetitionCommand(club.value.text , location.value.text , date.value.text)
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


@Preview
@Composable
fun TunierWelcomePagePreview(){

    var nav = rememberNavController()
    var dialogState = remember{ mutableStateOf(false) }
    var list = remember{ mutableStateOf( listOf<CompetitionModel>(
        CompetitionModel(UUID.randomUUID().toString(),"Kettig" , "Kettig" , "31.12.2023"),
        CompetitionModel(UUID.randomUUID().toString(),"Sankt Sebastian" , "Sankt Sebastian" , "10.01.2024")
    ) ) }

    content(nav = nav, dialogState, list, {}, {})

}


