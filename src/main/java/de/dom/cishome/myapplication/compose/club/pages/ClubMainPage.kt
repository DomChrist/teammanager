package de.dom.cishome.myapplication.compose.club.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ClubMainPage(){

}

@Composable
private fun content(){

    Scaffold( bottomBar = { bottomBar() } ) {
        Box(Modifier.padding(it)){

        }
    }

}


@Composable
private fun bottomBar(){
    NavigationBar() {

        NavigationBarItem(selected = false,
            onClick = { /*TODO*/ },
            icon = { Icon(Icons.Filled.Menu, contentDescription = "Localized description") },
            label = { Text("HOME") }
        )
        NavigationBarItem(selected = false,
            onClick = { /*TODO*/ },
            icon = { Icon(Icons.Outlined.Person, contentDescription = "Localized description") },
            label = { Text("TRAINER") }
        )
        NavigationBarItem(selected = false,
            onClick = { /*TODO*/ },
            icon = { Icon(Icons.Filled.Person, contentDescription = "Localized description") },
            label = { Text("SETTINGS") }
        )

    }
}


@Composable
@Preview
fun ClubMainPagePreview(){
    content();
}
