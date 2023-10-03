package de.dom.cishome.myapplication.tm.adapter.`in`.compose.contactperson.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.dom.cishome.myapplication.tm.application.services.PlayerApplicationService
import de.dom.cishome.myapplication.tm.application.domain.contactperson.model.ContactModel
import de.dom.cishome.myapplication.tm.application.services.ContactPersonApplicationService

class PlayerContactsViewModel(
    playerId: String,
    app: ContactPersonApplicationService = ContactPersonApplicationService.inject(),
    playerApp: PlayerApplicationService = PlayerApplicationService.inject()
    ) : ViewModel() {

    var data: MutableLiveData<List<ContactModel>> = MutableLiveData<List<ContactModel>>( emptyList() );

    init {
        Thread{
            playerApp.repo.asyncPlayer( playerId ){
                it.contactPersons.forEach {
                    app.reader.readById( it ){
                        val newList = data.value!!.plus(it);
                        data.postValue( newList )
                    }
                }
            }
        }.start()
    }

    class Factory( val playerId: String ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PlayerContactsViewModel( playerId = playerId) as T;
        }
    }

}