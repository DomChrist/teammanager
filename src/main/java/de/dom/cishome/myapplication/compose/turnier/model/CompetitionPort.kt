package de.dom.cishome.myapplication.compose.turnier.model

import androidx.lifecycle.ViewModel
import de.dom.cishome.myapplication.compose.shared.GsonUtils
import de.dom.cishome.myapplication.compose.shared.PlayerFileHelper
import java.io.File

interface CompetitionPort{

    fun write( c: CompetitionModel )

    fun loadAll(): List<CompetitionModel>

    fun loadById( id: String ): CompetitionModel

}


class CompetitionRepository : CompetitionPort {

    override fun write(c: CompetitionModel) {

        var h = PlayerFileHelper();

        var json = GsonUtils.mapper().toJson( c )

        val file = h.competitionFile(c.id)

        h.write( file , json )

    }

    override fun loadAll(): List<CompetitionModel> {
        var h = PlayerFileHelper();

        val listFiles = h.competitionDir().listFiles();

        if( listFiles == null) return listOf();

        val list = listFiles
            .filter { it.exists() }
            .map {
                    var json = h.read(File(it, "competition.json"))
                    GsonUtils.mapper().fromJson(json, CompetitionModel::class.java)
            }
        return list;
    }

    override fun loadById(id: String): CompetitionModel {
        var pfh = PlayerFileHelper();
        val file = pfh.competitionFile(id);
        val json = pfh.read(file);
        val model =
            GsonUtils.mapper().fromJson<CompetitionModel>(json, CompetitionModel::class.java)
        return model;
    }


}


data class CompetitionModel( var id: String, var club: String, var location: String, var date: String )