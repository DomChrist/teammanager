package de.dom.cishome.myapplication.config.security

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.MutableState
import de.dom.cishome.myapplication.compose.shared.GsonUtils
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.Serializable
import java.util.Base64

class SecurityIdentity(val status: Int = PENDING, var ident: UserIdentity? = null) : Serializable {


    fun isPending() = status == PENDING;
    fun isSuccessful() = status == SUCCESS;
    fun isError() = status == ERROR;

    companion object Factory{

        const val PENDING: Int = 100;
        const val SUCCESS: Int = 200;
        const val ERROR: Int = 500;

        fun fromResponseToken( r: ResponseToken ): SecurityIdentity {
            return try{
                var split = r.access_token.split(".");
                var user = String( Base64.getDecoder().decode( split[1] ) );

                var ident = GsonUtils.mapper().fromJson<UserIdentity>( user , UserIdentity::class.java );

                Log.i("UserInfo" , user )
                return SecurityIdentity( SUCCESS , ident );
            }catch ( e: Exception ){
                return SecurityIdentity(ERROR)
            }
        }

    }


}

data class UserIdentity( val sub: String, val given_name: String, val family_name: String, val email: String ) : Serializable


class SecurityAdapter()  {





    companion object Factory {





        private val clientId = "account";
        private val realm = "CIS";
        fun startAuth(ctx: Context): Intent {
            val redirect = "app://team-manager.com/callback";
            val url = "http://macbook-pro-von-dominik.local:8082/auth/realms/${realm.uppercase()}/protocol/openid-connect/auth?client_id=${clientId}&redirect_uri=${redirect}&response_type=code";
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse( url ),)
            return intent;
            //ctx.startActivity( intent)
        }

        fun codeFromToken(code: String , success: ( t: ResponseToken)->Unit ){
            var clientSecret = "3d9fdc9d-a492-49d2-8928-bc89df183bf1";
            val redirect = "app://team-manager.com/callback";
            Thread {
                val url =
                    "http://macbook-pro-von-dominik.local:8082/auth/realms/${realm.uppercase()}/protocol/openid-connect/token";
                val client = OkHttpClient();
                var body = FormBody.Builder()
                    .addEncoded("client_id", clientId)
                    //.addEncoded("client_secret" , clientSecret)
                    .addEncoded("grant_type", "authorization_code")
                    .addEncoded("redirect_uri" , redirect)
                    .addEncoded("scope" , "LIFE")
                    .addEncoded("code", code).build();
                val request = Request.Builder().url(url)
                    .post(body).build();

                var call = client.newCall(request);
                var response = call.execute();

                var responseBody = response.body!!.string();
                var token = GsonUtils.mapper().fromJson<ResponseToken>( responseBody , ResponseToken::class.java);

                success( token );

                //Log.i("Security", response.body!!.string());
            }.start()
        }
    }



}

data class ResponseToken( val refresh_token: String, val access_token: String )