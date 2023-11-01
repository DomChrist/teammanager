package de.dom.cishome.myapplication.tm.adapter.compose.player.contact

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.dom.cishome.myapplication.tm.adapter.out.PlayerPersistenceAdapter
import de.dom.cishome.myapplication.tm.application.domain.player.model.PlayerContactDetail
import de.dom.cishome.myapplication.tm.application.port.out.PlayerReaderPort
import de.dom.cishome.myapplication.tm.application.port.out.UpdatePlayerPort

class PlayerContactViewModel : ViewModel() {

    val data: MutableLiveData<List<PlayerContactDetail>> = MutableLiveData<List<PlayerContactDetail>>( emptyList() );
    val repo: PlayerReaderPort = PlayerPersistenceAdapter()
    val updateRepo: UpdatePlayerPort = PlayerPersistenceAdapter()


    fun load(playerId: String) {

        Thread{
            repo.readCommunications(playerId){
                data.postValue( it )
            }
        }.start()

    }

    fun addDetail( playerId: String, detail: PlayerContactDetail) {
        if( playerId.isBlank() ){
            Log.e("addDetail" , "Player id is empty")
            return;
        }

        updateRepo.create( playerId , detail  ){
            this.load(playerId = playerId)
        }
    }


}

class PlayerContactViewFactory(val player: String) : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PlayerContactViewModel() as T;
    }
}