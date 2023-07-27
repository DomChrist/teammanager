package de.dom.cishome.myapplication.compose.shared

import androidx.compose.ui.graphics.Color
import de.dom.cishome.myapplication.R

class TmColors {

    companion object Theme {

        var PRIMARY: Long = 0xff37474f;
        var primaryColor: Color = Color( PRIMARY );

        public val secondary: Long = 0xfffafafa;
        public val secondaryColor: Color = Color(secondary);

        var App: MyColorTheme = MyColorTheme(primaryColor, secondaryColor)
        var Competition: MyColorTheme = MyColorTheme(Color(0xff1565c0), Color(0xff64b5f6) )

    }

}

data class MyColorTheme(
    var primary: Color,
    var secondary: Color,
    var primaryText: Color = secondary,
    var secondaryText: Color = primary
)