package de.dom.cishome.myapplication.tm.adapter.out.myteam

import android.content.Context
import android.util.Log
import de.dom.cishome.myapplication.R
import de.dom.cishome.myapplication.compose.shared.GsonUtils
import de.dom.cishome.myapplication.compose.team.model.Team
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.time.LocalDate

class TeamRestApi( val ctx: Context){

    private val client = OkHttpClient();

    fun findAll(onSuccess: (t: Array<TeamResponse>) -> Unit, onError: (e: Exception)->Unit) {
        try{
            var url = "${ctx.getString(R.string.server)}:${ctx.resources.getInteger(R.integer.server_port)}/teams"
            var response: Response = client.newCall(
                Request.Builder()
                    .get()
                    .url(url)
                    .build()
            ).execute();
            var body: String = response.body?.string() ?: "[]";
            Log.i( "ServerResponse" , "${url}:${body}" )
            var array = GsonUtils.mapper().fromJson<Array<TeamResponse>>( body , Array<TeamResponse>::class.java )
            onSuccess( array )
        }catch( e: Exception){
            e.printStackTrace()
            onError( e )
        }
    }


    fun findById( onSuccess: (t: TeamResponse) -> Unit , onError: (e:Exception)->Unit ){
        try{
            var url = "${ctx.getString(R.string.server)}:${ctx.resources.getInteger(R.integer.server_port)}/teams/team/id";
            var response: Response = client.newCall(
                Request.Builder()
                    .get()
                    .url(url)
                    .build()
            ).execute();

        }catch ( e: Exception ){
            e.printStackTrace();
            onError(e);
        }
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
    U7( "Bambini" , "U-7" , 6 , 5 );

    fun current( current: Int = LocalDate.now().year ): CurrentAgeGroup{
        return CurrentAgeGroup( current - younger , current - older )
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