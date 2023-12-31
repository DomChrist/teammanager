package de.dom.cishome.myapplication.tm.adapter.`in`.compose.shared

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.DrawerState
import androidx.compose.material.ModalDrawer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.DrawerValue
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.dom.cishome.myapplication.compose.shared.MyColorTheme
import de.dom.cishome.myapplication.compose.shared.TmColors
import de.dom.cishome.myapplication.ui.MainControl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class Tm {

    companion object Component {

        fun components(): CommonComponents{
            return CommonComponents();
        }

    }


}

class CommonComponents{

    @Composable
    fun MenuCard(i: CardMenuItem, navigate: (route: String) -> Unit ){

        ElevatedCard( onClick = { navigate(i.dest) } )  {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(125.dp)
                    .heightIn(55.dp, 125.dp),
                contentScale = ContentScale.FillBounds,
                painter = painterResource( i.res ),
                contentDescription = "Fussballer"
            )
            Row(  Modifier.padding(15.dp) ){
                Box( modifier= Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Text(text = i.name.uppercase(), textAlign = TextAlign.Center, fontWeight = FontWeight.Bold )
                }
            }
        }
    }
    data class CardMenuItem(var name: String, var dest: String, var res: Int)


    @Composable fun TopBar( title: String = "Team Manager" , theme: MyColorTheme = TmColors.App, clickModel: DefaultClickModel = DefaultClickModel({},{})  ){

        val colors = TopAppBarDefaults.topAppBarColors(
            containerColor = theme.primary,
            titleContentColor = theme.secondary
        )

        TopAppBar(
            title = { Text(text=title , color = Color.White) },
            colors = colors,
            navigationIcon = {
                IconButton(onClick = clickModel.navBack ) {
                    Icon(  imageVector = Icons.Filled.Home, tint= Color.White, contentDescription = null)
                }
            },
            actions = {
                // RowScope here, so these icons will be placed horizontally
            }
        )
    }

    @Composable fun TmTopBar( title: String = "Team Manager",
                              theme: MyColorTheme = TmColors.App,
                              showBackArrow: Boolean = false,
                              clickModel: MainControl = MainControl({},{},{})  ){

        val colors = TopAppBarDefaults.topAppBarColors(
            containerColor = theme.primary,
            titleContentColor = theme.secondary
        )

        TopAppBar(
            title = { Text(text=title , color = Color.White) },
            colors = colors,
            navigationIcon = {
                if( showBackArrow ){
                    IconButton(onClick = clickModel.back ) {
                        Icon(  imageVector = Icons.Filled.ArrowBack, tint= Color.White, contentDescription = null)
                    }
                } else {
                    IconButton(onClick = { clickModel.showMainMenu(true) } ) {
                        Icon(  imageVector = Icons.Filled.Menu, tint= Color.White, contentDescription = null)
                    }
                }
            },
            actions = {
                if( showBackArrow ){
                    IconButton(onClick = {clickModel.showMainMenu(true)} ) {
                        Icon(  imageVector = Icons.Filled.Menu, tint= Color.White, contentDescription = null)
                    }
                }
            }
        )
    }


    @Composable
    fun TmBottomBar( actions: List<NavigationBarItemData> , color: Color = TmColors.secondaryColor){
        NavigationBar( contentColor = TmColors.primaryColor , containerColor = TmColors.primaryColor ) {
            actions.forEach {
                NavigationBarItem(selected = false,
                    onClick = it.click,
                    icon = { Icon(imageVector = it.icon , tint = color, contentDescription = "") },
                    label = { Text(text=it.label , color = color ) }
                )
            }
        }
    }
    data class NavigationBarItemData(val label: String, val icon: ImageVector, val click: ()->Unit ){

        companion object Factory{

            @Composable
           fun default(): List<NavigationBarItemData> {
               return listOf<NavigationBarItemData>(
                   NavigationBarItemData( label = "Test" , icon = Icons.Filled.Menu) {}
               )
           }

        }
    }


    @Composable
    fun Loading(){
        var mod = Modifier.fillMaxWidth();
        Row(mod) {
            Column(mod) {
                Text( modifier = mod , text="...Loading..." , textAlign = TextAlign.Center)
            }
        }
    }

}



open class DefaultClickModel( val navBack: ()->Unit = {} , val navTo: (route:String) -> Unit = {} );


@Composable
@Preview
private fun preview(){
    Tm.components().Loading();
}
