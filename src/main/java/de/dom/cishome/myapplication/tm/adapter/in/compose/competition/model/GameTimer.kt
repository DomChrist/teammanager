package de.dom.cishome.myapplication.tm.adapter.`in`.compose.competition.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import de.dom.cishome.myapplication.R
import de.dom.cishome.myapplication.compose.shared.TmDevice
import java.time.Duration
import java.time.LocalDateTime

class GameTimer {

    data class GameTime( val seconds: Int , val up: Int ){

        companion object{

            fun withMinutes( m: Int ): GameTime {
                return GameTime( m * 60 , 0 );
            }

        }


        fun timeTable(): GameTimeView {
            val remaining = this.seconds - this.up;
            return GameTimeView( remaining );
        }

        fun isRunning(): Boolean {
            return true;
        }

    }

    data class GameTimeView( val time: Int ){

        var minutes: Int = 0;
        var seconds: Int = 0;

        init {
            this.minutes = time / 60;
            this.seconds = time % 60;
        }

        fun stringView(): String{
            val minutesView = if( minutes < 10 ) "0${minutes}" else "$minutes";
            val secondsView = if( seconds < 10 ) "0${seconds}" else "$seconds";

            return "${minutesView} : ${secondsView}";
        }

        fun m0(): Int {
            return this.image(this.minutes / 10);
        }

        fun m1(): Int {
            return this.image( this.minutes % 10 )
        }

        fun s0(): Int {
            return this.image( this.seconds / 10 );
        }

        fun s1(): Int{
            return this.image( this.seconds % 10 );
        }

        fun intMinutes() = this.minutes;

        fun intSeconds() = this.seconds;

        private fun image( i: Int ): Int {
            return when(i){
                0 -> R.drawable.digital0
                1 -> R.drawable.digital1
                2 -> R.drawable.digital2
                3 -> R.drawable.digital3
                4 -> R.drawable.digital4
                5 -> R.drawable.digital5
                6 -> R.drawable.digital6
                7 -> R.drawable.digital7
                8 -> R.drawable.digital8
                9 -> R.drawable.digital9
                else -> R.drawable.digital0
            }
        }

    }


    class Timer(var gt: MutableState<GameTimer.GameTime>,
                        var running: MutableState<Boolean> = mutableStateOf(false),
                        var onFinished: () -> Unit = {},
                        var onMinute:(seconds: Int) -> Unit = {}){

        private var runningThread: Thread? = null;

        private var startTime = LocalDateTime.now();

        private var device: TmDevice = TmDevice()

        fun start(){
            startTime = LocalDateTime.now();
            running.value = true;
            val sleep = 100L;
            this.runningThread = Thread{
                while (running.value){
                    var now = LocalDateTime.now();
                    val secondsBetween = (Duration.between( startTime, now ).seconds);

                    gt.value = GameTimer.GameTime(
                        gt.value.seconds,
                        secondsBetween.toInt()
                    )
                    running.value = gt.value.isRunning()
                    vibrate( gt.value.seconds,onMinute )
                    Thread.sleep( sleep );

                    if( secondsBetween > gt.value.seconds ){
                        running.value = false;
                        onFinished();
                    }

                }
            }
            this.runningThread!!.start();
        }

        private fun vibrate(seconds: Int, onMinute: (seconds: Int) -> Unit) {
            if( (seconds % 60) == 0 ){
                onMinute( seconds )
            }
        }

        fun reset(){
            this.running.value = false;
        }

        fun isRunning(): Boolean = this.running.value;

        fun secondsAgo(): Int {
            val secondsBetween = (Duration.between( startTime, LocalDateTime.now() ).seconds);
            return secondsBetween.toInt();
        }

    }

}