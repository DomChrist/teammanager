package de.dom.cishome.myapplication.compose.home

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import de.dom.cishome.myapplication.R
import de.dom.cishome.myapplication.compose.shared.TmColors
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.shared.CommonComponents
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.shared.Tm
import okhttp3.OkHttpClient


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun home( nav: NavController ){

    var d = MenuData();

    content(d = d, nav = nav)

    var permission = rememberMultiplePermissionsState(permissions = listOf<String>(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.VIBRATE,
        Manifest.permission.CAMERA
    ))
    SideEffect {
        if( permission.allPermissionsGranted ){
        } else {
            permission.permissions.forEach {
                Log.i("Permission" , "${it.permission} -> ${it.status.isGranted}")
            }
            permission.launchMultiplePermissionRequest()
        }
    }

}

@Composable
fun NavigationBoxes( d: MenuData, nav: NavController ){
    Box() {
        Box( modifier = Modifier.align(Alignment.TopCenter)){
            Column() {
                Row() {
                    LazyVerticalGrid(columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(15.dp),
                        verticalArrangement = Arrangement.spacedBy(25.dp),
                        horizontalArrangement = Arrangement.spacedBy(25.dp) ){
                        items( d.list.size , key = {it}){
                            Tm.components().MenuCard(i = d.list[it], navigate = { nav.navigate(it) })
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun content( d: MenuData, nav: NavController ){
    var tm = TmComponents();
    Scaffold (
        topBar = { tm.header() },
        bottomBar = {TmBottomBar(nav)}
    ){
        Box(modifier = Modifier.padding(it)){
            NavigationBoxes(d,nav)
        }
    }
}

@Composable
fun TmBottomBar(nav: NavController){

    var ctx = LocalContext.current;

    NavigationBar( contentColor = TmColors.primaryColor , containerColor = TmColors.primaryColor ) {

        NavigationBarItem(selected = false,
            onClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://tus-kettig1959.de/tuskettig/"),)
                ctx.startActivity( intent)
            },
            icon = { Icon(imageVector = Icons.Filled.Menu, tint = TmColors.secondaryColor, contentDescription = "Localized description") },
            label = { Text(text="HOME" , color = TmColors.secondaryColor) }
        )
        /*
        NavigationBarItem(selected = false,
            onClick = {  },
            icon = { Icon(Icons.Filled.Person, tint = TmColors.secondaryColor, contentDescription = "Localized description") },
            label = { Text("PROFILE" , color = TmColors.secondaryColor) }
        )
         */
        NavigationBarItem(selected = false,
            onClick = { nav.navigate(Uri.parse("http://localhost:8082")) },
            icon = { Icon(Icons.Filled.Settings, tint=TmColors.secondaryColor, contentDescription = "Localized description") },
            label = { Text("SETTINGS" , color=TmColors.secondaryColor) }
        )

    }
}


fun auth(){
    val build = OkHttpClient.Builder().build();

}

class MenuData {

    var list = listOf<CommonComponents.CardMenuItem>(
        CommonComponents.CardMenuItem("Verein", "club", R.drawable.clublogo),
        //CommonComponents.CardMenuItem("Player" , "player", R.drawable.club),
        //CommonComponents.CardMenuItem("Turnier" , "competition", R.drawable.tuniert),
        CommonComponents.CardMenuItem("Mein Team" , "myteam", R.drawable.member),
        CommonComponents.CardMenuItem("Platz" , "platz", R.drawable.platz),
    )


}

@Composable
@Preview
fun HomePreview(){
    var d = MenuData();
    var nav = rememberNavController();
    content(d,nav = nav)
}