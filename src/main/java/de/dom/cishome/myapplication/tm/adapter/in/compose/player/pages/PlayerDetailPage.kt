package de.dom.cishome.myapplication.tm.adapter.`in`.compose.player.pages

import android.os.Environment
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ChipBorder
import androidx.compose.material3.ChipColors
import androidx.compose.material3.Divider
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import de.dom.cishome.myapplication.R
import de.dom.cishome.myapplication.compose.home.TmComponents
import de.dom.cishome.myapplication.compose.player.component.PlayerDetailNavigation
import de.dom.cishome.myapplication.compose.player.pages.PlayerNavItem
import de.dom.cishome.myapplication.compose.shared.PlayerFileHelper
import de.dom.cishome.myapplication.compose.shared.TmColors
import de.dom.cishome.myapplication.compose.shared.shotPlayerImage
import de.dom.cishome.myapplication.tm.application.domain.model.Player
import java.io.File
import java.lang.Exception
import java.time.LocalDate

@Composable
fun PlayerDetailPage(p: Player, items: List<PlayerNavItem>, onBackClick: () -> Unit, ){

    layout(p, onBackClick, items)
}


@Composable
private fun layout(p: Player, onBackClick: () -> Unit, items: List<PlayerNavItem>){
    var COMP = TmComponents();
    var counter = remember{ mutableStateOf(0) }


    if( p.trial ){
        BottomSheetScaffold( sheetContent = {
            BottomSheet( counter )
        }) {
            Scaffold( topBar = {header( onBackClick )} ) {
                Box(Modifier.padding(it)){
                    PlayerBox(p, listOf())
                    PlayerDetail(p = p, items = items)
                }
            }
        }
    } else {
        Scaffold( topBar = {header( onBackClick )} ) {
            Box(Modifier.padding(it)){
                PlayerBox(p, listOf())
                PlayerDetail(p = p, items = items)
            }
        }
    }


}

@Composable
private fun header(onBackClick: () -> Unit) {
    var title = "Spieleransicht"
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
private fun PlayerBox(p: Player, items: List<PlayerNavItem>){
    var max = Modifier.fillMaxWidth();
    var colors = AssistChipDefaults.assistChipColors(containerColor = TmColors.App.primary , labelColor = TmColors.App.primaryText)
    var assistBox = Modifier
        .fillMaxWidth()
        .padding(10.dp);

    Column(max) {
        Row(max) {
            Column(Modifier.weight(2f)) {
                Row {
                    AssistChip( modifier=assistBox, colors=colors, onClick = {  }, label = {Text(text = "${p.dateOfBirth?.year}")} )
                }
            }
            Column(Modifier.weight(5f)) {
                Row(modifier = Modifier.padding(0.dp , 25.dp)){
                    PlayerImage(p = p)
                }
            }
            Column(Modifier.weight(2f)) {
                Row {
                    var trialChipColor: ChipColors;
                    var border: ChipBorder;
                    if( !p.trial ){
                        trialChipColor = AssistChipDefaults.assistChipColors( containerColor = Color.Red, labelColor = Color.White );
                        border = AssistChipDefaults.assistChipBorder(borderColor = Color.Red)
                    } else {
                        trialChipColor = AssistChipDefaults.assistChipColors( containerColor = Color.Green, labelColor = Color.White );
                        border = AssistChipDefaults.assistChipBorder(borderColor = Color.Green)
                    }
                    var txt = if(p.trial) "AKTIV" else "TRIAL";
                    AssistChip( modifier=assistBox, colors=trialChipColor, border = border, onClick = {  }, label = {Text(text = txt)} )
                }
            }
        }
    }

    Box(){

    }


}

@Composable
private fun PlayerDetail(p: Player, items: List<PlayerNavItem>) {
    var COLORS = TmColors.App;
    var assistBox = Modifier
        .fillMaxWidth()
        .padding(10.dp);

    Box(Modifier.padding(5.dp , 150.dp)){

        Row(){
            Column(modifier = Modifier.weight(2f)) {
                var t = if( p.team == null) "N.A." else p.team.uppercase();
                AssistChip( modifier = assistBox, onClick = {  }, label = { Text("${t}" , textAlign = TextAlign.Center) } )
            }
            Column(modifier = Modifier.weight(5f)) {}
            Column(modifier = Modifier.weight(2f)) {
                AssistChip( modifier = assistBox, onClick = {  }, label = { Icon(Icons.Filled.Share,"") } )
            }
        }

        Row( Modifier.padding(0.dp , 55.dp) ){
            Column() {
                Row {
                    Text( modifier = Modifier.fillMaxWidth(),
                        color = COLORS.primary, fontSize= TextUnit(5.5f, TextUnitType.Em),
                        fontWeight = FontWeight.Bold,
                        text = p.fullName(), textAlign = TextAlign.Center )
                }
                Row(){
                    Divider()
                }
                Row(){
                    PlayerDetailNavigation(items =  items )
                }
            }
        }

    }

}

@Composable
private fun BottomSheet(counter: MutableState<Int>) {
    Box( modifier = Modifier.padding(0.dp , 15.dp) ) {
        Row(){
            Column() {
                Row( modifier = Modifier.padding(15.dp , 15.dp) ) {
                    Column(modifier = Modifier.weight(1f)) {
                        FilledIconButton( modifier = Modifier
                            .padding(5.dp, 0.dp)
                            .fillMaxWidth(), onClick = { counter.value = counter.value.inc() }) {
                            Icon( Icons.Filled.Clear , "" )
                        }
                    }
                    Column(modifier = Modifier.weight(3f)) {
                        Button( modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth(), onClick = { counter.value = counter.value.inc() }) {
                            Text(text = "# ${counter.value}")
                        }
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        FilledIconButton( modifier = Modifier
                            .padding(5.dp, 0.dp)
                            .fillMaxWidth(), onClick = { counter.value = counter.value.dec() }) {
                            Icon(Icons.Filled.Clear , "")
                        }
                    }
                }
                Row( modifier = Modifier.padding( 15.dp , 15.dp) ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Button( modifier = Modifier.fillMaxWidth(), onClick = { /*TODO*/ }) {
                            Text("BEENDEN")
                        }
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Button( modifier = Modifier.fillMaxWidth(), onClick = { /*TODO*/ }) {
                            Text("ZURÜCKSETZEN")
                        }
                    }
                }
            }
        }


    }
}



@Composable
private fun PlayerImage( p: Player? ){
    var id = p?.id ?: "unkown";
    var c = LocalContext.current;

    var file: File? = null;
    val playerImage: Boolean = if( p != null ){
        try{
            file = File(PlayerFileHelper().playerDir(id),"main.jpg"); File( Environment.getExternalStoragePublicDirectory("documents").absolutePath + "/tm/players/${id}/main.jpg" )
            file != null && file.exists()
        }catch ( e: Exception){
            false;
        }
    } else {
        false;
    }

    var m = Modifier
        .size(130.dp)
        .clip(CircleShape)
        .border(2.dp, Color.Gray, CircleShape);

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White.copy(alpha = 0.6f))
            .clickable { shotPlayerImage(p!!.id, c) }
            .padding(5.dp)) {

        if( playerImage ){
            AsyncImage(
                model = file,
                placeholder = painterResource( R.drawable.club ),
                contentScale = ContentScale.FillBounds,
                modifier = m
                ,
                contentDescription = null,
            )
        } else {
            Image(painter = painterResource(id = R.drawable.club),
                contentScale = ContentScale.FillBounds,
                contentDescription = "" ,
                modifier = m
            )
        }

    }

}



@Composable
@Preview
fun PlayerDetailPagePreview(){
    PlayerDetailPage(
        p = Player("1234","Dominik","Christ" , LocalDate.of(1988,10,1) , "Bambini"),
        listOf()
    ) {}

}




