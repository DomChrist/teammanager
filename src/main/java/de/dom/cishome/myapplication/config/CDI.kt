package de.dom.cishome.myapplication.config

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import java.lang.IllegalStateException

class CDI(val ctx: Context) {



    companion object {

        var cdi: CDI? = null;

        fun initialize( ctx: Context ): CDI {
            if( cdi != null ) return cdi!!;
            this.cdi = CDI( ctx );
            return this.cdi!!;
        }

        @Composable
        fun inject(): CDI {
            this.cdi = this.cdi ?: CDI(LocalContext.current);
            if( this.cdi == null ){
                throw IllegalStateException("context is empty. No context found")
            }
            return this.cdi!!;
        }

    }

}