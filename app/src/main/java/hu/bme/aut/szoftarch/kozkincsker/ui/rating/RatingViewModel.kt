package hu.bme.aut.szoftarch.kozkincsker.ui.rating

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.szoftarch.kozkincsker.data.model.Feedback
import hu.bme.aut.szoftarch.kozkincsker.data.model.Session
import javax.inject.Inject

@HiltViewModel
class RatingViewModel @Inject constructor(private val ratingPresenter: RatingPresenter) : RainbowCakeViewModel<RatingViewState>(
    Loading
) {

    fun setRating(session: Session, score: Int) {
        viewState = RatingContent(false, session, score)
    }

    fun newFeedback(feedback: Feedback, missionId: String) = execute {
        ratingPresenter.newFeedback(feedback, missionId)
    }
}