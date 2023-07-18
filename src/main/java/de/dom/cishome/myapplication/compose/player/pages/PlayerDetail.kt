package de.dom.cishome.myapplication.compose.player.pages

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
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
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import de.dom.cishome.myapplication.R
import de.dom.cishome.myapplication.compose.Dimens
import de.dom.cishome.myapplication.compose.player.service.Player
import de.dom.cishome.myapplication.compose.player.service.PlayerService
import de.dom.cishome.myapplication.compose.shared.PlayerHelper
import okhttp3.OkHttpClient
import java.io.File
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Objects
import java.util.UUID


@Composable
@ExperimentalUnitApi
@ExperimentalPermissionsApi
fun PlayerDetailPage(
    navBackStackEntry: NavBackStackEntry,
    service: PlayerService,
    nav: NavController
){
    var _player = nav.previousBackStackEntry!!
        .savedStateHandle
        .get<MutableState<Player>>("player");

    var play = remember{ mutableStateOf(_player!!.value) }

    content( play.value , service, nav )

}



@Composable
@ExperimentalUnitApi
@ExperimentalPermissionsApi
private fun content(player: Player?, service: PlayerService, nav: NavController) {

    PlayerImage( player)

    if(player != null){
        PlayerDetail(p = player, service, nav)
    }

    header(nav = nav)
}

@Composable
fun header( nav: NavController ) {
    val listOfColors = listOf<Color>( Color.Red , Color.Black )
    val side1 = 100
    val side2 = 200


    var brush = Brush.radialGradient(
        0.0f to Color.Red,
        0.3f to Color.Green,
        1.0f to Color.Blue,
        center = Offset(side1 / 2.0f, side2 / 2.0f),
        radius = side1 / 2.0f,
        tileMode = TileMode.Repeated
    )
    val iconModifier = Modifier
        .sizeIn(
            maxWidth = Dimens.ToolbarIconSize,
            maxHeight = Dimens.ToolbarIconSize
        )
        .background(
            color = MaterialTheme.colorScheme.surface,
            shape = CircleShape
        )
    Row(){
        IconButton(
            onClick = { nav.navigateUp() },
            modifier = Modifier
                .padding(start = Dimens.ToolbarIconPadding)
                .background(brush = brush)
                .then(iconModifier)
        ) {
            Icon(
                Icons.Filled.ArrowBack,
                contentDescription = stringResource(id = androidx.core.R.string.status_bar_notification_info_overflow)
            )
        }
    }
}

@Composable
fun PlayerImage( p: Player? ){
    var id = p?.id ?: "unkown";
    var c = LocalContext.current;

    var file: File;
    try{
        file = File( Environment.getExternalStoragePublicDirectory("documents").absolutePath + "/tm/players/${id}/main.jpg" )
    }catch (e: Exception){
        file = File("test.jpg");
    }

    var m = Modifier
        .size(110.dp)
        .clip(CircleShape)
        .border(2.dp, Color.Gray, CircleShape);

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Red.copy(alpha = 0.6f))
            .padding(5.dp)) {

        if( file.exists() ){
            AsyncImage(
                model = file,
                placeholder = painterResource( R.drawable.club ),
                contentScale = ContentScale.FillBounds,
                modifier = m.clickable { openCamera(p!!, c) }
                ,
                contentDescription = null,
            )
        } else {
            Image(painter = painterResource(id = R.drawable.club),
                contentScale = ContentScale.FillBounds,
                contentDescription = "" ,
                modifier = m.clickable { openCamera(p!!, c) }
            )
        }

    }




}


@Composable
private fun PlayerDetailLayout(  text: @Composable Unit ) {

    Row(modifier = Modifier.padding(0.dp, 135.dp)) {
        Column {
            text

            Divider( modifier = Modifier.padding(0.dp , 12.dp))



        }


    }

}

@Composable private fun NavItem( main: String , second: String? , onClick: ()->Unit){
    var mod = Modifier.padding(5.dp);
    if( onClick != null ){
        mod = mod.clickable { onClick() }
    }

    Row(modifier = mod  ){
        Column(modifier = Modifier.weight(5f)) {
            Text(text = main, )
        }
        Column(modifier = Modifier.weight(2f)) {
            Text(text = second ?: "")
        }
        Column(modifier = Modifier.weight(1f)) {
            Icon(Icons.Filled.KeyboardArrowRight, contentDescription = null)
        }
    }
}

@Composable
@ExperimentalUnitApi
@ExperimentalPermissionsApi
fun PlayerDetail(p: Player, service: PlayerService, nav: NavController){
    var context = LocalContext.current;
    var player = remember{ mutableStateOf(p) }

    Row( modifier=Modifier.padding(0.dp,135.dp) ) {
        Column(modifier = Modifier.padding(15.dp)) {
            Text( modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center ,
                fontSize = TextUnit(5f, TextUnitType.Em),
                text = p.fullName())


            Divider( modifier = Modifier.padding(0.dp , 12.dp))
            
            Row(modifier = Modifier.fillMaxWidth()) {

                var assistBox = Modifier
                    .fillMaxWidth()
                    .padding(5.dp);

                Box( modifier = Modifier.weight(1f)){
                    AssistChip( modifier=assistBox, onClick = {
                    }, label = {Text(text = "Bambini")} )
                }
                Box( modifier = Modifier.weight(1f)){
                    AssistChip( modifier=assistBox, onClick = {  }, label = {Text(text = "Tus Kettig")} )
                }
                Box( modifier = Modifier.weight(1f)){
                    AssistChip( modifier=assistBox, onClick = { restCall() }, label = {Text( textAlign = TextAlign.Center, text = "${p.dateOfBirth.year}")} )
                }

            }

            NavItem( main = "Kontaktpersonen" , second = "Bearbeiten", onClick = { PlayerHelper.jump(p , nav , "player/detail/${p.id}/contacts") } )

            NavItem( main = "Mitgliedschaft verwalten" , second = null, onClick = {} )

            NavItem( main = "Team zuordnen" , second = null, onClick = {} )

            NavItem( main = "Info" , second = null, onClick = {                nav.currentBackStackEntry
                ?.savedStateHandle
                ?.set("player" , player );
                nav.navigate("player/detail/${p.id}/info")
            });

        }
    }

}

fun restCall() {
    TODO("Not yet implemented")
}

@ExperimentalPermissionsApi
private fun fileFun( player: Player?, playerService: PlayerService ){

    //playerService.add2( player );

    /*
    p.launchPermissionRequest()

    val filesDir = Environment.getExternalStoragePublicDirectory("documents")
    val new = File( filesDir , "myapp" );
    Log.i("path" , filesDir?.absolutePath!! );
    Log.i("path" , new?.absolutePath!! );
    val createNewFile = new.mkdirs()
    Log.i( "file" , new.absolutePath + " " + createNewFile );

    File(new , "test.txt").createNewFile()

    filesDir.list().forEach { Log.i("file" , it) }

    val out: FileOutputStream = c.openFileOutput( "test.txt" , MODE_PRIVATE)
     */

}


private fun openCamera( p: Player, context: Context) {
    val values = ContentValues()

    values.put(MediaStore.Images.Media.TITLE, "New Picture")
    values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")

    //camera intent
    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

    // set filename
    var vFilename = "${p.id}.jpg"
    var vFilename2 = "main.jpg"

    var file = File(Environment.getExternalStoragePublicDirectory("documents").absolutePath + "/tm/players")
    var file2 = File(Environment.getExternalStoragePublicDirectory("documents").absolutePath + "/tm/players/${p.id}")
    file = File(file, vFilename);
    file2 = File(file2, vFilename2);

    Log.i("Pic" , "exist = " + file.exists() )
    Log.i("Pic" , "exist = " + file.absolutePath )

    val image_uri = FileProvider.getUriForFile(Objects.requireNonNull( context ), "tm.provider", file);
    val image_uri2 = FileProvider.getUriForFile(Objects.requireNonNull( context ), "tm.provider", file2);

    //cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri2)
    var a = context.startActivity(cameraIntent);


    Log.i("image" , a.toString());
}


@Composable
@Preview
@ExperimentalUnitApi
@ExperimentalPermissionsApi
fun playerDetailPreview(){
    val navController = rememberNavController()

    val p = Player(UUID.randomUUID().toString() , "Dominik" , "Christ" , LocalDate.now().withYear(1988))

    var service = PlayerService( null );

    PlayerImage( p )

    PlayerDetail(p = p, service = service, nav = navController)

    header(nav = navController)

}

