package hu.bme.aut.szoftarch.kozkincsker.ui.rating

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.extensions.exhaustive
import co.zsmb.rainbowcake.hilt.getViewModelFromFactory
import co.zsmb.rainbowcake.navigation.extensions.applyArgs
import co.zsmb.rainbowcake.navigation.navigator
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.szoftarch.kozkincsker.data.model.Feedback
import hu.bme.aut.szoftarch.kozkincsker.data.model.Session
import hu.bme.aut.szoftarch.kozkincsker.views.Rating
import hu.bme.aut.szoftarch.kozkincsker.views.helpers.FullScreenLoading
import hu.bme.aut.szoftarch.kozkincsker.views.theme.AppUiTheme1

@AndroidEntryPoint
class RatingFragment : RainbowCakeFragment<RatingViewState, RatingViewModel>() {
    override fun provideViewModel() = getViewModelFromFactory()

    companion object {
        private const val EXTRA_SESSION = "SESSION"
        private const val EXTRA_SCORE = "SCORE"

        fun newInstance(session: Session, score: Int): RatingFragment {
            return RatingFragment().applyArgs {
                putParcelable(EXTRA_SESSION, session)
                putInt(EXTRA_SCORE, score)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewModel.setRating(
            arguments?.getParcelable(EXTRA_SESSION)!!,
            arguments?.getInt(EXTRA_SCORE)!!
        )

        return ComposeView(requireContext()).apply {
            setContent {
                FullScreenLoading()
            }
        }
    }

    override fun render(viewState: RatingViewState) {
        (view as ComposeView).setContent {
            AppUiTheme1 {
                when (viewState) {
                    is Loading -> FullScreenLoading()
                    is RatingContent -> Rating(
                        session = viewState.session,
                        score = viewState.score,
                        onSaveClicked = ::onNewFeedback,
                        onBackClick = { navigator?.pop() }
                    )
                }.exhaustive
            }
        }
    }

    private fun onNewFeedback(feedback: Feedback, missionId: String) {
        viewModel.newFeedback(feedback, missionId)
        navigator?.pop()
    }
}