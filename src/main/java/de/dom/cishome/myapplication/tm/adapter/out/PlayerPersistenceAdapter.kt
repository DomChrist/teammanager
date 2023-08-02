package de.dom.cishome.myapplication.tm.adapter.out

import de.dom.cishome.myapplication.compose.shared.GsonUtils
import de.dom.cishome.myapplication.compose.shared.PlayerFileHelper
import de.dom.cishome.myapplication.tm.application.domain.model.Player
import de.dom.cishome.myapplication.tm.application.port.out.CreatePlayerPort

class PlayerPersistenceAdapter : CreatePlayerPort {

    private val helper = PlayerFileHelper();

    override fun persist(p: Player) {

        var file = helper.playerFile( p.key() , "player.json" );
        var json = GsonUtils.mapper().toJson( p );

        helper.write( file , json );
    }

}