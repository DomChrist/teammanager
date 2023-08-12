package de.dom.cishome.myapplication.tm.adapter.`in`.compose.competition.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.dom.cishome.myapplication.tm.adapter.out.competition.CompetitionRepository
import de.dom.cishome.myapplication.tm.application.domain.competition.TeamCompetitionModel

class CompetitionDetailViewModel(
    private var repo: CompetitionRepository,
    private var id: String
) : ViewModel() {


    var data: MutableLiveData<TeamCompetitionModel> = MutableLiveData(null);
    init {
        this.repo.read(this.id){
            data.postValue( it );
        }
    }





}

class CompetitionDetailViewModelFactory( var id: String ) : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repository = CompetitionRepository.inject();
        return CompetitionDetailViewModel( repository , id ) as T
    }

}