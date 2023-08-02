package de.dom.cishome.myapplication.tm.application

class TeamApplicationService {

    companion object Factory {
        private var app: TeamApplicationService? = null;

        fun inject(): TeamApplicationService? {
            if( app == null ){
                app = TeamApplicationService();
            }
            return app;
        }

    }



}