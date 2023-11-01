package de.dom.cishome.myapplication.tm.adapter.out.myteam

import android.util.Log
import de.dom.cishome.myapplication.compose.shared.GsonUtils
import de.dom.cishome.myapplication.compose.shared.GsonUtils.Companion.fromJson
import de.dom.cishome.myapplication.compose.team.model.Team
import de.dom.cishome.myapplication.config.ConfigProperties
import de.dom.cishome.myapplication.tm.adapter.out.player.PlayerApiObject
import de.dom.cishome.myapplication.tm.application.domain.player.model.Player
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.time.LocalDate

class TeamRestApi(){

    private val client = OkHttpClient();

    fun findAll(): List<TeamResponse> {
        var list:List<TeamResponse> = mutableListOf<TeamResponse>()
        try{
            var url = "${ConfigProperties.SERVER_PATH}:${ConfigProperties.SERVER_PORT}/teams"
            var response: Response = client.newCall(
                Request.Builder()
                    .get()
                    .url(url)
                    .build()
            ).execute();
            var body: String = response.body?.string() ?: "[]";
            Log.i( "ServerResponse" , "${url}:${body}" )
            val json = GsonUtils.mapper()
                .fromJson<Array<TeamResponse>>(body, Array<TeamResponse>::class.java)
            list = json!!.toList();
        }catch( e: Exception){
            e.printStackTrace()
        }
        return list;
    }


    fun findById( teamId: String, onSuccess: (t: TeamResponse) -> Unit , onError: (e:Exception)->Unit ){
        try{
            var url = "${ConfigProperties.SERVER_PATH}:${ConfigProperties.SERVER_PORT}/teams/team/${teamId}";
            var response: Response = client.newCall(
                Request.Builder()
                    .get()
                    .url(url)
                    .build()
            ).execute();
            Log.i("Server Response" , "${response.body?.string() ?: ""}")

        }catch ( e: Exception ){
            e.printStackTrace();
            onError(e);
        }
    }

    fun addNewTeamPlayer( teamId: String , p: Player ): Boolean {
        var url = "${ConfigProperties.SERVER_PATH}:${ConfigProperties.SERVER_PORT}/teams/team/${teamId}/players";
        var json = GsonUtils.mapper().toJson(p);
        var response: Response = client.newCall(
            Request.Builder()
                .post( json.toRequestBody("application/json".toMediaType()) )
                .url(url)
                .build()
        ).execute();
        val responseJson = response.body?.string() ?: "{}";
        Log.i("team_api","response ${response.code} | ${responseJson}")
        return response.isSuccessful;
        //return responseJson.fromJson( PlayerApiObject::class.java )
    }

}



data class TeamResponse(var id: String, var label: String, val ageGroup: AgeGroup){
    fun toModel(): Team {
        return Team(
            id,
            label
        )
    }


}

enum class AgeGroup(val local: String, val international: String, val older: Int, val younger: Int) {

    U19( "A" , "U-19" , 18 , 17 ),
    U17( "B" , "U-17" , 16 , 15 ),
    U15( "C" , "U-15" , 14 , 13 ),
    U13( "D" , "U-13" , 12 , 11 ),
    U11( "E" , "U-11" , 10 , 9 ),
    U9( "F" , "U-9" , 8 , 7 ),
    U7( "Bambini" , "U-7" , 6 , 2 );

    fun current( current: Int = LocalDate.now().year ): CurrentAgeGroup{
        return CurrentAgeGroup( current - younger , current - older )
    }

    fun yearRange(): Pair<Int,Int>{
        var year = LocalDate.now().year;
        return Pair( year - this.younger , year - this.older )
    }

    fun isInGroup(age: Int): Boolean {
        if( age >= younger && age <= older ){
            return true;
        } else if( this == U7 && age < younger ){
            return true;
        }
        return false;
    }

}

data class CurrentAgeGroup( val first: Int, val second: Int){

    companion object {

        fun U19( current: Int = LocalDate.now().year ): CurrentAgeGroup {
            return CurrentAgeGroup( current - 18 , current - 17 );
        }
        fun A(current: Int = LocalDate.now().year) = U19( current)

        fun U17( current: Int = LocalDate.now().year ): CurrentAgeGroup = CurrentAgeGroup( current - 15 , current - 16 );
        fun B( current: Int = LocalDate.now().year ): CurrentAgeGroup = U17(current);

        fun U15( current: Int = LocalDate.now().year ): CurrentAgeGroup = CurrentAgeGroup( current - 13 , current - 14 );
        fun C( current: Int = LocalDate.now().year ): CurrentAgeGroup = U15(current);

        fun U13( current: Int = LocalDate.now().year ): CurrentAgeGroup = CurrentAgeGroup( current - 11 , current - 12 );
        fun D( current: Int = LocalDate.now().year ): CurrentAgeGroup = U13(current);

        fun U11( current: Int = LocalDate.now().year ): CurrentAgeGroup = CurrentAgeGroup( current - 9 , current - 10 );
        fun E( current: Int = LocalDate.now().year ): CurrentAgeGroup = U11(current);

        fun U9( current: Int = LocalDate.now().year ): CurrentAgeGroup = CurrentAgeGroup( current - 7 , current - 8 );
        fun F( current: Int = LocalDate.now().year ): CurrentAgeGroup = U9(current);

        fun U7( current: Int = LocalDate.now().year ): CurrentAgeGroup = CurrentAgeGroup( current - 5 , current - 6 );
        fun Bambini( current: Int = LocalDate.now().year ): CurrentAgeGroup = U7(current);

    }

}