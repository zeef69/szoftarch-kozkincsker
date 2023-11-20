package hu.bme.aut.szoftarch.kozkincsker.ui.missions

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.szoftarch.kozkincsker.data.model.Mission
import hu.bme.aut.szoftarch.kozkincsker.data.model.Session
import javax.inject.Inject

@HiltViewModel
class MissionsViewModel @Inject constructor(private val missionStartPresenter: MissionsPresenter) : RainbowCakeViewModel<MissionsViewState>(
    Loading
) {

    fun onListMissions() = execute {
        missionStartPresenter.onListMissions()
    }
}