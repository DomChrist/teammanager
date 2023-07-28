package de.dom.cishome.myapplication.compose.player.component

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ChipBorder
import androidx.compose.material3.ChipColors
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import de.dom.cishome.myapplication.R
import de.dom.cishome.myapplication.compose.home.TmComponents
import de.dom.cishome.myapplication.compose.player.pages.PlayerNavItem
import de.dom.cishome.myapplication.compose.player.service.Player
import de.dom.cishome.myapplication.compose.shared.PlayerFileHelper
import de.dom.cishome.myapplication.compose.shared.TmColors
import de.dom.cishome.myapplication.compose.shared.shotPlayerImage
import java.io.File
import java.lang.Exception
import java.time.LocalDate
import java.util.UUID

@Composable
fun PlayerDetailViewLayout(p: Player, items: List<PlayerNavItem>, nav: NavController){

    val tm = TmComponents();

    content(p = p, nav = nav, items)

}

@Composable
private fun content(p: Player, nav: NavController, items: List<PlayerNavItem>){
    var COMP = TmComponents();

    Scaffold( topBar = {COMP.stage1HeaderCustomize(title = "${p.fullName()}", nav = nav, color = TmColors.App)} ) {
        Box(Modifier.padding(it)){
            PlayerBox(p,items)
            PlayerDetail( p , items )
        }
    }
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
                AssistChip( modifier = assistBox, onClick = {  }, label = { Text("${p.team.uppercase()}" , textAlign = TextAlign.Center) } )
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
                        color = COLORS.primary, fontSize=TextUnit(5.5f,TextUnitType.Em),
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
fun Tags( p: Player ){
    Row(modifier = Modifier.fillMaxWidth()) {

        var assistBox = Modifier
            .fillMaxWidth()
            .padding(10.dp);

        Column( modifier = Modifier.weight(1f)){
            AssistChip( modifier=assistBox, onClick = {
            }, label = {Text(text = "Bambini")} )
        }
        Column( modifier = Modifier.weight(1f)){
            AssistChip( modifier=assistBox, onClick = {  }, label = {Text(text = "Tus Kettig")} )
        }
        Column( modifier = Modifier.weight(1f)){
            AssistChip( modifier=assistBox, onClick = {}, label = {Text( textAlign = TextAlign.Center, text = p.dateOfBirth.year.toString())} )
        }
    }
}

@Composable
fun PlayerDetailNavigation(items: List<PlayerNavItem>) {
    Row( modifier = Modifier.padding(10.dp , 10.dp) ) {
        Column() {
            LazyColumn(){
                items(items){
                    NavItem(main = it.first, second = it.second , onClick = it.click );
                }
            }
        }
    }
}

@Composable
fun PlayerName(fullName: String) {
    Row( modifier=Modifier.padding(25.dp) ) {
        Column() {
            Text( modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center ,
                fontSize = TextUnit(5f, TextUnitType.Em),
                text = fullName )
        }
    }

}

@Composable fun topBar( nav: NavController ){
    Box() {
        IconButton(onClick = { nav.navigateUp() }) {
            Icon(Icons.Filled.ArrowBack , "",  modifier = Modifier.size(35.dp) )
        }
    }
}




@Composable
fun PlayerImage( p: Player? ){
    var id = p?.id ?: "unkown";
    var c = LocalContext.current;

    var file: File? = null;
    val playerImage: Boolean = if( p != null ){
        try{
            file = File(PlayerFileHelper().playerDir(id),"main.jpg"); File( Environment.getExternalStoragePublicDirectory("documents").absolutePath + "/tm/players/${id}/main.jpg" )
            file != null && file.exists()
        }catch ( e: Exception ){
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


@Composable fun NavItem( main: String , second: String? , onClick: ()->Unit){
    var mod = Modifier.padding(5.dp);
    if( onClick != null ){
        mod = mod.clickable { onClick.invoke() }
    }

    Row(modifier = mod  ){
        Column(modifier = mod.weight(4f)) {
            Text(text = main, fontWeight = FontWeight.Bold )
        }
        Column(modifier = mod.weight(2f)) {
            Text( text = second?.lowercase() ?: "")
        }
        Column(modifier = mod.weight(1f)) {
            Icon(Icons.Filled.KeyboardArrowRight, contentDescription = null)
        }
    }
}

@Composable
@Preview
fun PlayerDetailViewComponentPreview(){
    val controller = rememberNavController()
    val p = Player(UUID.randomUUID().toString(),"Dominik" , "Christ" , LocalDate.now() , "Bambini",true)

    var nav1 = PlayerNavItem("Eigenschaften" , "" , click = {})

    PlayerDetailViewLayout( p , listOf<PlayerNavItem>(nav1), nav =  controller )
}

