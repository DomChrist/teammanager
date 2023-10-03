package de.dom.cishome.myapplication.tm.adapter.out.myteam

import android.content.Context
import android.util.Log
import androidx.compose.ui.res.stringResource
import de.dom.cishome.myapplication.R
import de.dom.cishome.myapplication.compose.shared.GsonUtils
import de.dom.cishome.myapplication.compose.shared.PlayerFileHelper
import de.dom.cishome.myapplication.compose.team.model.Team
import de.dom.cishome.myapplication.tm.application.port.out.TeamReaderPort
import de.dom.cishome.myapplication.tm.application.port.out.TeamWriterPort
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.File

class TeamPersistenceAdapter(val ctx: Context) : TeamReaderPort , TeamWriterPort{

    private var file = PlayerFileHelper();
    private var module = "team";

    private val all: MutableList<Team> = mutableListOf();

    override fun findAll(onSuccess: (t: List<Team>) -> Unit) {
        // routing
        Thread(){
            remoteFindAll( onSuccess = { this.all.clear(); this.all.addAll(it); onSuccess(it) } ){
                Log.e("service call" , "service not reachable")
                localFindAll(onSuccess)
            }
        }.start()
    }

    private fun remoteFindAll(onSuccess: (t: List<Team>) -> Unit , onError: ()->Unit) {
        TeamRestApi( ctx = ctx )
            .findAll(
                onSuccess = {
                    onSuccess(it.map { it.toModel() }.toList())
                },
                onError = {
                    onError()
                }
            )
    }

    private fun localFindAll(onSuccess: (t: List<Team>) -> Unit) {
        try{
            var f = file.moduleDir( module , "list" );
            if( f == null || !f.exists() || f.listFiles().isEmpty() ) return;

            val map = f.listFiles().map {
                var f = File(it, "team.json");
                var json = file.read(f);
                GsonUtils.mapper().fromJson(json, Team::class.java);
            }.filterNotNull()
            onSuccess( map )
        }catch (e: Exception){
            e.printStackTrace();
        }
    }


    override fun findById(id: String, onSuccess: (t: Team) -> Unit) {

        if( this.all.isNotEmpty() ){
            var team = this.all.find { it.id.equals(id , true) }
            if( team != null) onSuccess( team )
            return;
        }

        var f = File( file.moduleDir( module , "list/${id}" ) , "team.json");
        if( f == null || !f.exists() ) return;
        var json = file.read( f );
        onSuccess(GsonUtils.mapper().fromJson( json , Team::class.java ));
    }

    override fun persist(t: Team, onSuccess: () -> Unit) {
        var f = File( file.moduleDir( module , "list/${t.label}" ) , "team.json");
        file.write( f , GsonUtils.mapper().toJson(t) )
    }


}