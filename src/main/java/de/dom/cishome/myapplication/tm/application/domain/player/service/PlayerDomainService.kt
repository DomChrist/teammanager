package de.dom.cishome.myapplication.tm.application.domain.player.service

import android.content.Context
import de.dom.cishome.myapplication.tm.adapter.out.PlayerPersistenceAdapter
import de.dom.cishome.myapplication.tm.application.domain.player.model.Player
import de.dom.cishome.myapplication.tm.application.port.`in`.DeletePlayerUseCase
import de.dom.cishome.myapplication.tm.application.port.`in`.TrialPlayerUseCase
import de.dom.cishome.myapplication.tm.application.port.out.UpdatePlayerPort

class PlayerDomainService constructor( private var ctx: Context, private var update: UpdatePlayerPort) : DeletePlayerUseCase {


    override fun delete(p: Player) {
        update.delete( p.id )
    }

    override fun delete(playerId: String) {
        update.delete(playerId)
    }


}