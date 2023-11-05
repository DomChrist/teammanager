package de.dom.cishome.myapplication.tm.adapter.compose.startscreen.permission

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import de.dom.cishome.myapplication.tm.adapter.device.DevicePermission.Companion.CheckPermission

class PermissionScreen {



    @Composable
    @OptIn(ExperimentalPermissionsApi::class)
    fun Screen(){

        Text("Ich brauche ganz viele rechte!!!!")

        CheckPermission()



    }



}