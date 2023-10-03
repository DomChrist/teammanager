package de.dom.cishome.myapplication.tm.adapter.out.competition

import android.util.Log
import androidx.compose.runtime.MutableState
import de.dom.cishome.myapplication.compose.shared.GsonUtils
import de.dom.cishome.myapplication.compose.shared.PlayerFileHelper
import de.dom.cishome.myapplication.tm.application.domain.competition.TeamCompetitionModel
import de.dom.cishome.myapplication.tm.application.port.out.CompetitionReader
import de.dom.cishome.myapplication.tm.application.port.out.CompetitionWriter
import java.io.File

class CompetitionRepository : CompetitionReader , CompetitionWriter {

    override fun persist(model: TeamCompetitionModel) {
        var h = PlayerFileHelper();
        var json = GsonUtils.mapper().toJson( model )
        val file = h.competitionFile( model.id )
        h.write( file , json )
    }


    override fun readAll(team: String): List<TeamCompetitionModel> {
        var h = PlayerFileHelper();
        val listFiles = h.competitionDir().listFiles();
        if( listFiles == null) return listOf();
        val list = listFiles
            .filter { it.exists() }
            .map {
                var json = h.read(File(it, "competition.json"))
                GsonUtils.mapper().fromJson(json, TeamCompetitionModel::class.java)
            }
            .filter { it.isTeam(team) }
            .filterNotNull()
        return list;
    }

    override fun readAll(team: String, state: MutableState<List<TeamCompetitionModel>>) {
        state.value = readAll(team);
    }

    override fun readAll(team: String, onLoad: (list: List<TeamCompetitionModel>) -> Unit) {
        onLoad( readAll( team ) );
    }

    override fun read(id: String): TeamCompetitionModel? {
        var pfh = PlayerFileHelper();
        val file = pfh.competitionFile(id);
        val json = pfh.read(file);
        return try{
            val model = GsonUtils.mapper().fromJson<TeamCompetitionModel>(json, TeamCompetitionModel::class.java)
            model;
        }catch (e: Exception){
            Log.d("Exception" , json)
            Log.d("Exception" , e.message , e)
            null;
        }
    }

    fun read( id: String , onSuccess: ( t: TeamCompetitionModel )->Unit ){
        Thread{
            var model = read( id );
            if( model != null ){
                Thread.sleep(1500)
                onSuccess(read( id )!!);
            }
        }.start()
    }

    companion object {

        private var repo: CompetitionRepository? = null;

        fun inject(): CompetitionRepository {
            return if( repo != null ){
                this.repo!!
            } else {
                repo = CompetitionRepository();
                repo!!;
            }
        }
    }




}
