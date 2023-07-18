package de.dom.cishome.myapplication.compose.player.pages

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.ContactsContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Divider
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import de.dom.cishome.myapplication.compose.home.TmComponents
import de.dom.cishome.myapplication.compose.player.model.contact.ContactAdapter
import de.dom.cishome.myapplication.compose.player.model.contact.ContactModel
import de.dom.cishome.myapplication.compose.shared.PlayerHelper
import java.lang.String


@Composable
@ExperimentalPermissionsApi
fun PlayerContactPersonPage( nav: NavController ){

    val player = PlayerHelper.player(nav)
    if( player == null ){
        Text(text = "Nothing here")
    } else {
        var repo = ContactAdapter();
        content( repo.previewReadFromPlayer(),  nav )
    }

}

@Composable
@ExperimentalPermissionsApi
private fun content( list: List<ContactModel>, nav: NavController ){
    val c = TmComponents();
    var ctx = LocalContext.current;
    Scaffold( topBar = {c.stage1Header(title = "Ansprechpartner", nav = nav)} ) {

        Box(modifier = Modifier.padding(it)){
            Column() {
                Row( modifier = Modifier.padding(5.dp) ) {
                    LazyColumn(){
                        items( list ){
                            Row(){
                                Column() {
                                    OutlinedCard( modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(25.dp) ) {
                                        Row(){
                                            Column() {
                                                Text(
                                                    modifier=Modifier.padding(5.dp),
                                                    fontWeight= FontWeight.Bold,
                                                    fontSize = TextUnit(6f , TextUnitType.Em),
                                                    text="${it.givenName } ${it.familyName}"
                                                )
                                            }
                                        }

                                        Row(){
                                            Column() {
                                                CallButton(p = it)
                                            }
                                            Column() {
                                                FilledIconButton(onClick = { whatsApp(ctx,"",it.phone) }) {
                                                    Icon(Icons.Default.ExitToApp , "")
                                                }
                                            }
                                        }

                                        Row( modifier = Modifier
                                            .padding(10.dp)
                                            .clickable { phone(ctx, it.phone) } ){
                                            Column(modifier = Modifier
                                                .weight(1f)
                                                .padding(5.dp)  ) {
                                                Icon( imageVector = Icons.Filled.Phone, contentDescription = "PhoneCall" )
                                            }
                                            Column(modifier = Modifier
                                                .weight(3f)
                                                .padding(5.dp)  ) {
                                                Text( text = it.phone )
                                            }
                                        }

                                        Row( modifier = Modifier
                                            .padding(10.dp)
                                            .clickable { whatsApp(ctx, "", it.phone) } ){
                                            Column(modifier = Modifier
                                                .weight(1f)
                                                .padding(5.dp)  ) {
                                                Icon( imageVector = Icons.Filled.ExitToApp, contentDescription = "PhoneCall" )
                                            }
                                            Column(modifier = Modifier
                                                .weight(3f)
                                                .padding(5.dp)  ) {
                                                Text( text = it.phone )
                                            }
                                        }

                                    }
                                }
                            }
                            Divider()
                        }
                    }
                }
            }
        }

    }

}

private fun communication(){

}

@SuppressLint("Range")
@Composable
@ExperimentalPermissionsApi
private fun CallButton( p: ContactModel ){
    var ctx = LocalContext.current
    var camera = rememberPermissionState(permission = "android.permission.READ_CONTACTS")
    OutlinedIconButton(  onClick = {
        if( camera.status.isGranted ){
            val cr: ContentResolver = ctx.contentResolver;
            val cur = cr.query(
                ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null
            )

            if( cur != null && cur?.count ?: 0 > 0 ){
                while ( cur.moveToNext() ){
                    val columnIndex = cur.getColumnIndex(ContactsContract.Contacts._ID);
                    var id = cur.getString(columnIndex)
                    var name = when{
                        cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME) < 0 -> "";
                        else -> cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    }

                }
            }

            phone( ctx , p.phone )

        } else {
            camera.launchPermissionRequest();
        }
    }) {
        Icon( imageVector = Icons.Filled.Call, contentDescription = "PhoneCall" )
    }


}

private fun phone( ctx: Context, nr: kotlin.String ){

    var clear = "tel:" + nr.filter { !it.isWhitespace() }
    var p = Intent(
        Intent.ACTION_DIAL,
        Uri.parse( clear )
    )
    ctx.startActivity(p);
}

private fun whatsApp(ctx: Context, message: kotlin.String, phoneNumber: kotlin.String ){
    var wa = Intent(
        // on below line we are calling
        // uri to parse the data
        Intent.ACTION_VIEW,
        Uri.parse(
            // on below line we are passing uri,
            // message and whats app phone number.
            String.format(
                "https://api.whatsapp.com/send?phone=%s&text=%s",
                phoneNumber,
                message
            )
        )
    )
    ctx.startActivity( wa );
}

@Composable
@Preview
@ExperimentalPermissionsApi
fun PlayerContactPersonPagePreview(){
    var repo = ContactAdapter();
    var nav = rememberNavController()
    content( repo.previewReadFromPlayer(), nav )

}
