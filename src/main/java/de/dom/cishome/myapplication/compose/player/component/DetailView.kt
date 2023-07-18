package de.dom.cishome.myapplication.compose.player.component

import android.os.Environment
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import de.dom.cishome.myapplication.R
import de.dom.cishome.myapplication.compose.home.TmComponents
import de.dom.cishome.myapplication.compose.player.service.Player
import java.io.File

@Composable
fun PlayerDetailViewComponent(){
    val tm = TmComponents();
    Scaffold(topBar = {tm.header()} ) {
        Box( modifier = Modifier.padding(it) ){

        }
    }


}


@Composable
fun PlayerImage( p: Player?, onclick: Unit ){
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
                modifier = m.clickable { onclick }
                ,
                contentDescription = null,
            )
        } else {
            Image(painter = painterResource(id = R.drawable.club),
                contentScale = ContentScale.FillBounds,
                contentDescription = "" ,
                modifier = m.clickable { onclick }
            )
        }

    }

}



