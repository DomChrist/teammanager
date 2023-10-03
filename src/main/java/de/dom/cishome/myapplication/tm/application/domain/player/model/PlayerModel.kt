package de.dom.cishome.myapplication.tm.application.domain.player.model

import java.time.LocalDate
import java.util.UUID

class PlayerModel {
}

data class Player(val id: String = UUID.randomUUID().toString(),
                  val givenName: String,
                  val familyName: String,
                  val dateOfBirth: LocalDate,
                  val team: String,
                  var contactPersons: List<String> = emptyList(),
                  val trial: Boolean = false,
                  val member: Boolean = true){

    fun key(): String {
        return "${givenName.lowercase()}_${familyName.lowercase()}"
    }

    fun fullName(): String {
        return "$givenName $familyName";
    }


}

data class PlayerTeam( val id: String, val name: String )