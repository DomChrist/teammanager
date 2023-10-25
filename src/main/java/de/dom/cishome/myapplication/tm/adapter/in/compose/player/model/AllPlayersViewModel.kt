package de.dom.cishome.myapplication.tm.adapter.`in`.compose.player.model

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.dom.cishome.myapplication.compose.player.pages.NewPlayerCommand
import de.dom.cishome.myapplication.config.AsyncResponse
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.player.pages.PlayerListFilter
import de.dom.cishome.myapplication.tm.adapter.out.PlayerPersistenceAdapter
import de.dom.cishome.myapplication.tm.application.services.PlayerApplicationService
import de.dom.cishome.myapplication.tm.application.domain.player.model.Player
import de.dom.cishome.myapplication.tm.application.domain.player.service.RegisterPlayerDomainService
import de.dom.cishome.myapplication.tm.application.port.`in`.RegisterPlayerUseCase
import de.dom.cishome.myapplication.tm.application.port.out.PlayerReaderPort

class AllPlayersViewModel(
    var ctx: Context,
    var filter: PlayerListFilter,
    val app: RegisterPlayerUseCase,
    val reader: PlayerReaderPort
) : ViewModel() {

    var all: MutableLiveData<List<Player>> = MutableLiveData<List<Player>>( emptyList() );
    var selectedTeams: MutableLiveData<List<Player>> = MutableLiveData<List<Player>>( emptyList() );


    init {
        this.loadAll();
    }


    fun handle( cmd: NewPlayerCommand) {
        var domainCommand = cmd.toDomainCommand();
        //var contactDomainCommand = cmd.toContactDomainCommand();
        this.app.registerPlayer( domainCommand );

        this.loadAll();
    }

    private fun loadAll(){
        val response = AsyncResponse<List<Player>>( {
            this.all.postValue(it)
            this.selectedTeams.postValue(it);
        } , {} )
        this.reader.readAll( this.filter ){
            response.onSuccess( it )
        }
    }

    class ViewFactory(var ctx: Context, var filter: PlayerListFilter) : ViewModelProvider.Factory{

        companion object{
            private var model: PlayerViewModel? = null;
        }

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val adapter = PlayerPersistenceAdapter();
            val registerService = RegisterPlayerDomainService( adapter , adapter )
            return AllPlayersViewModel( ctx, filter , registerService , adapter ) as T;
        }
    }


}