package de.dom.cishome.myapplication.tm.adapter.`in`.compose.launch.pages

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import de.dom.cishome.myapplication.R
import de.dom.cishome.myapplication.compose.shared.TmColors
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.shared.CommonComponents
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.shared.Tm
import de.dom.cishome.myapplication.ui.MainControl

class HomeScreen {

    val list = listOf<CommonComponents.CardMenuItem>(
        CommonComponents.CardMenuItem("Verein", "club", R.drawable.clublogo),
        //CommonComponents.CardMenuItem("Player" , "player", R.drawable.club),
        //CommonComponents.CardMenuItem("Turnier" , "competition", R.drawable.tuniert),
        CommonComponents.CardMenuItem("Mein Team" , "myteam", R.drawable.member),
        CommonComponents.CardMenuItem("Platz" , "platz", R.drawable.platz),
    )

    @Composable
    fun Screen(mainClicks: MainControl = MainControl({},{},{}) ){
        content( clicks = mainClicks )
        this.PermissionRequest();
    }

    @Composable
    private fun content(clicks: MainControl){
        Scaffold (
            topBar = {Tm.components().TmTopBar(clickModel = clicks)},
            bottomBar = { BottomBar(clicks) }
        ){
            Box(modifier = Modifier.padding(it)){
                NavigationBoxes(clicks)
            }
        }
    }

    @Composable
    private fun DrawerContent(clicks: MainClicks, onClose: () -> Unit){
        ModalDrawerSheet {

            Column {
                Image( modifier=Modifier.align(Alignment.CenterHorizontally),
                    painter = painterResource(id = R.drawable.clublogo), contentDescription = "")
            }
            Divider()

            list.forEach {
                NavigationDrawerItem(
                    label = { Text(text = it.name) },
                    selected = false,
                    onClick = { clicks.navTo( it.dest ); onClose() }
                )
            }


        }
    }

    @Composable
    private fun NavigationBoxes(nav: MainControl){
        Box() {
            Box( modifier = Modifier.align(Alignment.TopCenter)){
                Column() {
                    Row() {
                        LazyVerticalGrid(columns = GridCells.Fixed(2),
                            contentPadding = PaddingValues(15.dp),
                            verticalArrangement = Arrangement.spacedBy(25.dp),
                            horizontalArrangement = Arrangement.spacedBy(25.dp) ){
                            items( list.size , key = {it}){
                                Tm.components().MenuCard(i = list[it], navigate = { nav.navTo(it) })
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun BottomBar(nav: MainControl){

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
            NavigationBarItem(selected = false,
                onClick = {  },
                icon = { Icon(Icons.Filled.Person, tint = TmColors.secondaryColor, contentDescription = "Localized description") },
                label = { Text("PROFILE" , color = TmColors.secondaryColor) }
            )
            NavigationBarItem(selected = false,
                onClick = { nav.navTo("home") },
                icon = { Icon(Icons.Filled.Settings, tint= TmColors.secondaryColor, contentDescription = "Localized description") },
                label = { Text("SETTINGS" , color= TmColors.secondaryColor) }
            )

        }
    }

    @Composable
    private fun Header( clicks: MainClicks ){

        val colors = TopAppBarDefaults.topAppBarColors(
            containerColor = TmColors.primaryColor,
            titleContentColor = TmColors.secondaryColor
        )

        TopAppBar(
            title = { Text(text="Team Manager" , color = Color.White) },
            colors = colors,
            navigationIcon = {
                IconButton(onClick = { clicks.onMenu()  } ) {
                    Icon(  imageVector = Icons.Filled.Menu, tint= Color.White, contentDescription = null)
                }
            },
            actions = {
                // RowScope here, so these icons will be placed horizontally
            }
        )

    }

    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    private fun PermissionRequest(){
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

    data class MainClicks( val navTo:(r: String)->Unit, val onHome: () -> Unit , var onMenu: ()->Unit = {} )

}

@Composable
@Preview
private fun Preview(){
    HomeScreen().Screen();
}