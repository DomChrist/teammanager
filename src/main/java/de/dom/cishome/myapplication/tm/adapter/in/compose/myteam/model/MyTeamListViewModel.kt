package de.dom.cishome.myapplication.tm.adapter.`in`.compose.myteam.model

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.dom.cishome.myapplication.compose.team.model.Team
import de.dom.cishome.myapplication.tm.adapter.out.myteam.TeamPersistenceAdapter

class MyTeamListViewModel( ctx: Context ) : ViewModel() {

    var teams: MutableLiveData<List<Team>> = MutableLiveData();

    class Factory(val ctx: Context) : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MyTeamListViewModel( ctx ) as T;
        }
    }

    init {
        TeamPersistenceAdapter(ctx).findAll() { teams.postValue(it) }
    }





}