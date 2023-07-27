package de.dom.cishome.myapplication.compose.home

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import de.dom.cishome.myapplication.R
import de.dom.cishome.myapplication.compose.shared.MyColorTheme
import de.dom.cishome.myapplication.compose.shared.TmColors


class TmComponents{

    @Composable
    fun header(){

        val colors = TopAppBarDefaults.topAppBarColors(
            containerColor = TmColors.primaryColor,
            titleContentColor = TmColors.secondaryColor
        )

        TopAppBar(
            title = { Text(text="Team Manager" , color = Color.White) },
            colors = colors,
            navigationIcon = {
                IconButton(onClick = { /* doSomething() */ }) {
                    Icon(  imageVector = Icons.Filled.Menu, tint=Color.White, contentDescription = null)
                }
            },
            actions = {
                // RowScope here, so these icons will be placed horizontally
            }
        )

    }

    @Composable
    fun header( nav: NavController ){
        TopAppBar(
            modifier = Modifier.background(Color.Cyan),
            title = { Text("Team Manager") },
            navigationIcon = {
                IconButton(onClick = { /* doSomething() */ }) {
                    Icon(Icons.Filled.Menu, contentDescription = null)
                }
            },
            actions = {
                // RowScope here, so these icons will be placed horizontally
                IconButton(onClick = { nav.navigateUp() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Localized description")
                }
            }
        )
    }

    @Composable
    fun header(nav: NavController, action: () -> Unit){
        TopAppBar(
            modifier = Modifier.background(Color.Cyan),
            title = { Text("Team Manager") },
            navigationIcon = {
                IconButton(onClick = { nav.navigateUp() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Localized description")
                }
            },
            actions = {
                IconButton(onClick = { action }) {
                    Icon(Icons.Filled.Send, contentDescription = null)
                }
            }
        )
    }

    @Composable
    fun stage1Header( title: String, nav: NavController ){
        stage1HeaderCustomize(title = title, nav = nav, color = TmColors.App)
    }

    @Composable
    fun stage1HeaderCustomize( title: String, nav: NavController, color: MyColorTheme ){
        var defaults = TopAppBarDefaults.topAppBarColors( containerColor = color.primary )
        TopAppBar(
            title = { Text(title , color=color.primaryText) },
            navigationIcon = {
                IconButton(onClick = { nav.navigateUp() }) {
                    Icon(Icons.Filled.ArrowBack, tint=color.primaryText, contentDescription = null)
                }
            },
            actions = {
                // RowScope here, so these icons will be placed horizontally
                IconButton(onClick = { nav.navigateUp() }) {
                    Icon(Icons.Filled.Menu, tint=color.primaryText, contentDescription = "Localized description")
                }
            },
            colors = defaults
        )
    }

}

@Composable
fun header( nav: NavController ){
    TopAppBar(
        modifier = Modifier.background(Color.Cyan),
        title = { Text("Team Manager") },
        navigationIcon = {
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(Icons.Filled.Menu, contentDescription = null)
            }
        },
        actions = {
            // RowScope here, so these icons will be placed horizontally
            IconButton(onClick = { nav.navigateUp() }) {
                Icon(Icons.Filled.Home, contentDescription = "Localized description")
            }
        }
    )
}

@Composable
fun header(){

    TopAppBar(
        title = { Text("Team Manager") },
        navigationIcon = {
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(Icons.Filled.Menu, contentDescription = null)
            }
        },
        actions = {
            // RowScope here, so these icons will be placed horizontally
        }
    )

}

@Composable
fun subPageHeader(title: String, function: Unit){

    return TopAppBar(
        title = {Text(title)},
        navigationIcon = {
            function
        },
        actions = {
            // RowScope here, so these icons will be placed horizontally
        }
    )
}

fun open(){

}


