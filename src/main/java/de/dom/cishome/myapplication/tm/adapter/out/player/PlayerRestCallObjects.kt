package de.dom.cishome.myapplication.tm.adapter.out.player

import de.dom.cishome.myapplication.tm.adapter.out.myteam.TeamResponse
import de.dom.cishome.myapplication.tm.application.domain.player.model.Player
import de.dom.cishome.myapplication.tm.application.domain.player.model.PlayersTeamModel
import java.time.LocalDate

data class PlayerApiObject(val id: String,
                          val givenName: String,
                          val familyName: String,
                          val dateOfBirth: LocalDate,
                          val trialState: PlayerMemberState
){
    fun map(): Player {

        val state: Player.MemberState;
        this.trialState.let {
            state = it.trial?.let {
                Player.MemberState( Player.MemberState.Trial(it.start,it.count) , null )
            } ?: Player.MemberState( null , Player.MemberState.Active(true))
        }

        var p = Player(
            this.id,
            this.givenName,
            this.familyName,
            this.dateOfBirth,
            "unkown",
            listOf(),
            state
        )
        return p;
    }

}

data class TeamPlayerResponse( var meta: TeamResponse, var list: List<PlayerApiObject> ){

    fun toModel(): PlayersTeamModel{
        var team = PlayersTeamModel.Team( meta.id , meta.label , meta.ageGroup )
        var players = list.map { it.map() }
        return PlayersTeamModel( 200, team , players )
    }

}

data class PlayerMemberState( val trial:  TrialResponse? , val member: Boolean?  ) {


    data class TrialResponse(val start: LocalDate, val count: Int );

    data class MemberResponse(val start: LocalDate, val end: LocalDate?)

}

data class PlayerContactResponse( val id: String , val description: String , val value: String ){}


class PlayerRequest(){
    data class PlayerMembershipRequest( var localDate: LocalDate = LocalDate.now() )

}