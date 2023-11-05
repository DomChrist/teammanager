package de.dom.cishome.myapplication.tm.adapter.compose.player.overview

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.dom.cishome.myapplication.compose.player.pages.NewPlayerCommand
import de.dom.cishome.myapplication.tm.adapter.compose.player.shared.PlayerListFilter
import de.dom.cishome.myapplication.tm.adapter.out.PlayerPersistenceAdapter
import de.dom.cishome.myapplication.tm.adapter.out.player.PlayerRestCall
import de.dom.cishome.myapplication.tm.application.domain.player.model.Player
import de.dom.cishome.myapplication.tm.application.domain.player.model.PlayersTeamModel
import de.dom.cishome.myapplication.tm.application.domain.player.service.PlayerDomainService
import de.dom.cishome.myapplication.tm.application.domain.player.service.RegisterPlayerDomainService

class PlayerOverviewViewModel : ViewModel(){

    var model: MutableLiveData<PlayersTeamModel> = MutableLiveData<PlayersTeamModel>( PlayersTeamModel(0 , null , listOf()) );

    fun load( criteria: PlayerListFilter){
        PlayerPersistenceAdapter().readAll( criteria ,  ){
            model.postValue( it );
        }
    }

    fun addPlayer(cmd: NewPlayerCommand) {
        val teamId = model.value?.team?.id ?: return;
        cmd.team.value = TextFieldValue(teamId);
        RegisterPlayerDomainService().registerPlayer(cmd.toDomainCommand()){
            load( PlayerListFilter.byTeam(teamId) );
        }
    }


}

