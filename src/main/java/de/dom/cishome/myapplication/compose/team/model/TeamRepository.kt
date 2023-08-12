package de.dom.cishome.myapplication.compose.team.model

import de.dom.cishome.myapplication.compose.shared.GsonUtils
import de.dom.cishome.myapplication.compose.shared.PlayerFileHelper
import java.io.File

interface TeamPersistencePort{

    fun write( m: Team );

    fun readAll(): List<Team>;

    fun read( team: String): Team?;

}


class TeamRepository : TeamPersistencePort {

    private var file = PlayerFileHelper();
    private var module = "team";
    private var gson = GsonUtils();

    private var call: RemoteListCall<Team> = RemoteListCall<Team>(emptyList() , 0);

    override fun write(m: Team) {
        var f = File( file.moduleDir( module , "list/${m.label}" ) , "team.json");
        file.write( f , GsonUtils.mapper().toJson(m) )
    }

    override fun readAll(): List<Team> {
        if( call.notExecuted() ){
            var f = file.moduleDir( module , "list" );
            if( f == null || !f.exists() || f.listFiles().isEmpty() ) return emptyList();

            val map = f.listFiles().map {
                var f = File(it, "team.json");
                var json = file.read(f);
                GsonUtils.mapper().fromJson(json, Team::class.java);
            }.filterNotNull()
            call = RemoteListCall(map , 200 );
        }
        return call.list;
    }

    override fun read(team: String): Team? {
        var f = File( file.moduleDir( module , "list/${team}" ) , "team.json");
        if( f == null || !f.exists() ) return null;
        var json = file.read( f );
        return GsonUtils.mapper().fromJson( json , Team::class.java )
    }

}

data class RemoteListCall<T>( var list: List<T> , var requestState: Int = 0 ){

    fun notExecuted() = requestState == 0;

}