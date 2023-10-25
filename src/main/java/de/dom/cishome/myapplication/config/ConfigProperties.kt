package de.dom.cishome.myapplication.config

import android.content.Context
import de.dom.cishome.myapplication.R

class ConfigProperties {


    companion object {

        public var SERVER_PATH: String = ""
        var SERVER_PORT: Int = 8080;
        var AUTH_MODE : AuthMode = AuthMode.LOCAL;


        fun initialize( ctx: Context ){
            SERVER_PATH = ctx.resources.getString( R.string.server )
            SERVER_PORT = ctx.resources.getInteger( R.integer.server_port )
        }

    }



    public enum class AuthMode{
        LOCAL,REMOTE
    }

}