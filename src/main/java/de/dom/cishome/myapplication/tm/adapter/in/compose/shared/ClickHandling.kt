package de.dom.cishome.myapplication.tm.adapter.`in`.compose.shared

data class ClickHandling(
    var onBack: () -> Unit = {},
    var onNavTo: ( route: String ) -> Unit = {}
)