package de.dom.cishome.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.booleanResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import de.dom.cishome.myapplication.compose.home.home
import de.dom.cishome.myapplication.compose.team.teamGraph
import de.dom.cishome.myapplication.config.security.SecurityAdapter
import de.dom.cishome.myapplication.config.security.SecurityComponents
import de.dom.cishome.myapplication.config.security.SecurityIdentity
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.competition.competitionGraph
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.player.playerGraph
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.shared.TmViewModel
import de.dom.cishome.myapplication.ui.theme.MyApplicationTheme
import java.lang.Exception

@ExperimentalUnitApi
@ExperimentalPermissionsApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var identity: SecurityIdentity? = loggedInIdentity( intent );
            if( (identity != null ) || !booleanResource(id = R.bool.auth_required)){
                MyApplicationTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        App(  )
                    }
                }
            } else {
                SecurityComponents.LoginScreen {
                    this.startActivity(SecurityAdapter.startAuth(this.applicationContext))
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

}



@Composable
@ExperimentalUnitApi
@ExperimentalPermissionsApi
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
fun App(){

    val navController = rememberNavController();

    var tm = TmViewModel( navController.currentBackStackEntry?.savedStateHandle )

    Routing(navController = navController , tm)
    

}

@ExperimentalMaterialApi
@OptIn(ExperimentalPermissionsApi::class)
@Composable
@ExperimentalUnitApi
private fun Routing( navController: NavHostController, tm: TmViewModel ){
    NavHost(navController = navController, startDestination = "home") {

        composable("home"){
            home(navController)
        }

        playerGraph( navController = navController , tm)

        competitionGraph(navController)

        teamGraph(navController)
    }
}