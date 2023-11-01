package de.dom.cishome.myapplication.tm.application.domain.player.model

import de.dom.cishome.myapplication.tm.adapter.out.myteam.AgeGroup


data class PlayersTeamModel(
    val state: Int = 204,
    val team: Team?,
    val players: List<Player>
){


    fun hasTeam(): Boolean = team != null;
    fun ready(): Boolean {
        return state in 200..299;
    }

    fun onFailure(): Boolean {
        return state >= 300
    }

    fun onLoad(): Boolean {
        return state < 200;
    }

    data class Team(var id: String, var name: String, val ageGroup: AgeGroup){

    }

}

