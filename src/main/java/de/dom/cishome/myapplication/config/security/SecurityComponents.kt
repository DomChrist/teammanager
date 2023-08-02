package de.dom.cishome.myapplication.config.security

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class SecurityComponents {

    companion object Components{

        @Composable
        fun LoginScreen(onLogin: () -> Unit) {

            Box( Modifier.fillMaxSize() ){
                Column(Modifier.fillMaxWidth()) {
                    Row(Modifier.fillMaxWidth()) {
                        Card(Modifier.padding(25.dp)){
                            Button(modifier=Modifier.fillMaxWidth() , onClick = onLogin ) {
                                Text("LOGIN")
                            }
                        }
                    }
                }
            }

        }

    }

}
