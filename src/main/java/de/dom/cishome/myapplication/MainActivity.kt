package de.dom.cishome.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.booleanResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import de.dom.cishome.myapplication.compose.club.clubGraph
import de.dom.cishome.myapplication.compose.shared.TmColors
import de.dom.cishome.myapplication.compose.team.teamGraph
import de.dom.cishome.myapplication.config.CDI
import de.dom.cishome.myapplication.config.ConfigProperties
import de.dom.cishome.myapplication.config.security.SecurityAdapter
import de.dom.cishome.myapplication.config.security.SecurityComponents
import de.dom.cishome.myapplication.config.security.SecurityIdentity
import de.dom.cishome.myapplication.config.security.UserIdentity
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.competition.competitionGraph
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.launch.pages.HomeScreen
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.myteam.myTeamGraph
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.player.playerGraph
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.shared.CommonComponents
import de.dom.cishome.myapplication.tm.application.services.TeamApplicationService
import de.dom.cishome.myapplication.ui.MainControl
import de.dom.cishome.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalUnitApi
@ExperimentalPermissionsApi
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CDI.initialize( LocalContext.current )
            ConfigProperties.initialize( LocalContext.current )
            var identity: SecurityIdentity? = loggedInIdentity( intent );
            if( (identity != null ) || !booleanResource(id = R.bool.auth_required)){
                MyAppSurface(activity = this , identity!!)
            } else {
                LoginScreen()
            }
        }
    }

    @ExperimentalMaterialApi
    @OptIn(ExperimentalPermissionsApi::class)
    @ExperimentalUnitApi
    @Composable private fun MyAppSurface(activity: MainActivity, identity: SecurityIdentity){
        MyApplicationTheme {
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                AppView( activity , identity )
            }
        }
    }

    @Composable private fun LoginScreen(){
        SecurityComponents.LoginScreen(
            onLogin = { ConfigProperties.AUTH_MODE = ConfigProperties.AuthMode.REMOTE; this.startActivity(SecurityAdapter.startAuth(this.applicationContext))},
            onLocal = {
                ConfigProperties.AUTH_MODE = ConfigProperties.AuthMode.LOCAL;
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("test", "value")
                intent.putExtra("loggedin", true)
                intent.putExtra("identity" , SecurityIdentity( status = 200 , UserIdentity("1234" , "Local" , "User" , "local.user@local,de") )  )
                startActivity(intent)
            }
        )
    }

    @Composable
    @ExperimentalMaterialApi
    @OptIn(ExperimentalPermissionsApi::class)
    @ExperimentalUnitApi
    fun AppView(mainActivity: MainActivity , identity: SecurityIdentity) {
        val navController = rememberNavController();
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        val mainControl: MainControl = MainControl.Create( navController , drawerState , scope)

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = { DrawerContent(  mainActivity, mainControl ) }
        ){
            Scaffold(
            ) {
                Box(modifier = Modifier.padding(it)){
                    Routing(navController = navController , mainControl )
                }
            }
        }
    }

    fun loggedInIdentity( i: Intent ): SecurityIdentity? {
        val hasIdentity = i.extras?.keySet()?.contains("identity") ?: false;
        if( hasIdentity ){
            var identity = i.extras?.getSerializable("identity") as SecurityIdentity
            if( identity.isSuccessful() ) return identity;
        }
        return null;
    }


    @OptIn(ExperimentalUnitApi::class, ExperimentalPermissionsApi::class)
    @Composable
    private fun DrawerContent(
        mainActivity: MainActivity,
        mainControl: MainControl
    ) {
        val list = listOf<CommonComponents.CardMenuItem>(
            CommonComponents.CardMenuItem("Verein", "club", R.drawable.clublogo),
            //CommonComponents.CardMenuItem("Player" , "player", R.drawable.club),
            //CommonComponents.CardMenuItem("Turnier" , "competition", R.drawable.tuniert),
            CommonComponents.CardMenuItem("Mein Team" , "myteam", R.drawable.member),
            CommonComponents.CardMenuItem("Platz" , "platz", R.drawable.platz),
        )

        ModalDrawerSheet {

            Column {
                Image( modifier=Modifier.align(Alignment.CenterHorizontally),
                    painter = painterResource(id = R.drawable.clublogo), contentDescription = "")
            }
            Divider()

            list.forEach {
                NavigationDrawerItem(
                    label = { Text(text = it.name) },
                    selected = false,
                    onClick = {
                        mainControl.navTo( it.dest );
                        mainControl.toggleLeftMenu();
                    }
                )
            }
            Divider()
            NavigationDrawerItem(
                label = { Text(text = "MyTeam Activity") },
                selected = false,
                onClick = {
                    val intent = Intent( mainActivity , MyTeamActivity::class.java)
                    intent.putExtra("test", "value")
                    intent.putExtra("loggedin", true)
                    mainActivity.startActivity( intent )
                }
            )


        }
    }

    @ExperimentalMaterialApi
    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    @ExperimentalUnitApi
    private fun Routing(
        navController: NavHostController,
        mainControl: MainControl
    ){

        NavHost(navController = navController, startDestination = "home") {

            composable("home"){
                HomeScreen().Screen( mainControl )
            }

            clubGraph(navController , mainControl)

            playerGraph( navController, mainControl )

            competitionGraph(navController)

            teamGraph(navController , TeamApplicationService.inject())

            myTeamGraph( nav = navController , mainControl = mainControl )
        }
    }

}



@OptIn(ExperimentalUnitApi::class, ExperimentalPermissionsApi::class)
@Composable
private fun DrawerContent(
    mainActivity: MainActivity,
    mainControl: MainControl
) {
    val list = listOf<CommonComponents.CardMenuItem>(
        CommonComponents.CardMenuItem("Verein", "club", R.drawable.clublogo),
        //CommonComponents.CardMenuItem("Player" , "player", R.drawable.club),
        //CommonComponents.CardMenuItem("Turnier" , "competition", R.drawable.tuniert),
        CommonComponents.CardMenuItem("Mein Team" , "myteam", R.drawable.member),
        CommonComponents.CardMenuItem("Platz" , "platz", R.drawable.platz),
    )

    ModalDrawerSheet {

        Column {
            Image( modifier=Modifier.align(Alignment.CenterHorizontally),
                painter = painterResource(id = R.drawable.clublogo), contentDescription = "")
        }
        Divider()

        list.forEach {
            NavigationDrawerItem(
                label = { Text(text = it.name) },
                selected = false,
                onClick = {
                    mainControl.navTo( it.dest );
                    mainControl.toggleLeftMenu();
                }
            )
        }
        Divider()
        NavigationDrawerItem(
            label = { Text(text = "MyTeam Activity") },
            selected = false,
            onClick = {
                val intent = Intent( mainActivity , MyTeamActivity::class.java)
                intent.putExtra("test", "value")
                intent.putExtra("loggedin", true)
                mainActivity.startActivity( intent )
            }
        )


    }
}


@Composable
private fun Header(scope: CoroutineScope, drawerState: DrawerState) {

    val colors = TopAppBarDefaults.topAppBarColors(
        containerColor = TmColors.primaryColor,
        titleContentColor = TmColors.secondaryColor
    )

    TopAppBar(
        title = { androidx.compose.material3.Text(text="Team Manager" , color = Color.White) },
        colors = colors,
        navigationIcon = {
            IconButton(onClick = { scope.launch{ drawerState.open() }  } ) {
                Icon(  imageVector = Icons.Filled.Menu, tint= Color.White, contentDescription = null)
            }
        },
        actions = {
            // RowScope here, so these icons will be placed horizontally
        }
    )

}

@Composable
private fun cdi(){
   // val myTeamApp = rememberSaveable { MyTeamApplicationService(); }
   // val teamApp = rememberSaveable{ TeamApplicationService.inject()!! };

}

