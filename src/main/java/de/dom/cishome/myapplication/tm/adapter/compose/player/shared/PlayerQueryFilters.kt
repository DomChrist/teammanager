package de.dom.cishome.myapplication.tm.adapter.compose.player.shared

import de.dom.cishome.myapplication.tm.application.domain.player.model.Player

data class PlayerListFilter( var key: String, var value: String , val filter: (p: Player)->Boolean ){
    fun isTeam(): Boolean = key.equals("TEAM" , true);
    fun isPlayer(): Boolean = key.equals("PLAYER" , true);
    fun isDefault(): Boolean = key.equals("ALL" , true);

    companion object Factory{

        fun byPlayer( id: String): PlayerListFilter {
            return PlayerListFilter("PLAYER" , id) { it.id.equals(id, true) };
        }

        fun byTeam( id: String ) = PlayerListFilter("TEAM" , id ){ it.team.equals(id,true) };

        fun none() = PlayerListFilter("ALL" , "") { true }

    }

}

