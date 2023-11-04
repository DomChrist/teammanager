package de.dom.cishome.myapplication.tm.adapter.compose.player.overview

import android.os.Environment
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.R
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import de.dom.cishome.myapplication.compose.shared.PlayerFileHelper
import de.dom.cishome.myapplication.tm.application.domain.player.model.Player
import java.io.File
import java.lang.Exception
import java.time.LocalDate

class PlayerOverviewComponents {

    companion object{
        @Composable
        fun ShortPlayerImage( modifier: Modifier? = null, p: Player ){
            val m: Modifier = modifier
                ?: Modifier
                    .size(45.dp)
                    .clip(RectangleShape)
                    .border(2.dp, Color.Gray, RectangleShape)

            var targetFile: File? = null;// = File(PlayerFileHelper().playerDir(p.id),"main.jpg");
            PlayerFileHelper().whenPlayerFileExist(p.id,"main.jpg"){
                targetFile = it;
            }
            if( targetFile != null ){
                AsyncImage(
                    model = targetFile,
                    placeholder = painterResource( de.dom.cishome.myapplication.R.drawable.club ),
                    contentScale = ContentScale.FillBounds,
                    modifier = m,
                    contentDescription = null,
                )
            } else {
                Image(painter = painterResource(id = de.dom.cishome.myapplication.R.drawable.club),
                    contentScale = ContentScale.FillBounds,
                    contentDescription = "" ,
                    modifier = m
                )
            }
        }

        @Composable
        fun PlayerShortBox(
            p:Player,
            withImage: Boolean = true
        ){
            if( withImage ){
                val borderColorOnActive = if( p.isTrial() ) Color.Red else Color.Green;
                val modifier = Modifier
                    .size(45.dp)
                    .clip(CircleShape)
                    .border(1.dp, borderColorOnActive, CircleShape)

                Box(modifier = Modifier.padding(PaddingValues(15.dp))){

                    Column() {
                        ShortPlayerImage( modifier=modifier, p = p)
                    }
                    Column(Modifier.padding(50.dp , 0.dp, 0.dp, 0.dp)) {
                        Text(  p.givenName.plus(" ").plus(p.familyName), fontWeight = FontWeight.Bold )
                    }
                }
            } else {
                Box(modifier = Modifier.padding(PaddingValues(15.dp))){
                    Column() {
                        Icon(
                            Icons.Rounded.Person,
                            contentDescription = stringResource(id = R.string.in_progress)
                        )
                    }
                    Column(Modifier.padding(30.dp , 0.dp, 0.dp, 0.dp)) {
                        Text(  p.givenName.plus(" ").plus(p.familyName), fontWeight = FontWeight.Bold )
                    }
                }
            }

        }


    }

}



@Composable
@Preview
private fun PlayerOverviewComponentsPreview(){
    val player = Player("1234" , "Max" , "Mustermann" , LocalDate.of(2018,1,1) , "Bambini" , state = Player.MemberState(null,Player.MemberState.Active(true)) )

    PlayerOverviewComponents.PlayerShortBox( player , true );
}