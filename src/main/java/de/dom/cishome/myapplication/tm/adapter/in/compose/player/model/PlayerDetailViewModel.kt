package de.dom.cishome.myapplication.tm.adapter.`in`.compose.player.model

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import de.dom.cishome.myapplication.compose.player.pages.PlayerNavItem
import de.dom.cishome.myapplication.tm.application.domain.model.Player

@Deprecated(message = "Please use PlayerViewModel")
class PlayerDetailViewModel( var p: Player, var nav: NavController ) : ViewModel() {

    var items: List<PlayerNavItem> = listOf();
    init {
        items = PlayerDetailViewModel.navItemsBy( this.p , nav )
    }




    companion object Factory {
        fun navItemsBy( p: Player , nav: NavController ): List<PlayerNavItem> {
            return listOf<PlayerNavItem>(
                PlayerNavItem("Ansprechpartner" , "" , click =  {
                    nav.navigate("player/detail/${p.id}/contacts")
                })
            );
        }
    }




}