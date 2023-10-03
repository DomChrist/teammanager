package de.dom.cishome.myapplication.tm.application.port.`in`

import de.dom.cishome.myapplication.tm.application.domain.player.model.Player

interface DeletePlayerUseCase{

    fun delete( p: Player );

    fun delete( playerId: String )

}