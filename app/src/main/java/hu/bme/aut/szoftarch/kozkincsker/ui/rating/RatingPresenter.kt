package hu.bme.aut.szoftarch.kozkincsker.ui.rating

import co.zsmb.rainbowcake.withIOContext
import hu.bme.aut.szoftarch.kozkincsker.data.model.Feedback
import hu.bme.aut.szoftarch.kozkincsker.domain.MissionInteractor
import javax.inject.Inject

class RatingPresenter @Inject constructor(
    private val missionInteractor: MissionInteractor
) {
    suspend fun newFeedback(feedback: Feedback, missionId: String) = withIOContext {
        missionInteractor.newFeedback(feedback, missionId)
    }
}