package de.dom.cishome.myapplication.tm.adapter.compose.player.notes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.dom.cishome.myapplication.config.AsyncResponse
import de.dom.cishome.myapplication.tm.adapter.out.player.PlayerNoteRepository
import de.dom.cishome.myapplication.tm.adapter.out.player.PlayerNoteResponse

class PlayerNoteViewModel( private val repo: PlayerNoteRepository = PlayerNoteRepository() ): ViewModel() {

    var data: MutableLiveData<PlayerNoteResponse?> = MutableLiveData( null );

    fun load(playerId: String) {
        repo.readNotes(playerId , AsyncResponse(onSuccess = { data.postValue(it) } , onError = {}))
    }


}