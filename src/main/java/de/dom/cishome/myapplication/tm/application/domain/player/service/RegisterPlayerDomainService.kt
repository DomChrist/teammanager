package de.dom.cishome.myapplication.tm.application.domain.player.service

import de.dom.cishome.myapplication.tm.application.domain.player.model.Player
import de.dom.cishome.myapplication.tm.application.port.`in`.CreatePlayerCommand
import de.dom.cishome.myapplication.tm.application.port.`in`.RegisterPlayerUseCase
import de.dom.cishome.myapplication.tm.application.port.out.CreatePlayerPort
import de.dom.cishome.myapplication.tm.application.port.out.PlayerReaderPort

class RegisterPlayerDomainService(private val reader: PlayerReaderPort, private val out: CreatePlayerPort ): RegisterPlayerUseCase{

    override fun registerPlayer(cmd: NewPlayerDomainCommand): Player {
        val player = Player(
            cmd.id,
            cmd.givenName,
            cmd.familyName,
            cmd.dateOfBirth,
            cmd.team,
            listOf<String>(),
            cmd.trail,
            false
        )
        out.persist( player );
        return player;
    }

    override fun addContactPerson(playerId: String, contactId: String) {
        reader.byId( playerId ){
            it.contactPersons = it.contactPersons.plus( contactId )
            out.persist(it);
        }
    }


}