package de.dom.cishome.myapplication.tm.adapter.device

import android.Manifest
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState

class DevicePermission {

    companion object{

        @OptIn(ExperimentalPermissionsApi::class)
        @Composable
        fun CheckPermission(){
            var permission = rememberMultiplePermissionsState(permissions = listOf<String>(
                //Manifest.permission.READ_EXTERNAL_STORAGE,
                //Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.VIBRATE,
                Manifest.permission.INTERNET,
                Manifest.permission.CALL_PHONE,
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

        @OptIn(ExperimentalPermissionsApi::class)
        @Composable fun CheckPermissionAndAsk(permission: String ){
            val state = rememberPermissionState(permission = permission);
            if( state.status != PermissionStatus.Granted ){
                SideEffect {
                    state.launchPermissionRequest()
                }
            }
        }

    }

}