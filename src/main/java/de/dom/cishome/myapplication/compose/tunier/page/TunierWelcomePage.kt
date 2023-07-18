package de.dom.cishome.myapplication.compose.tunier.page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import de.dom.cishome.myapplication.compose.home.TmComponents

@Composable
fun TurnierWelcomePage( nav: NavController ){

    content(nav = nav )

}


@Composable
fun content( nav: NavController){

    Scaffold(topBar = { TmComponents().stage1Header(title = "Turnier", nav = nav) }) {
        it -> Box(modifier = Modifier.padding(it)){}
    }

}


@Preview
@Composable
fun TunierWelcomePagePreview(){

    var nav = rememberNavController()

    content(nav = nav)

}
