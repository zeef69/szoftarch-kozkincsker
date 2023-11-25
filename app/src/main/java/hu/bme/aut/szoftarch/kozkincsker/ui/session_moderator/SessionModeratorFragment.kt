package hu.bme.aut.szoftarch.kozkincsker.ui.session_moderator

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
import hu.bme.aut.szoftarch.kozkincsker.data.model.Session
import hu.bme.aut.szoftarch.kozkincsker.data.model.User
import hu.bme.aut.szoftarch.kozkincsker.views.ModeratorPlayerList
import hu.bme.aut.szoftarch.kozkincsker.views.helpers.FullScreenLoading
import hu.bme.aut.szoftarch.kozkincsker.views.theme.AppUiTheme1

@AndroidEntryPoint
class SessionModeratorFragment : RainbowCakeFragment<SessionModeratorViewState, SessionModeratorViewModel>(){
    override fun provideViewModel() = getViewModelFromFactory()

    companion object {
        private const val SESSION = "SESSION"

        fun newInstance(session: Session): SessionModeratorFragment {
            return SessionModeratorFragment().applyArgs {
                putParcelable(SESSION, session)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        viewModel.addListener(arguments?.getParcelable(SESSION)!!)

        return ComposeView(requireContext()).apply {
            setContent {
                FullScreenLoading()
            }
        }
    }

    override fun render(viewState: SessionModeratorViewState) {
        (view as ComposeView).setContent {
            AppUiTheme1 {
                when (viewState) {
                    is Loading -> FullScreenLoading()
                    is SessionModeratorContent -> ModeratorPlayerList(
                        session = viewState.session,
                        mission = viewState.mission,
                        designer = viewState.designer,
                        players = viewState.players,
                        onUserClicked = ::onUserClicked,
                        onBackClick = { navigator?.pop() }
                    )
                }.exhaustive
            }
        }
    }

    private fun onUserClicked(user : User) {

    }

}