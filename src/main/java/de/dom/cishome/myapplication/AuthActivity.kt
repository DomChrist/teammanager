package de.dom.cishome.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.ExperimentalUnitApi
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import de.dom.cishome.myapplication.config.security.SecurityAdapter
import de.dom.cishome.myapplication.config.security.SecurityIdentity
import java.lang.Exception

class AuthActivity: ComponentActivity() {

    @OptIn(ExperimentalUnitApi::class, ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContent{
            Log.i("Auth Activity" , "called")

            var state = remember { mutableStateOf<SecurityIdentity?>( null ) }

            fetchCode( this , state , success = {
                this.startActivity(it)
            } )

            /*
            if ( state.value != null && state.value!!.isSuccessful() ){
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("test", "value")
                intent.putExtra("loggedin", true)
                this.startActivity(intent)
            }
             */

        }
    }



    @OptIn(ExperimentalUnitApi::class, ExperimentalPermissionsApi::class)
    private fun fetchCode(
        activity: ComponentActivity,
        onSuccess: MutableState<SecurityIdentity?>,
        success: (i: Intent) -> Unit
    ){
        try{
            var code: String? = activity.intent.data?.getQueryParameter("code")
            if( code != null ){
                Log.i("auth code " , code )
                SecurityAdapter.codeFromToken( code , success =  {
                    var i = SecurityIdentity.fromResponseToken(it);
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("test", "value")
                    intent.putExtra("loggedin", true)
                    intent.putExtra( "identity" , i)
                    success( intent );
                } );
            }
        }catch ( e: Exception){
            e.printStackTrace();
        }
    }

}