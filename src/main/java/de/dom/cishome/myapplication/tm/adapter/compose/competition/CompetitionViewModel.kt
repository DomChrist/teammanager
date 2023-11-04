package de.dom.cishome.myapplication.tm.adapter.compose.competition

import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.ViewModel
import de.dom.cishome.myapplication.tm.adapter.compose.player.shared.PlayerListFilter
import de.dom.cishome.myapplication.tm.adapter.out.PlayerPersistenceAdapter

class CompetitionViewModel : ViewModel(){

    fun load(){
        PlayerPersistenceAdapter().readAll(PlayerListFilter.byPlayer("1234")){}

    }



}