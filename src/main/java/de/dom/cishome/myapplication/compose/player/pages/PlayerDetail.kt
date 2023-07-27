package de.dom.cishome.myapplication.compose.player.pages

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Share
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import de.dom.cishome.myapplication.compose.Dimens
import de.dom.cishome.myapplication.compose.player.component.NavItem
import de.dom.cishome.myapplication.compose.player.component.PlayerImage
import de.dom.cishome.myapplication.compose.player.service.Player
import de.dom.cishome.myapplication.compose.player.service.PlayerService
import de.dom.cishome.myapplication.compose.shared.PlayerHelper
import de.dom.cishome.myapplication.compose.shared.TmDevice
import de.dom.cishome.myapplication.compose.shared.share
import java.time.LocalDate
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
    val context = LocalContext.current
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
        Column(modifier = Modifier.weight(1f)) {
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
        Column(modifier = Modifier.weight(5f)) {

        }
        Column(modifier = Modifier.weight(1f)) {
            IconButton(onClick = {
                share(ctx = context , text = "<html><body><div>Dominik Christ</div><div>Christ</div></body></html>" , t = "text/html")
            }) {
                Icon( Icons.Rounded.Share , "999999")
            }
        }




    }
}


@Composable
@ExperimentalUnitApi
@ExperimentalPermissionsApi
fun PlayerDetail(p: Player, service: PlayerService, nav: NavController){
    var context = LocalContext.current;
    var player = remember{ mutableStateOf(p) }

    var contactClick: ()->Unit = { PlayerHelper.jump(p , nav , "player/detail/${p.id}/contacts") }

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
                    if( p.trial ){
                        AssistChip( modifier=assistBox, onClick = {  }, label = {Text(text = "Schnupper")} )
                    } else {
                        AssistChip( modifier=assistBox, onClick = {  }, label = {Text(text = "Tus Kettig")} )
                    }

                }
                Box( modifier = Modifier.weight(1f)){
                    AssistChip( modifier=assistBox, onClick = {}, label = {Text( textAlign = TextAlign.Center, text = "${p.dateOfBirth.year}")} )
                }

            }

            NavItem( main = "Kontaktpersonen" , second = "Bearbeiten", onClick = { PlayerHelper.jump(p , nav , "player/detail/${p.id}/contacts") } )

            NavItem( main = "Mitgliedschaft" , second = null, onClick = contactClick )

            NavItem( main = "Team zuordnen" , second = null, onClick = {} )

            NavItem( main = "Info" , second = null, onClick = {                nav.currentBackStackEntry
                ?.savedStateHandle
                ?.set("player" , player );
                nav.navigate("player/detail/${p.id}/info")
            });

        }
    }

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

data class PlayerNavItem( val first: String, val second: String = "" , var click: ()->Unit )

