package de.dom.cishome.myapplication.tm.adapter.`in`.compose.competition.components

import android.content.Context
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import de.dom.cishome.myapplication.compose.shared.TmColors
import de.dom.cishome.myapplication.compose.shared.TmDevice
import java.time.Duration
import java.time.LocalDateTime

@Composable
fun CompetitionTimer(){
    var running = remember { mutableStateOf(false) }
    var txt = remember {
        mutableStateOf("00:00")
    }

    key(txt.value){

        var change: (s: Long)->Unit = {
            txt.value = intToNumber( it );
        }
        content(txt = txt, running = running, change)
    }

}

fun CompetitionTimerLabel(){

}


@Composable
private fun content(
    txt: MutableState<String>,
    running: MutableState<Boolean>,
    change: (s: Long) -> Unit
){
    val context = LocalContext.current;
    val c = TmColors.Competition;
    val cButton = ButtonDefaults.buttonColors( c.secondary , c.primary )

    if( running.value ){
        Button( modifier = Modifier.fillMaxWidth(),colors=cButton, onClick = { running.value = false }) {
            Text("${txt.value}")
        }
    } else {
        Button( modifier=Modifier.fillMaxWidth(), colors=cButton, onClick = { start(txt,running, change, context ) }) {
           Text("START" )
        }
    }
}

private fun start(
    txt: MutableState<String>,
    running: MutableState<Boolean>,
    change: (s: Long) -> Unit,
    ctx: Context
){
    txt.value = "Now";


    var now = LocalDateTime.now();
    running.value = true;
    change(0)
    var t = Thread {
        while (running.value){
            var d = Duration.between( now , LocalDateTime.now() )
            change( d.seconds )
            if( d.seconds == 0L ){
                TmDevice.vibrate(ctx)
            }else if( (d.seconds % 60) == 0L ){
                Thread {
                    TmDevice.vibrate( ctx , d.toMinutes().toInt()-1 )
                }.start()
            } else if( d.toMinutes() > 10){
                TmDevice.vibrate(ctx , 5);
                running.value = false;
            }

        }
    };
    t.start();
    /*
    runBlocking {
        while ( running.value ){
            var d = Duration.between( now , LocalDateTime.now() )
            change( "${d.seconds}" )
            txt.value = "${d.seconds}"
            delay( 1000 )
        }
    }
    */
}

private fun intToNumber( i: Long ): String {
    var m = i / 60
    var mString = if(m < 10)   "0${m}" else "${m}";

    var s = i % 60;
    var sString = if(s < 10) "0${s}" else "${s}"

    return "${mString}:${sString}"
}


