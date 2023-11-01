package de.dom.cishome.myapplication.tm.adapter.compose.player.detail

import de.dom.cishome.myapplication.tm.application.domain.player.model.Player

data class PlayerDetailClick(var back: ()->Unit,
                             var navTo: (r: String)->Unit,
                             var onActivate: ( p: Player)->Unit = {},
                             var onTrialParticipation: ( count: Int , player: Player) -> Unit = { count, p -> {} }
);