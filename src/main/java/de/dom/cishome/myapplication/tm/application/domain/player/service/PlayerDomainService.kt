package de.dom.cishome.myapplication.tm.application.domain.player.service

import android.content.Context
import androidx.compose.animation.core.updateTransition
import de.dom.cishome.myapplication.tm.adapter.out.PlayerPersistenceAdapter
import de.dom.cishome.myapplication.tm.application.domain.player.model.Player
import de.dom.cishome.myapplication.tm.application.port.`in`.DeletePlayerUseCase
import de.dom.cishome.myapplication.tm.application.port.`in`.TrialPlayerUseCase
import de.dom.cishome.myapplication.tm.application.port.out.UpdatePlayerPort
import java.io.File

class PlayerDomainService constructor( private var update: UpdatePlayerPort = PlayerPersistenceAdapter()) : DeletePlayerUseCase {


    override fun delete(p: Player) {
        update.delete( p.id )
    }

    override fun delete(playerId: String) {
        update.delete(playerId)
    }

    fun updateImage( playerId: String, file: File){
        update.updateImage( playerId = playerId , imageFile = file )
    }

}