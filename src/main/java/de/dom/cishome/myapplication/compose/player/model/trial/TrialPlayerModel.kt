package de.dom.cishome.myapplication.compose.player.model.trial

import android.os.Environment
import de.dom.cishome.myapplication.compose.shared.GsonUtils
import java.io.File
import java.io.FileWriter
import java.time.LocalDate

interface TrialPlayerPort{

    fun byPlayerId( id: String ): TrialPlayerInformation?

    fun add( id: String, date: TrialDate )

}

class FileTrialPlayerRepository: TrialPlayerPort{

    override fun byPlayerId(id: String): TrialPlayerInformation? {
        val dir = File( Environment.getExternalStoragePublicDirectory("documents").absolutePath + "/tm/players/${id}" )
        val file = File( dir , "trial.json")

        var text = file.readText();

        val fromJson = GsonUtils.mapper().fromJson(text, TrialPlayerInformation::class.java);

        return fromJson;

    }

    override fun add(id: String, date: TrialDate) {

        var i: TrialPlayerInformation = byPlayerId(id) ?: TrialPlayerInformation( id , listOf())

        i.add( date )

        var jsom = GsonUtils.mapper().toJson( i );

        val dir = File( Environment.getExternalStoragePublicDirectory("documents").absolutePath + "/tm/players/${id}" )
        val file = File( dir , "trial.json")

        val writer = FileWriter(file)

        writer.write(jsom);

        writer.flush()
        writer.close();
    }

}


data class TrialPlayerInformation( val player: String, val list: List<TrialDate> ){

    fun add( date: LocalDate ){
        this.list + TrialDate( date );
    }

    fun add( date: TrialDate ){
        this.list + date;
    }

    fun count(): Int{
        return this.list.size;
    }

}


data class TrialDate( val date: LocalDate )