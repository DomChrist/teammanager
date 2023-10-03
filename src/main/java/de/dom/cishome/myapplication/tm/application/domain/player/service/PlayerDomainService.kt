package de.dom.cishome.myapplication.tm.application.domain.player.service

import de.dom.cishome.myapplication.tm.adapter.out.PlayerPersistenceAdapter
import de.dom.cishome.myapplication.tm.application.domain.player.model.Player
import de.dom.cishome.myapplication.tm.application.port.`in`.DeletePlayerUseCase
import de.dom.cishome.myapplication.tm.application.port.out.UpdatePlayerPort

class PlayerDomainService private constructor( private var update: UpdatePlayerPort = PlayerPersistenceAdapter.inject()) : DeletePlayerUseCase {

    companion object CDI {
        var service: PlayerDomainService? = null;
        fun inject(): PlayerDomainService {
            if( service == null ) service = PlayerDomainService()
            return service!!;
        }
    }


    override fun delete(p: Player) {
        update.delete( p.id )
    }

    override fun delete(playerId: String) {
        update.delete(playerId)
    }

}