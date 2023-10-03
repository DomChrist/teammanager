package de.dom.cishome.myapplication.config.security

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.dom.cishome.myapplication.R
import de.dom.cishome.myapplication.compose.shared.TmColors

class SecurityComponents {

    companion object Components{

        @Composable
        fun LoginScreen(onLogin: () -> Unit , onLocal: ()->Unit ) {
            val color = TmColors.App;
            Box( Modifier.fillMaxSize() ){
                Column {
                    Row(Modifier.fillMaxWidth().padding(15.dp)) {
                        Image( modifier=Modifier.fillMaxWidth().width(200.dp).height(220.dp),
                            painter = painterResource(id = R.drawable.clublogo), contentDescription = "")
                    }
                    Row(Modifier.fillMaxWidth()) {
                        Card(Modifier.padding(25.dp)){
                            var d = ButtonDefaults.buttonColors( color.primary , color.primaryText )
                            Button(modifier=Modifier.fillMaxWidth() , colors=d, onClick = onLogin ) {
                                Text("LOGIN")
                            }
                        }
                    }
                    Row(Modifier.fillMaxWidth()) {
                        Card(Modifier.padding(25.dp , 1.dp)){
                            var d = ButtonDefaults.buttonColors( color.secondary , color.secondaryText )
                            Button(modifier=Modifier.fillMaxWidth() , colors=d, onClick = onLocal ) {
                                Text("LOCAL")
                            }
                        }
                    }
                }


                Column(Modifier.fillMaxWidth()) {
                }
            }

        }

    }

}

@Composable
@Preview
private fun Preview(){

    SecurityComponents.LoginScreen( {} , {} );

}