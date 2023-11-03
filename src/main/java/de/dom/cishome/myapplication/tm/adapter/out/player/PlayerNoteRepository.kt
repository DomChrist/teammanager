package de.dom.cishome.myapplication.tm.adapter.out.player

import de.dom.cishome.myapplication.config.AsyncResponse

class PlayerNoteRepository {

    fun readNotes( playerId: String , call: AsyncResponse<PlayerNoteResponse>){
        val response = PlayerRestCall().playerNotes(playerId);
        if( response != null ){
            call.onSuccess( response )
        } else {
            call.onSuccess( PlayerNoteResponse(playerId , listOf()) )
        }
    }

    fun createNote( playerId: String , title: String, txt: String , call: AsyncResponse<Boolean> ){
        var c = CreateNoteRequest( title , txt );
        val boolean = PlayerRestCall().createPlayerNote( playerId , c );
        call.onSuccess( boolean );
    }

}