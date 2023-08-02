package de.dom.cishome.myapplication.tm.application.domain.service

import de.dom.cishome.myapplication.tm.application.domain.model.Player
import de.dom.cishome.myapplication.tm.application.port.`in`.CreatePlayerCommand
import de.dom.cishome.myapplication.tm.application.port.`in`.RegisterPlayerUseCase
import de.dom.cishome.myapplication.tm.application.port.out.CreatePlayerPort

class RegisterPlayerDomainService(private val out: CreatePlayerPort ): RegisterPlayerUseCase{

    override fun registerPlayer(cmd: NewPlayerDomainCommand): Player {
        val player = Player(
            cmd.id,
            cmd.givenName,
            cmd.familyName,
            cmd.dateOfBirth,
            cmd.team,
            cmd.trail,
            false
        )
        out.persist( player );
        return player;
    }


}