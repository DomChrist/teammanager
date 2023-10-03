package de.dom.cishome.myapplication.tm.application.port.`in`

import de.dom.cishome.myapplication.tm.application.domain.player.model.Player
import de.dom.cishome.myapplication.tm.application.domain.player.service.NewPlayerDomainCommand


interface RegisterPlayerUseCase{

    fun registerPlayer( cmd: NewPlayerDomainCommand): Player

    fun addContactPerson( playerId: String , contactId: String )

}