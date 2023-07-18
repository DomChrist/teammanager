package de.dom.cishome.myapplication.compose.shared

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavController
import de.dom.cishome.myapplication.compose.player.service.Player

class PlayerHelper {


    companion object Nav {
        val NAV_PLAYER_KEY = "player";

        fun player( nav: NavController ): MutableState<Player>? {
            return nav.previousBackStackEntry
                ?.savedStateHandle
                ?.get< MutableState<Player>>(NAV_PLAYER_KEY)
        }

        fun jump ( p: Player, nav: NavController , path: String ){
            var m = mutableStateOf(p);
            try{
                nav.currentBackStackEntry
                    ?.savedStateHandle
                    ?.set( NAV_PLAYER_KEY , m )
                nav.navigate( path )
            }catch ( e: Exception ){
                e.printStackTrace()
            }
        }

    }


}