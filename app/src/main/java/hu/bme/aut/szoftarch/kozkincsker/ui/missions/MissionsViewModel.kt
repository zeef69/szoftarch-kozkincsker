package hu.bme.aut.szoftarch.kozkincsker.ui.missions

import androidx.lifecycle.viewModelScope
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.szoftarch.kozkincsker.data.model.Mission
import hu.bme.aut.szoftarch.kozkincsker.data.model.Session
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MissionsViewModel @Inject constructor(private val missionStartPresenter: MissionsPresenter) : RainbowCakeViewModel<MissionsViewState>(
    Loading
) {
    fun addListener() = execute {
        val uid = missionStartPresenter.getUid()
        viewModelScope.launch {
            missionStartPresenter.addListener().collect {
                viewState = MissionsContent(isListed = true)
                viewState = MissionsContent(
                    missions = it,
                    uid = uid,
                    isListed = false
                )
            }
        }
    }

    fun deleteMission(mission: Mission) = execute {
        missionStartPresenter.deleteMission(mission)
    }

    fun joinWithCode(code: String): Session? = runBlocking {
        return@runBlocking missionStartPresenter.joinWithCode(code)
    }
}