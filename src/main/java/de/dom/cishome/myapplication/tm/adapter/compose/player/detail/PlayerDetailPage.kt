package de.dom.cishome.myapplication.tm.adapter.compose.player.detail

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.os.FileUtils
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.core.content.FileProvider
import androidx.core.net.toFile
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import de.dom.cishome.myapplication.R
import de.dom.cishome.myapplication.compose.player.component.PlayerDetailNavigation
import de.dom.cishome.myapplication.compose.player.pages.PlayerNavItem
import de.dom.cishome.myapplication.compose.shared.PlayerFileHelper
import de.dom.cishome.myapplication.compose.shared.TmColors
import de.dom.cishome.myapplication.compose.shared.TmDeviceShare
import de.dom.cishome.myapplication.compose.shared.shotPlayerImage
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.shared.DefaultClickModel
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.shared.Tm
import de.dom.cishome.myapplication.tm.application.domain.player.model.Player
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import java.nio.file.Files
import java.time.LocalDate

class PlayerDetailPage( val playerId: String , val defaultClickModel: DefaultClickModel) {

    private lateinit var viewModel: PlayerDetailViewModel;

    @Composable
    fun Screen( view: PlayerDetailViewModel = viewModel() ){
        this.viewModel = view;
        val model = remember{ mutableStateOf<Player?>(null) }
        view.playerLifecycle.observeForever { model.value = it.p }

        LaunchedEffect(key1 = Unit){
            view.load( playerId )
        }

        if( view.isSuccessful() ){
            val clickModel = view.clickModel(this.defaultClickModel);
            val navItems = view.navItemsBy(model.value!!, clickModel)
            layout( view.playerLifecycle.value!!.p!! , clickModel, navItems )
        } else {
            Tm.components().Loading()
        }
    }


    @Composable
    fun layout(p: Player, clicks: PlayerDetailClick, items: List<PlayerNavItem> ){
        var counter = remember{ mutableStateOf(p.state.trial?.trialCount ?: 0) }

        //initClickModel( counter )


        if( p.isTrial() ){
            BottomSheetScaffold( sheetContent = {
                TrialPlayerBottomSheet( p.state.trial?.trialCount ?: 0 , p , clicks.onTrialParticipation )
            }) {
                Scaffold( topBar = {header( clicks.back )} ) {
                    Box(Modifier.padding(it)){
                        PlayerBox(p, listOf())
                        PlayerDetail(p = p, items = items , clicks=clicks)
                    }
                }
            }
        } else {
            Scaffold( topBar = {header( clicks.back )} ) {
                Box(Modifier.padding(it)){
                    PlayerBox(p, listOf())
                    PlayerDetail(p = p, items = items , clicks)
                }
            }
        }
    }

    @Composable
    private fun CardLayout() {

        Row(modifier=Modifier.padding(0.dp , 110.dp)){
            Column {
                Surface(modifier=Modifier.size(70.dp).padding(5.dp)
                    .background(Color.Green)
                    .border(2.dp , Color.Red , RoundedCornerShape(10.dp))) {
                    Icon( Icons.Filled.Call , contentDescription = "")
                    Text("Kontaktdaten")
                }
            }
        }

    }


    @Composable
    private fun PlayerDetail(p: Player, items: List<PlayerNavItem> , clicks: PlayerDetailClick) {
        var ctx = LocalContext.current;
        var COLORS = TmColors.App;
        var assistBox = Modifier
            .fillMaxWidth()
            .padding(10.dp);

        Box(Modifier.padding(5.dp , 150.dp)){


            Row(){
                Column(modifier = Modifier.weight(2f)) {
                    var t = if( p.team == null) "N.A." else p.team.uppercase();
                    //TODO TeamName
                    //AssistChip( modifier = assistBox, onClick = {  }, label = { Text("${t}" , textAlign = TextAlign.Center) } )
                }
                Column(modifier = Modifier.weight(5f)) {}
                Column(modifier = Modifier.weight(2f)) {
                    AssistChip( modifier = assistBox, onClick = { TmDeviceShare.share(ctx,p.fullName()) }, label = { Icon(
                        Icons.Filled.Share,"") } )
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
                    if( p.isTrial() ){
                        Row{
                            ActivatePlayerButton { clicks.onActivate(p) }
                        }
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
        var playerStateCounter = remember{ mutableIntStateOf(0) };
        var max = Modifier.fillMaxWidth();
        var colors = AssistChipDefaults.assistChipColors(containerColor = TmColors.App.primary , labelColor = TmColors.App.primaryText)
        var assistBox = Modifier
            .fillMaxWidth()
            .padding(10.dp);

        Column(max) {
            Row(max) {
                Column(Modifier.weight(2f)) {
                    Row {
                        AssistChip( modifier=assistBox, colors=colors, onClick = {  }, label = { Text(text = "${p.dateOfBirth?.year}") } )
                    }
                }
                Column(Modifier.weight(5f)) {
                    Row(modifier = Modifier.padding(0.dp , 25.dp)){
                        PlayerImage(p = p , onNewImage = {
                            playerState.value = p
                            playerStateCounter.value = playerStateCounter.value.inc();
                            viewModel.updateImage(it);
                        })
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
    private fun TrialPlayerBottomSheet(counter: Int, p: Player , onTrialParticipationUpdate: ( count: Int, player: Player )->Unit) {
        var counterState = remember{ mutableStateOf(counter) }
        Box( modifier = Modifier.padding(0.dp , 15.dp) ) {
            Row(){
                Column() {
                    Row( modifier = Modifier.padding(15.dp , 15.dp) ) {
                        Column(modifier = Modifier.weight(1f)) {
                            FilledIconButton( modifier = Modifier
                                .padding(5.dp, 0.dp)
                                .fillMaxWidth(), onClick = { counterState.value = counterState.value.dec() }) {
                                Text(" - ")
                            }
                        }
                        Column(modifier = Modifier.weight(3f)) {
                            Button( modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .fillMaxWidth(), onClick = { counterState.value = counterState.value.inc() }) {
                                Text(text = "# ${counterState.value}")
                            }
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            FilledIconButton( modifier = Modifier
                                .padding(5.dp, 0.dp)
                                .fillMaxWidth(), onClick = { counterState.value = counterState.value.inc() }) {
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
                            Button( modifier = Modifier.fillMaxWidth(), onClick = { onTrialParticipationUpdate(counterState.value , p) } ) {
                                Text("SPEICHERN")
                            }
                        }
                    }
                }
            }


        }
    }

    @Composable
    fun PlayerImage( p: Player? , onNewImage: ( f: File )->Unit = {} ){

        val playerId = p?.id ?: "temp";
        val ctx = LocalContext.current;
        var file: File = File(PlayerFileHelper().playerDir(playerId),"main.jpg"); File( Environment.getExternalStoragePublicDirectory("documents").absolutePath + "/tm/players/${playerId}/main.jpg" )
        val uri = ComposeFileProvider.getImageUri( ctx , playerId = playerId );

        val result =
            rememberLauncherForActivityResult(ActivityResultContracts.TakePicture(), onResult = {
                Log.i("ActivityResult","shot shot shoooooooo!")
                ctx.contentResolver.openInputStream(uri).let {
                    val readAllBytes = it!!.readBytes()
                    it!!.close();
                    PlayerFileHelper().copy(readAllBytes,file)
                    onNewImage( file );
                };
            })

        var id = p?.id ?: "unkown";

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
                .clickable {
                    result.launch( uri )
                    //shotPlayerImage(p!!.id, c, onNewImage)
                }
                .padding(5.dp)) {

            if( playerImage ){
                AsyncImage(
                    model = file,
                    placeholder = painterResource( R.drawable.club ),
                    contentScale = ContentScale.FillBounds,
                    modifier = m,
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
        AssistChip( modifier=modifier, colors=trialChipColor, border = border, onClick = {  }, label = { Text(text = txt) } )
    }


}

@Preview
@Composable
private fun preview(){
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
    PlayerDetailPage("1234" , DefaultClickModel({},{}))
        .layout(p = player, clicks = clicks, items = items)
}


class ComposeFileProvider : FileProvider(
    R.xml.provider_paths
) {
    companion object {
        fun getImageUri(context: Context , playerId: String): Uri {
            val directory = File(context.cacheDir, "images/cache")
            directory.mkdirs()
            val file = File.createTempFile(
                "selected_image_",
                ".jpg",
                directory
            )
            val authority = context.packageName + ".fileprovider"
            return getUriForFile(
                context,
                authority,
                file,
            )
        }
    }
}