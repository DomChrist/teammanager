package de.dom.cishome.myapplication.tm.application.port.`in`

import de.dom.cishome.myapplication.tm.application.domain.player.model.Player
import de.dom.cishome.myapplication.tm.application.domain.player.model.PlayerModel
import java.time.LocalDate
import java.util.UUID

data class CreatePlayerCommand(var id: String = UUID.randomUUID().toString(), var givenName: String, var familyName: String, val dateOfBirth: LocalDate, var team: String, var trial: Boolean = false, var member: Boolean = true ){

    fun toPlayer(): Player {
        return Player( id , givenName , familyName , dateOfBirth , team, listOf() ,trial , member);
    }

}

data class ActivatePlayerCommand( var id: String )