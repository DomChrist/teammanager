package de.dom.cishome.myapplication.tm.adapter.`in`.compose.myteam.model

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.dom.cishome.myapplication.compose.team.model.Team
import de.dom.cishome.myapplication.tm.adapter.out.myteam.TeamPersistenceAdapter
import de.dom.cishome.myapplication.tm.application.services.MyTeamApplicationService

class MyTeamViewModel(val ctx: Context, val id: String?, val app: MyTeamApplicationService = MyTeamApplicationService() ): ViewModel() {

    private val myTeamApp = MyTeamApplicationService.inject();

    val selectedTeam = MutableLiveData<Team>( null );

    init {
        var team = id ?: "";
        if( myTeamApp.selected() != null ){
            this.selectedTeam.postValue( myTeamApp.selected() )
        } else {
            TeamPersistenceAdapter(ctx).findById( team ){ selectedTeam.postValue(it) }
        }
    }



    class MyTeamViewFactory(var ctx: Context, private var selectedTeam: String?) : ViewModelProvider.Factory{

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MyTeamViewModel( ctx, selectedTeam!! ) as T;
        }
    }



}