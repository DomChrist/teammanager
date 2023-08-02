package de.dom.cishome.myapplication.tm.adapter.`in`.compose.shared

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.player.model.PlayerViewModel
import de.dom.cishome.myapplication.tm.adapter.out.PlayerPersistenceAdapter
import de.dom.cishome.myapplication.tm.adapter.out.PlayerRepository
import de.dom.cishome.myapplication.tm.application.PlayerApplicationService

class TmViewModel(savedStateHandle: SavedStateHandle?) : ViewModel(){

    var playerApp = PlayerApplicationService.inject();
    var playerViewModel = PlayerViewModel( playerApp )

}