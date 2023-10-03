package de.dom.cishome.myapplication.ui

import androidx.compose.material3.DrawerState
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class TmNavigation {



}

data class MainControl(
    val showMainMenu: (b:Boolean)->Unit,
    val back: ()->Unit,
    val navTo: (r: String) -> Unit
){

    fun toggleLeftMenu(){
        showMainMenu(false)
    }

    companion object Factory {

        fun Create(nav: NavController, drawerState: DrawerState, scope: CoroutineScope): MainControl {
            return MainControl(
                { if( drawerState.isOpen ) scope.launch{drawerState.close()} else scope.launch { drawerState.open() } },
                { nav.navigateUp() },
                { nav.navigate( it ) }
            )
        }

    }

}