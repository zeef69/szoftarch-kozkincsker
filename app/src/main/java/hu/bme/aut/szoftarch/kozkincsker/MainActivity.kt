package hu.bme.aut.szoftarch.kozkincsker

import android.os.Bundle
import co.zsmb.rainbowcake.navigation.SimpleNavActivity
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.szoftarch.kozkincsker.ui.login.LoginFragment
import hu.bme.aut.szoftarch.kozkincsker.ui.main.MainFragment
import hu.bme.aut.szoftarch.kozkincsker.ui.new_mission.NewMissionFragment

@AndroidEntryPoint
class MainActivity : SimpleNavActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            //navigator.add(LoginFragment())
            //navigator.add(MainFragment())
            navigator.add(NewMissionFragment())
        }
    }
}