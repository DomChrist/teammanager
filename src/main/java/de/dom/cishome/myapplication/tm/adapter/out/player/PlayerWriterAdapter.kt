package de.dom.cishome.myapplication.tm.adapter.out.player

import de.dom.cishome.myapplication.config.AsyncResponse
import de.dom.cishome.myapplication.tm.application.domain.player.model.Player
import de.dom.cishome.myapplication.tm.application.domain.player.model.PlayerContactDetail
import de.dom.cishome.myapplication.tm.application.port.out.CreatePlayerPort
import de.dom.cishome.myapplication.tm.application.port.out.UpdatePlayerPort
import java.io.File

class PlayerWriterAdapter(

) : UpdatePlayerPort , CreatePlayerPort {


    override fun persist(p: Player) {
        TODO("Not yet implemented")
    }

    override fun persistNewTeamPlayer(p: Player, teamId: String, call: AsyncResponse<Player>) {
        TODO("Not yet implemented")
    }

    override fun update(p: Player) {
        TODO("Not yet implemented")
    }

    override fun delete(id: String) {
        TODO("Not yet implemented")
    }

    override fun create(playerId: String, newContact: PlayerContactDetail, onSuccess: () -> Unit) {
        TODO("Not yet implemented")
    }

    override fun updateImage(playerId: String, imageFile: File) {
        TODO("Not yet implemented")
    }


}