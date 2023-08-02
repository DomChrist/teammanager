package de.dom.cishome.myapplication.tm.application.port.`in`

import de.dom.cishome.myapplication.tm.application.domain.model.Player
import de.dom.cishome.myapplication.tm.application.domain.service.NewPlayerDomainCommand


interface RegisterPlayerUseCase{

    fun registerPlayer( cmd: NewPlayerDomainCommand ): Player

}