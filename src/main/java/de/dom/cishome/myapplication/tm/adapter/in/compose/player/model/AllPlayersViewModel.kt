package de.dom.cishome.myapplication.tm.adapter.`in`.compose.player.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.dom.cishome.myapplication.compose.player.pages.NewPlayerCommand
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.player.pages.PlayerListFilter
import de.dom.cishome.myapplication.tm.application.PlayerApplicationService
import de.dom.cishome.myapplication.tm.application.domain.player.model.Player

class AllPlayersViewModel(
    var filter: PlayerListFilter,
    val app: PlayerApplicationService = PlayerApplicationService.inject()
) : ViewModel() {

    var all: MutableLiveData<List<Player>> = MutableLiveData<List<Player>>( emptyList() );
    var selectedTeams: MutableLiveData<List<Player>> = MutableLiveData<List<Player>>( emptyList() );


    init {
        this.selectByModel()
    }

    fun selectByModel(): MutableLiveData<List<Player>> {

        this.app.repo.asyncPlayers {
            val selected = it.filter { filter.filter(it) }
            this.selectedTeams.postValue( selected )
        }

        return this.selectedTeams;
    }

    fun handle( cmd: NewPlayerCommand) {
        var domainCommand = cmd.toDomainCommand();
        var p = this.app.newPlayer( domainCommand );
        this.selectedTeams.postValue( this.selectedTeams!!.value!!.plus( p!! ) as List<Player> )
    }

    class ViewFactory(var filter: PlayerListFilter) : ViewModelProvider.Factory{

        companion object{
            private var model: PlayerViewModel? = null;
        }

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AllPlayersViewModel( filter ) as T;
        }
    }


}