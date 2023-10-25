package de.dom.cishome.myapplication.tm.application.services

import androidx.lifecycle.MutableLiveData
import de.dom.cishome.myapplication.compose.team.model.Team

class MyTeamApplicationService {

    var selectedTeam: MutableLiveData<Team?> = MutableLiveData();


    companion object {
        private var app: MyTeamApplicationService? = null;

        fun inject(): MyTeamApplicationService {
            if( app == null ){
                app = MyTeamApplicationService();
            }
            return app!!;
        }

    }

    fun select(t: Team) {
        this.selectedTeam.postValue( t );
    }

    fun selected(): Team? {
        return this.selectedTeam.value;
    }


}