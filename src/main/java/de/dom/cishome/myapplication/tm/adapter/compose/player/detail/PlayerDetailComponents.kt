package de.dom.cishome.myapplication.tm.adapter.compose.player.detail

import android.os.Environment
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import de.dom.cishome.myapplication.R
import de.dom.cishome.myapplication.compose.shared.PlayerFileHelper
import de.dom.cishome.myapplication.tm.application.domain.player.model.Player
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception

@Composable
fun ActivatePlayerButton( onClick: () -> Unit) {

    val colors = ButtonDefaults.buttonColors( Color.Green , Color.White )
    Button(
        modifier= Modifier.fillMaxWidth(1f),
        colors = colors,
        onClick = { onClick() })
    {
        Text("AKTIVIEREN" , color = Color.Black)
    }

}

@Composable
fun PlayerImage(p: Player?,  onNewImage: ( f: File )->Unit = {}  ){

    val playerId = p?.id ?: "temp";
    val ctx = LocalContext.current;
    var targetFile: File = File(PlayerFileHelper().playerDir(playerId),"main.jpg"); File( Environment.getExternalStoragePublicDirectory("documents").absolutePath + "/tm/players/${playerId}/main.jpg" )
    val sourceUri = ComposeFileProvider.getImageUri( ctx , playerId = playerId );

    val result =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture(), onResult = {
            ctx.contentResolver.openInputStream( sourceUri ).let {
                PlayerFileHelper().copy( it!!.readBytes() , targetFile );
                it!!.close();
                onNewImage( targetFile );
            };
        })

    val hasImage = remember{ mutableStateOf(false) };

    val playerImage: Boolean = if( p != null ){
        try{
            targetFile != null && targetFile.exists()
        }catch ( e: Exception){
            false;
        }
    } else {
        false;
    }
    hasImage.value = playerImage;

    var m = Modifier
        .size(130.dp)
        .clip(CircleShape)
        .border(2.dp, Color.Gray, CircleShape);

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White.copy(alpha = 0.6f))
            .clickable {
                result.launch( sourceUri )
            }
            .padding(5.dp)) {

        if( playerImage ){
            AsyncImage(
                model = targetFile,
                placeholder = painterResource( R.drawable.club ),
                contentScale = ContentScale.FillBounds,
                modifier = m,
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
private fun preview(){
    ActivatePlayerButton { { } }
}