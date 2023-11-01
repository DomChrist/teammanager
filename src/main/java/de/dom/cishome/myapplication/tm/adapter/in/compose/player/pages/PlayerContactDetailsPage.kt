package de.dom.cishome.myapplication.tm.adapter.`in`.compose.player.pages

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import de.dom.cishome.myapplication.tm.adapter.compose.player.contact.PlayerContactDetailsPage
import de.dom.cishome.myapplication.tm.adapter.`in`.compose.shared.DefaultClickModel
import de.dom.cishome.myapplication.tm.application.domain.player.model.PlayerContactDetail

private enum class ProgressState{
    NOT_STARTED,
    IN_PROGRESS,
    FINISHED,
    DEACTIVATED
}


@Composable
@Preview
private fun preview(){

    PlayerContactDetailsPage( DefaultClickModel() ).layout(
        listOf(
            PlayerContactDetail("1234" , "Mobile" , "01785237162"),
            PlayerContactDetail("1234" , "Mobile" , "01785237162"),
            PlayerContactDetail("1234" , "Mobile" , "01785237162"),
            PlayerContactDetail("1234" , "Mobile" , "01785237162")
        )
    )

}