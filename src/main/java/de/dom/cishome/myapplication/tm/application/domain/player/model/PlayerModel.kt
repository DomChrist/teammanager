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
                  var contactDetails: List<String> = emptyList(),
                  var state: MemberState = MemberState(MemberState.Trial(LocalDate.now().minusDays(5)) , null )
){

    fun key(): String {
        return "${givenName.lowercase()}_${familyName.lowercase()}"
    }

    fun fullName(): String {
        return "$givenName $familyName";
    }

    fun isTrial(): Boolean = state.isTrial();


    data class MemberState( val trial: Trial? , val active: Active? ){


        fun isConsist() = (this.trial != null) !=  (this.active != null)

        fun isActive() = this.active != null && this.active.active

        fun isTrial() = !this.isActive();

        data class Trial( val trialStart: LocalDate , var trialCount: Int = 0 )

        data class Active( val active: Boolean )

    };

}

data class PlayerTeam( val id: String, val name: String )

data class PlayerContactDetail( var id: String , var description: String , var value: String )

