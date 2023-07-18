package de.dom.cishome.myapplication.compose.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable

@Composable
fun tmBackButton( click: ()->Boolean ){
    IconButton(onClick = { click }) {
        Icon(Icons.Filled.ArrowBack, contentDescription = "Arrow back")
    }
}
