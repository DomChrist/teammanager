package de.dom.cishome.myapplication.compose.playground.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import de.dom.cishome.myapplication.R

@Composable
fun PlayGroundPage(navController: NavHostController) {

    Column(modifier = Modifier.padding(5.dp)) {
        Box() {
            content()

        }
    }



}


@Composable
fun content(){

    var m = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
    Image(painter = painterResource(id = R.drawable.large_field),
        contentScale = ContentScale.FillBounds,
        contentDescription = "" , modifier = m)

    var colMod = Modifier
        .fillMaxSize()
        .padding(22.dp, 30.dp)
        .border(3.dp, Color.White);


    Column( modifier=colMod ) {
        var box = Modifier
            .fillMaxWidth()
            .weight(1f)
            .border(1.dp, Color.White);

        Box( modifier = box.background(Color.DarkGray.copy(alpha = 0.5f) , RectangleShape ) ) {
            Text("Feld1")
        }
        Box( modifier = box ) {
            Text("Feld2")
        }

    }

}

@Composable
@Preview
fun PlayGroundPagePreview(){

    Column(modifier = Modifier.padding(25.dp)) {
        Box() {
            content()
        }
    }

}


