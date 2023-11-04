package de.dom.cishome.myapplication.tm.adapter.out.player

import android.util.Log
import de.dom.cishome.myapplication.compose.shared.GsonUtils
import de.dom.cishome.myapplication.compose.shared.GsonUtils.Companion.fromJson
import de.dom.cishome.myapplication.config.ConfigProperties
import de.dom.cishome.myapplication.tm.application.domain.player.model.Player
import de.dom.cishome.myapplication.tm.application.domain.player.model.PlayerContactDetail
import de.dom.cishome.myapplication.tm.application.domain.player.model.PlayersTeamModel
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.File
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Base64


class PlayerRestCall {

    private val client = OkHttpClient()

    fun playersInTeam( teamId: String , onError: (e: Exception) -> Unit, onSuccess: ( players: Array<PlayerApiObject> ) -> Unit  ){
        var path = "/teams/team/${teamId}/players"
        var url = "${ConfigProperties.SERVER_PATH}:${ConfigProperties.SERVER_PORT}/${path}"
        val observer = ApiCallObserver.start(url)
        var response: Response = client.newCall(
            Request.Builder()
                .get()
                .url(url)
                .build()
        ).execute()
        observer.close(response)
        var body: String = response.body?.string() ?: "[]"
        var array = GsonUtils.mapper().fromJson<Array<PlayerApiObject>>( body , Array<PlayerApiObject>::class.java )

        onSuccess( array )
    }

    fun playersInTeam( teamId: String ): PlayersTeamModel? {
        try{
            var path = "/teams/team/${teamId}/players"
            var url = "${ConfigProperties.SERVER_PATH}:${ConfigProperties.SERVER_PORT}/${path}"
            val observer = ApiCallObserver.start(url)
            var response: Response = client.newCall(
                Request.Builder()
                    .get()
                    .url(url)
                    .build()
            ).execute()
            observer.close(response)
            var body: String = response.body?.string() ?: "{}"
            Log.i( "ServerResponse" , "${url}:${body}" )
            var array = GsonUtils.mapper().fromJson<TeamPlayerResponse>( body , TeamPlayerResponse::class.java )
            return array.toModel()
        }catch ( e: Exception ){
            e.printStackTrace()
            return null
        }
    }

    fun player( playerId: String , onError: (e: Exception)->Unit, onSuccess: (player: PlayerApiObject) -> Unit ){
        val playerSync = this.playerSync(playerId = playerId)
        if( playerSync != null ){
            onSuccess( playerSync )
        }
        onError( IllegalArgumentException("No player found"))
    }


    fun playerSync( playerId: String ): PlayerApiObject {
        var path = "players/${playerId}"
        var url = "${ConfigProperties.SERVER_PATH}:${ConfigProperties.SERVER_PORT}/${path}"
        var response: Response = httpGet(url)

        var player: PlayerApiObject = serialize( response.body?.string() ?: "{}" , PlayerApiObject::class.java )
        return player
    }


    fun persistNewPlayerInTeam( p: Player , teamId: String): PlayerApiObject? {
            Log.i("PlayerRepository:Persist","persist player to server")
            var path = "teams/team"
        var url = "${ConfigProperties.SERVER_PATH}:${ConfigProperties.SERVER_PORT}/${path}"
            var json = GsonUtils.mapper().toJson( p )
        var body = json.toRequestBody( "application/json".toMediaType() )
            var request = Request.Builder()
                .url( url )
                .post( body = body )
                .build()
        val newCall = client.newCall(request)
        val response = newCall.execute()
        Log.i("PlayerRepository:Persist" , response.message)

            val newPlayer = response.body?.string()?.fromJson( PlayerApiObject::class.java )
        return newPlayer
    }

    fun trialParticipation( count: Int, id: String) {
        val request = Request.Builder()
            .url( "${root()}/players/$id/trial/participation/count/$count" )
            .post( "".toRequestBody(null) )
            .build()
        val response = client.newCall( request )
    }

    fun activateMember( id: String ){
        val json = GsonUtils.mapper().toJson(PlayerRequest.PlayerMembershipRequest(LocalDate.now()))
        val body = json.toRequestBody("application/json".toMediaType())// RequestBody.create(null, byteArrayOf())
        val request = Request.Builder()
            .url( "${root()}/players/$id/member" )
            .post(body)
            .build()
        val response = client.newCall( request ).execute()
        Log.i("ApiCall" , "activateMember: ${response.code}")
    }

    fun playerCommunications( playerId: String ): List<PlayerContactResponse> {
        val request = Request.Builder()
            .url( "${root()}/players/$playerId/communication" )
            .get()
            .build()
        val response = client.newCall( request ).execute()
        if( response.isSuccessful ){
            val json = response.body?.string() ?: "[]"
            val list = serialize( json , Array<PlayerContactResponse>::class.java)
            return list.toList()
        }
        return listOf<PlayerContactResponse>()
    }

    fun playerCommunications( playerId: String , detail: PlayerContactDetail ): Boolean {
        var json = GsonUtils.mapper().toJson( detail )
        var url = "${root()}/players/$playerId/communication"
        val request = Request.Builder()
            .url( url )
            .post( json.toRequestBody("application/json".toMediaType()) )
            .build()
        val response = log(client.newCall(request).execute())
        return response.isSuccessful
    }

    fun playerNotes( playerId: String ): PlayerNoteResponse? {
        var url = "${root()}/players/${playerId}/note"
        val response = httpGet( url )
        try{
            val json = response.body?.string() ?: "{}"
            val fromJson = GsonUtils.mapper().fromJson(json, PlayerNoteResponse::class.java)
            return fromJson
        }catch (e: Exception){
            return null;
        }
    }

    fun createPlayerNote( playerId: String , body: CreateNoteRequest ): Boolean {
        var url = "${root()}/players/${playerId}/note"
        val payload = GsonUtils.mapper().toJson( body ).toRequestBody("application/json".toMediaType())
        val request = Request.Builder()
            .url(url)
            .post( payload )
            .build()
        val response = client.newCall( request ).execute()
        return response.isSuccessful
    }

    fun deleteNotes(noteId: String, playerId: String): Boolean {
        var url = "${root()}/players/${playerId}/note/$noteId"
        val request = Request.Builder()
            .url(url)
            .delete( "".toRequestBody(null) )
            .build()
        val response = client.newCall( request ).execute();
        return response.isSuccessful;
    }


    fun playerImageUpdate( playerId: String , imageFile: File){
        val data = imageFile.readBytes()
        val file = data.toRequestBody("application/octet-stream".toMediaType())

        //val file = String( Base64.getMimeEncoder().encode(data) );

        var mp = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("file" , "file",file )
            .addFormDataPart("name" , "main" )
            .build()

        val request = Request.Builder()
            .url("${root()}/players/$playerId/image")
            .post( mp )
            .build()

        client.newCall( request ).execute()
    }




    private fun root(): String {
        return "${ ConfigProperties.SERVER_PATH }:${ ConfigProperties.SERVER_PORT }"
    }

    private fun <T> serialize(json: String, clazz: Class<T> ): T{
        Log.i("serialize" , json)
        val obj: T = GsonUtils.mapper().fromJson( json,clazz )
        return obj
    }

    private fun log( response: Response ): Response {
        Log.i("ApiCall" , "${response.code} : $response.message : ${response.body?.string() ?: ""}" )
        return response
    }

    private fun httpGet( url: String ): Response {
        val observer = ApiCallObserver.start(url)
        var response: Response = client.newCall(
            Request.Builder()
                .get()
                .url(url)
                .build()
        ).execute()
        return observer.close(response)
    }




    private class ApiCallObserver( val url: String) {

        private val start: LocalDateTime = LocalDateTime.now()

        private lateinit var stop: LocalDateTime

        fun stop(){
            stop = LocalDateTime.now()
        }

        fun duration(): Long {
            return Duration.between(start,stop).toMillis()
        }

        fun close( response: Response ): Response{
            stop()
            return log( response)
        }

        private fun log( response: Response ): Response {
            Log.d("ApiCall" , "-------------")
            Log.i("ApiCall" , "${response.request.method} : $url -> ${response.code}")
            Log.i("ApiCall" , "time: ${duration()}ms")
            //Log.i("ApiCall" , " ${response.message}" );
            Log.d("ApiCall" , "-------------")

            return response
        }

        companion object {
            fun start(url: String): ApiCallObserver {
                return ApiCallObserver(url)
            }
        }

    }


}