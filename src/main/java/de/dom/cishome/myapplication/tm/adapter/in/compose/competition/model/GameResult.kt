package de.dom.cishome.myapplication.tm.adapter.`in`.compose.competition.model

import de.dom.cishome.myapplication.R

class GameResult ( val goals: Int ){

    fun resources(): Array<Int> {
        var r1 = R.drawable.digitalred0;
        var r2 = R.drawable.digitalred0;

        if( goals >= 99 ){
            r1 = resourceId(9);
            r2 = resourceId(9);
        } else if( goals >= 10 ){
            r1 = resourceId( goals / 10 )
            r2 = resourceId( goals % 10 )
        } else if( goals > 0 ){
            r2 = resourceId(goals);
        }
        return arrayOf(r1,r2);
    }


    private fun resourceId( i: Int ): Int {

        return when( i ){
            0 -> R.drawable.digitalred0
            1 -> R.drawable.digitalred1
            2 -> R.drawable.digitalred2
            3 -> R.drawable.digitalred3
            4 -> R.drawable.digitalred4
            5 -> R.drawable.digitalred5
            6 -> R.drawable.digitalred6
            7 -> R.drawable.digitalred7
            8 -> R.drawable.digitalred8
            9 -> R.drawable.digitalred9
            else -> R.drawable.digitalempty
        }

    }

}