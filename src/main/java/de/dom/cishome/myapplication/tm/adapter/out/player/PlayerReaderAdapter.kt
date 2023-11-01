package de.dom.cishome.myapplication.tm.adapter.out.player

import android.content.Context
import de.dom.cishome.myapplication.tm.adapter.compose.player.shared.PlayerListFilter
import de.dom.cishome.myapplication.tm.application.domain.player.model.Player
import de.dom.cishome.myapplication.tm.application.domain.player.model.PlayerContactDetail
import de.dom.cishome.myapplication.tm.application.domain.player.model.PlayersTeamModel
import de.dom.cishome.myapplication.tm.application.port.out.PlayerReaderPort


class PlayerReaderAdapter constructor( val ctx: Context ,
                                       val api: PlayerRestCall = PlayerRestCall(),
                                       val file: PlayerFileRepository = PlayerFileRepository()
) : PlayerReaderPort {

    override fun readAll(onSuccess: (list: List<Player>) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun readAll(f: PlayerListFilter, onSuccess: (response: PlayersTeamModel ) -> Unit) {
        this.api.playersInTeam(
            f.value,
            onSuccess = { onSuccess( PlayersTeamModel(200,null,it.map { p -> p.map() }) ) },
            onError = { onSuccess(PlayersTeamModel(400,null, listOf())) }
            )
    }

    override fun byId(id: String, onSuccess: (list: Player) -> Unit) {
        this.api.player(
            id,
            onError = {
                this.file.player( id , onSuccess )
            }
        ){
            onSuccess( it.map() )
        }
    }

    override fun readCommunications(
        playerId: String,
        onSuccess: (list: List<PlayerContactDetail>) -> Unit
    ) {
        TODO("Not yet implemented")
    }

}