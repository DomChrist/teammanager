package de.dom.cishome.myapplication.tm.adapter.compose.player.overview

import androidx.navigation.NavController
import de.dom.cishome.myapplication.tm.application.domain.player.model.Player


data class PlayerOverviewClicks(
    var onPlayerAddClick: () -> Unit,
    var onBackClick: () -> Unit,
    var onPlayerSelect: (p: Player) -> Unit,
) {

    companion object Factory{

        fun clicks( nav: NavController): PlayerOverviewClicks{
            return PlayerOverviewClicks( { nav.navigate("player/add") } ,
                {nav.navigateUp()} ,
                { nav.navigate("player/detail/${it.id}") });
        }
    }

}