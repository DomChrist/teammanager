package de.dom.cishome.myapplication.tm.adapter.`in`.compose.competition.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import de.dom.cishome.myapplication.compose.shared.TmColors
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.competition.model.GameResult
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.competition.model.GameTimer
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.shared.Tm


class CompetitionGamePage {


    private lateinit var running: MutableState<Boolean>;
    private lateinit var goalsTeam1: MutableState<Int>;
    private lateinit var goalsTeam2: MutableState<Int>;
    private lateinit var screenTime: MutableState<GameTimer.GameTime>
    private lateinit var gameEvents: MutableState<MutableList<GameTimeEvent>>

    private var timer: GameTimer.Timer? = null;

    @Composable
    fun Screen(){
        this.running = remember{ mutableStateOf(false) }
        this.goalsTeam1 = remember{ mutableStateOf(0) }
        this.goalsTeam2 = remember{ mutableStateOf(0) }
        this.screenTime = remember {
            mutableStateOf( GameTimer.GameTime.withMinutes(8))
        }
        this.gameEvents = remember{ mutableStateOf( mutableListOf<GameTimeEvent>() ) }

        val clicks = Clicks( {
            this.timer = GameTimer.Timer( this.screenTime , this.running);
            this.goalsTeam1.value = 0;
            this.goalsTeam2.value = 0;
            this.timer!!.start();
        },
        {
            this.timer!!.reset()
        },
            {
                if( running.value ){
                    val secondsAgo = this.timer?.secondsAgo() ?: 0;
                    this.goalsTeam1.value = this.goalsTeam1.value.inc()
                    this.gameEvents.value.add( GameTimeEvent.GOAL(secondsAgo , goalsTeam1.value , goalsTeam2.value) );
                }
            }
            ,
            {
                if( running.value ){
                    val secondsAgo = this.timer?.secondsAgo() ?: 0;
                    this.goalsTeam2.value = this.goalsTeam2.value.inc()
                    this.gameEvents.value.add( GameTimeEvent.GOAL(
                        secondsAgo,
                        goalsTeam1.value,
                        goalsTeam2.value
                    ) );
                }
            }
        )

        content( clicks );
    }

    @Composable
    private fun content(clicks: Clicks) {
        val colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.White,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = Color.DarkGray
        )
        val buttons = ButtonDefaults.buttonColors(
            containerColor = TmColors.primaryColor,
            contentColor = TmColors.secondaryColor
        )

        Scaffold(
            topBar = {Tm.components().TmTopBar()},
            bottomBar = {
                bottomBar(colors, buttons , clicks)
            },
            content = { body(it,clicks) }
        )

    }

    @Composable
    private fun bottomBar(
        colors: ButtonColors,
        buttons: ButtonColors,
        clicks: Clicks
    ) {
        val isRunning = this.running.value;

        Column(modifier = Modifier.background(Color.Black)) {
            Row() {
                Column(verticalArrangement = Arrangement.Center, modifier = Modifier.weight(3f)) {
                    Button(
                        onClick = clicks.onTeam1Goal,
                        colors = colors,
                        modifier = Modifier
                            .padding(55.dp, 5.dp)
                            .fillMaxWidth()
                    ) {
                        Icon(
                            Icons.Filled.AddCircle,
                            tint = TmColors.secondaryColor,
                            contentDescription = "Localized description"
                        )
                    }
                }
                Column(modifier = Modifier.weight(3f)) {
                    Button(
                        onClick = clicks.onTeam2Goal,
                        colors = colors,
                        modifier = Modifier
                            .padding(55.dp, 5.dp)
                            .fillMaxWidth()
                    ) {
                        Icon(
                            Icons.Filled.AddCircle,
                            tint = TmColors.secondaryColor,
                            contentDescription = "Localized description"
                        )
                    }
                }
            }
            Row() {
                Column(modifier = Modifier.weight(2f)) {}
                Column(modifier = Modifier.weight(2f)) {
                    if( !isRunning ){
                        Button(
                            colors = buttons,
                            modifier = Modifier
                                .padding(25.dp, 2.dp)
                                .fillMaxWidth(),
                            onClick = clicks.onStart,
                        ) {
                            Icon(
                                Icons.Filled.PlayArrow,
                                tint = TmColors.secondaryColor,
                                contentDescription = "Localized description"
                            )
                        }
                    } else {
                        Button(
                            colors = buttons,
                            modifier = Modifier
                                .padding(25.dp, 2.dp)
                                .fillMaxWidth(),
                            onClick = clicks.onStop,
                        ) {
                            Icon(
                                Icons.Filled.Close,
                                tint = TmColors.secondaryColor,
                                contentDescription = "Localized description"
                            )
                        }
                    }

                }
                Column(modifier = Modifier.weight(2f)) {}
            }


        }
    }

    @Composable
    private fun body(p: PaddingValues, clicks: Clicks) {

        val view = this.screenTime.value.timeTable();
        val team1 = this.goalsTeam1.value;
        val team2 = this.goalsTeam2.value;

        Box( modifier = Modifier.padding(p) ){

            Row{
                Column(modifier = Modifier
                    .background(Color.Black)
                    .padding(0.dp, 10.dp)) {

                    ScoreBoard( view, clicks, team1, team2 )
                }
            }
        }

        Box( modifier = Modifier.padding(14.dp , 270.dp) ){
            Text("EVENTS:")
            LazyVerticalGrid(columns = GridCells.Fixed(1) , modifier = Modifier
                .background(Color.White),
                verticalArrangement = Arrangement.spacedBy(25.dp),
                horizontalArrangement = Arrangement.spacedBy(25.dp) ){
                items( gameEvents.value.size , key = {it}){
                    var event = gameEvents.value[ it ];
                    Card(modifier = Modifier.padding(5.dp)) {
                        Text(text = event.string())
                    }

                    if( false ){
                        Card {
                            Row(){
                                Column( modifier = Modifier.weight(1f)) {
                                    Text("hallo")
                                }
                                Column( modifier = Modifier.weight(8f)) {
                                    Text("hallo")
                                }
                                Column( modifier = Modifier.weight(1f)) {
                                    Button(
                                        onClick = {},
                                        modifier = Modifier
                                            .padding(55.dp, 5.dp)
                                            .fillMaxWidth()
                                    ) {
                                        Icon(
                                            Icons.Filled.AddCircle,
                                            tint = TmColors.secondaryColor,
                                            contentDescription = "Localized description"
                                        )
                                    }
                                }
                            }

                        }
                    }

                }
            }
        }

    }

    @Composable
    private fun ScoreBoard(view: GameTimer.GameTimeView, clicks: Clicks, team1: Int, team2: Int) {
        var textUnit = TextUnit(14f , TextUnitType.Em);
        var timeImageModifier = Modifier
            .width(50.dp)
            .height(15.dp)
            .scale(15f)
            .padding(5.dp);
        var t1Result = GameResult(team1);
        var t2Result = GameResult(team2);

        Row {
            Column(modifier=Modifier.fillMaxWidth()) {

                Row(  ){
                    Column(modifier=Modifier.weight(1f)) {}
                    Column( modifier=Modifier.weight(3f) ) {
                        Row( modifier = Modifier
                            .padding(25.dp)
                            .fillMaxWidth() ){
                            Column {
                                Image( modifier= timeImageModifier, painter = painterResource( view.m0() ) , contentDescription = "sdfsdf" )
                            }
                            Column {
                                Image( modifier= timeImageModifier, painter = painterResource( view.m1() ) , contentDescription = "sdfsdf" )
                            }
                            Image( modifier= timeImageModifier, painter = painterResource( view.s0() ) , contentDescription = "sdfsdf" )
                            Image( modifier= timeImageModifier, painter = painterResource( view.s1() ) , contentDescription = "sdfsdf" )
                        }
                    }
                    Column(modifier=Modifier.weight(1f)) {}
                }
            }
        }

        Row {
            Column(modifier = Modifier.weight(6f)) {
                Text("Kettig".uppercase() ,color=Color.White, fontSize= TextUnit(5f,TextUnitType.Em), modifier= Modifier
                    .fillMaxWidth()
                    .padding(5.dp), textAlign = TextAlign.Left)
            }
            Column(modifier = Modifier.weight(6f)) {
                Text("Gegner".uppercase() , fontSize= TextUnit(5f,TextUnitType.Em), color=Color.White, modifier=Modifier.fillMaxWidth(), textAlign = TextAlign.Right)
            }
        }

        Row(modifier = Modifier.padding(5.dp)) {
            val color = CardDefaults.cardColors( contentColor = Color.DarkGray , containerColor = Color.DarkGray )
            Column(modifier = Modifier
                .clickable { goalsTeam1.value = goalsTeam1.value.dec() }
                .weight(4f)) {
                val r = t1Result.resources();
                Row(modifier=Modifier.padding(25.dp)) {
                    Image( modifier= timeImageModifier, painter = painterResource( r[0] ) , contentDescription = "sdfsdf" )
                    Image( modifier= timeImageModifier, painter = painterResource( r[1] ) , contentDescription = "sdfsdf" )
                }
            }
            Column(modifier = Modifier.weight(4f)) {

            }
            Column(modifier = Modifier
                .weight(4f)
                .clickable { clicks.onTeam2Goal() }
            ) {
                val r = t2Result.resources();
                Row(modifier=Modifier.padding(25.dp)) {
                    Image( modifier= timeImageModifier, painter = painterResource( r[0] ) , contentDescription = "sdfsdf" )
                    Image( modifier= timeImageModifier, painter = painterResource( r[1] ) , contentDescription = "sdfsdf" )
                }
            }
        }
    }


    private data class Clicks(
        val onStart: () -> Unit,
        val onStop: () -> Unit,
        val onTeam1Goal: () -> Unit,
        val onTeam2Goal: () -> Unit
    )


    data class GameTimeEvent( val timeStamp: Int , val text: String  ){
        fun string(): String {
            val m = timeStamp / 60;
            val s = timeStamp % 60;

            return "$m:$s $text"
        }

        companion object{
            fun GOAL(timeStamp: Int, value: Int, value1: Int): GameTimeEvent = GameTimeEvent( timeStamp , "TOOOOOOOOOR! $value : $value1" )

        }

    }

}





@Preview
@Composable
private fun preview(){

    CompetitionGamePage().Screen();

}