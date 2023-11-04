package de.dom.cishome.myapplication.tm.adapter.out.player

import de.dom.cishome.myapplication.config.AsyncResponse

class PlayerNoteRepository {

    fun readNotes( playerId: String , call: AsyncResponse<PlayerNoteResponse>){
        Thread{
            val response = PlayerRestCall().playerNotes(playerId);
            if(response?.playerId != null){
                call.onSuccess( response )
            } else {
                call.onSuccess( PlayerNoteResponse(playerId , listOf()) )
            }
        }.start()
    }

    fun createNote( playerId: String , title: String, txt: String , call: AsyncResponse<Boolean> ){
        Thread{
            var c = CreateNoteRequest( title , txt );
            val boolean = PlayerRestCall().createPlayerNote( playerId , c );
            call.onSuccess( boolean );
        }.start();
    }

    fun deleteNote( noteId: String, playerId: String, call: AsyncResponse<Boolean> ){
        Thread{
            PlayerRestCall().deleteNotes( noteId , playerId )
            call.onSuccess(true)
        }.start();
    }

}