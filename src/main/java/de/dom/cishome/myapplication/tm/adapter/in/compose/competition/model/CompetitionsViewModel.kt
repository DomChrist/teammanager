package de.dom.cishome.myapplication.tm.adapter.`in`.compose.competition.model

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import de.dom.cishome.myapplication.tm.application.services.CompetitionApplicationService
import de.dom.cishome.myapplication.tm.application.domain.competition.NewCompetitionCommand
import de.dom.cishome.myapplication.tm.application.port.out.CompetitionReader

class CompetitionsViewModel(
    val state: SavedStateHandle?,
    val app: CompetitionApplicationService
) : ViewModel(){

    fun clickModel( nav: NavController ): CompetitionViewClick {
        return CompetitionViewClick( {nav.navigateUp()} , {nav.navigate(it)} , { app.handle(it) } );
    }

    fun team(){
        if( state != null ){
        }
    }

    fun repo(): CompetitionReader {
        if( this.state != null ){
            this.state.set("jo" , "jo");
        }
        return app.reader;
    }

}

data class CompetitionViewClick(
    var back: ()->Unit,
    var navTo: (route: String) -> Unit,
    var onCreateCompetition: (c: NewCompetitionCommand) -> Unit
)
