package de.dom.cishome.myapplication.compose.home

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController


class TmComponents{

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
    fun stage1Header( title: String, nav: NavController ){
        TopAppBar(
            modifier = Modifier.background(Color.Cyan),
            title = { Text(title) },
            navigationIcon = {
                IconButton(onClick = { nav.navigateUp() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = null)
                }
            },
            actions = {
                // RowScope here, so these icons will be placed horizontally
                IconButton(onClick = { nav.navigateUp() }) {
                    Icon(Icons.Filled.Menu, contentDescription = "Localized description")
                }
            }
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


