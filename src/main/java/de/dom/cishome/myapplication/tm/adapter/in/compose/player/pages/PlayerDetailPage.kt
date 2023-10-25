package de.dom.cishome.myapplication.tm.adapter.`in`.compose.player.pages

import android.os.Environment
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ChipBorder
import androidx.compose.material3.ChipColors
import androidx.compose.material3.Divider
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import de.dom.cishome.myapplication.R
import de.dom.cishome.myapplication.compose.player.component.PlayerDetailNavigation
import de.dom.cishome.myapplication.compose.player.pages.PlayerNavItem
import de.dom.cishome.myapplication.compose.shared.PlayerFileHelper
import de.dom.cishome.myapplication.compose.shared.TmColors
import de.dom.cishome.myapplication.compose.shared.TmDeviceShare
import de.dom.cishome.myapplication.compose.shared.shotPlayerImage
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.player.model.PlayerViewModel
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.shared.DefaultClickModel
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.shared.Tm
import de.dom.cishome.myapplication.tm.application.domain.player.model.Player
import java.io.File
import java.lang.Exception
import java.time.LocalDate


class PlayerDetailPage( private val defaultClickModel: DefaultClickModel ) {

    private var model: PlayerViewModel? = null;

    private var onTrialParticipationUpdate: ()->Unit = {}

    @Composable
    fun Screen(
        playerId: String,
        model: PlayerViewModel = viewModel( factory = PlayerViewModel.PlayerViewFactory(playerId,
            LocalContext.current ) )
    ){
        this.model = model;
        var playerState = remember{ mutableStateOf<Player?>(null) }
        this.model.let {
            it!!.player.observeForever { playerState.value = it }
        }

        if( playerState.value != null ){
            var player = playerState.value!!;
            val clickModel = this.model!!.clickModel(defaultClickModel);

            val navItems = model.navItemsBy(player, clickModel)
            layout( player , clickModel, navItems )
        } else {
            Tm.components().Loading()
        }
    }

    private fun initClickModel(counter: MutableState<Int>) {
        val clickModel = this.model!!.clickModel(defaultClickModel);
        onTrialParticipationUpdate = {
            this.model?.trialParticipation( counter.value )
        }
    }


    @Composable
    fun layout( p: Player , clicks: PlayerDetailClick, items: List<PlayerNavItem> ){
        var counter = remember{ mutableStateOf(p.state.trial?.trialCount ?: 0) }

        initClickModel( counter )

        if( p.isTrial() ){
            BottomSheetScaffold( sheetContent = {
                BottomSheet( counter )
            }) {
                Scaffold( topBar = {header( clicks.back )} ) {
                    Box(Modifier.padding(it)){
                        PlayerBox(p, listOf())
                        PlayerDetail(p = p, items = items)
                    }
                }
            }
        } else {
            Scaffold( topBar = {header( clicks.back )} ) {
                Box(Modifier.padding(it)){
                    PlayerBox(p, listOf())
                    PlayerDetail(p = p, items = items)
                }
            }
        }

    }

    @Composable
    private fun PlayerDetail(p: Player, items: List<PlayerNavItem>) {
        var ctx = LocalContext.current;
        var COLORS = TmColors.App;
        var assistBox = Modifier
            .fillMaxWidth()
            .padding(10.dp);

        Box(Modifier.padding(5.dp , 150.dp)){

            Row(){
                Column(modifier = Modifier.weight(2f)) {
                    var t = if( p.team == null) "N.A." else p.team.uppercase();
                    AssistChip( modifier = assistBox, onClick = {  }, label = { Text("${t}" , textAlign = TextAlign.Center) } )
                }
                Column(modifier = Modifier.weight(5f)) {}
                Column(modifier = Modifier.weight(2f)) {
                    AssistChip( modifier = assistBox, onClick = { TmDeviceShare.share(ctx,p.fullName()) }, label = { Icon(Icons.Filled.Share,"") } )
                }
            }

            Row( Modifier.padding(0.dp , 55.dp) ){
                Column() {
                    Row {
                        Text( modifier = Modifier.fillMaxWidth(),
                            color = COLORS.primary, fontSize= TextUnit(5.5f, TextUnitType.Em),
                            fontWeight = FontWeight.Bold,
                            text = p.fullName(), textAlign = TextAlign.Center )
                    }
                    Row(){
                        Divider()
                    }
                    Row(){
                        PlayerDetailNavigation(items =  items )
                    }
                }
            }

        }

    }

    @Composable
    private fun PlayerBox(p: Player, items: List<PlayerNavItem>){
        var playerState = remember{ mutableStateOf(p) }
        var max = Modifier.fillMaxWidth();
        var colors = AssistChipDefaults.assistChipColors(containerColor = TmColors.App.primary , labelColor = TmColors.App.primaryText)
        var assistBox = Modifier
            .fillMaxWidth()
            .padding(10.dp);

        Column(max) {
            Row(max) {
                Column(Modifier.weight(2f)) {
                    Row {
                        AssistChip( modifier=assistBox, colors=colors, onClick = {  }, label = {Text(text = "${p.dateOfBirth?.year}")} )
                    }
                }
                Column(Modifier.weight(5f)) {
                    Row(modifier = Modifier.padding(0.dp , 25.dp)){
                        PlayerImage(p = p , onNewImage = {playerState.value = p})
                    }
                }
                Column(Modifier.weight(2f)) {
                    Row {
                        TrialChip(p = p, modifier = assistBox)
                    }
                }
            }
        }

        Box(){

        }


    }

    @Composable
    private fun header(onBackClick: () -> Unit) {
        var title = "Spieleransicht"
        var color = TmColors.App;
        var defaults = TopAppBarDefaults.topAppBarColors( containerColor = color.primary )
        TopAppBar(
            title = { Text(title , color=color.primaryText) },
            navigationIcon = {
                IconButton(onClick = onBackClick ) {
                    Icon(Icons.Filled.ArrowBack, tint=color.primaryText, contentDescription = null)
                }
            },
            actions = {
                // RowScope here, so these icons will be placed horizontally
                IconButton(onClick = onBackClick) {
                    Icon(Icons.Filled.Menu, tint=color.primaryText, contentDescription = "Localized description")
                }
            },
            colors = defaults
        )
    }

    @Composable
    private fun BottomSheet(counter: MutableState<Int>) {

        Box( modifier = Modifier.padding(0.dp , 15.dp) ) {
            Row(){
                Column() {
                    Row( modifier = Modifier.padding(15.dp , 15.dp) ) {
                        Column(modifier = Modifier.weight(1f)) {
                            FilledIconButton( modifier = Modifier
                                .padding(5.dp, 0.dp)
                                .fillMaxWidth(), onClick = { counter.value = counter.value.dec() }) {
                                Text(" - ")
                            }
                        }
                        Column(modifier = Modifier.weight(3f)) {
                            Button( modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .fillMaxWidth(), onClick = { counter.value = counter.value.inc() }) {
                                Text(text = "# ${counter.value}")
                            }
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            FilledIconButton( modifier = Modifier
                                .padding(5.dp, 0.dp)
                                .fillMaxWidth(), onClick = { counter.value = counter.value.inc() }) {
                                Text(" + ")
                            }
                        }
                    }
                    Row( modifier = Modifier.padding( 15.dp , 15.dp) ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Button( modifier = Modifier.fillMaxWidth(), onClick = { /*TODO*/ }) {
                                Text("ABBRUCH")
                            }
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Button( modifier = Modifier.fillMaxWidth(), onClick = onTrialParticipationUpdate ) {
                                Text("SPEICHERN")
                            }
                        }
                    }
                }
            }


        }
    }

    @Composable
    fun PlayerImage( p: Player? , onNewImage: ()->Unit = {} ){
        var id = p?.id ?: "unkown";
        var c = LocalContext.current;

        var file: File? = null;
        val playerImage: Boolean = if( p != null ){
            try{
                file = File(PlayerFileHelper().playerDir(id),"main.jpg"); File( Environment.getExternalStoragePublicDirectory("documents").absolutePath + "/tm/players/${id}/main.jpg" )
                file != null && file.exists()
            }catch ( e: Exception){
                false;
            }
        } else {
            false;
        }

        var m = Modifier
            .size(130.dp)
            .clip(CircleShape)
            .border(2.dp, Color.Gray, CircleShape);

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White.copy(alpha = 0.6f))
                .clickable { shotPlayerImage(p!!.id, c, onNewImage) }
                .padding(5.dp)) {

            if( playerImage ){
                AsyncImage(
                    model = file,
                    placeholder = painterResource( R.drawable.club ),
                    contentScale = ContentScale.FillBounds,
                    modifier = m
                    ,
                    contentDescription = null,
                )
            } else {
                Image(painter = painterResource(id = R.drawable.club),
                    contentScale = ContentScale.FillBounds,
                    contentDescription = "" ,
                    modifier = m
                )
            }

        }

    }

    @Composable
    fun TrialChip( modifier: Modifier = Modifier.padding(5.dp) , p: Player ){
        var trialChipColor: ChipColors;
        var border: ChipBorder;
        if( p.isTrial() ){
            trialChipColor = AssistChipDefaults.assistChipColors( containerColor = Color.Red, labelColor = Color.White );
            border = AssistChipDefaults.assistChipBorder(borderColor = Color.Red)
        } else {
            trialChipColor = AssistChipDefaults.assistChipColors( containerColor = Color.Green, labelColor = Color.White );
            border = AssistChipDefaults.assistChipBorder(borderColor = Color.Green)
        }
        var txt = if(p.isTrial()) "TRIAL" else "AKTIV";
        AssistChip( modifier=modifier, colors=trialChipColor, border = border, onClick = {  }, label = {Text(text = txt)} )
    }

}










data class PlayerDetailClick( var back: ()->Unit ,
                              var navTo: (r: String)->Unit,
                              var onActivate: ( p: Player )->Unit = {},
                              var onTrialParticipation: () -> Unit = {}
);


@Composable
@Preview
fun PlayerDetailPagePreview(){
    var clicks = PlayerDetailClick({},{});
    var player = Player("1234","Dominik","Christ" , LocalDate.of(1988,10,1) , "Bambini");
    var items = listOf<PlayerNavItem>(
        PlayerNavItem("Ansprechpartner" , "" , click =  {
            clicks.navTo("player/detail/${player.id}/contacts")
        }),
        PlayerNavItem("Ansprechpartner" , "" , click =  {
            clicks.navTo("player/detail/${player.id}/contacts")
        })
    )
    PlayerDetailPage( DefaultClickModel() ).layout(
        player,
        clicks,
        items = items,
    )

}




