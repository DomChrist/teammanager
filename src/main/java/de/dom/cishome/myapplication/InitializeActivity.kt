package de.dom.cishome.myapplication

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.unit.ExperimentalUnitApi
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState

class InitializeActivity : ComponentActivity() {

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            View()

        }

    }


    @OptIn(ExperimentalUnitApi::class, ExperimentalPermissionsApi::class)
    @Composable
    fun View(){
        Text("Permissions needed")
        Button(onClick = {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }) {
            Text("go back")
        }
    }

    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    private fun PermissionRequest(){
        var permission = rememberMultiplePermissionsState(permissions = listOf<String>(
            //Manifest.permission.READ_EXTERNAL_STORAGE,
            //Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.VIBRATE,
            Manifest.permission.CAMERA
        ))
        SideEffect {
            if( permission.allPermissionsGranted ){
            } else {
                permission.permissions.forEach {
                    Log.i("Permission" , "${it.permission} -> ${it.status.isGranted}")
                }
                permission.launchMultiplePermissionRequest()
            }
        }
    }

}