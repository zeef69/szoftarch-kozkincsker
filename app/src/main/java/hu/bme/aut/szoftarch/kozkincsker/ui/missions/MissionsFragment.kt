package hu.bme.aut.szoftarch.kozkincsker.ui.missions

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
import hu.bme.aut.szoftarch.kozkincsker.data.model.Mission
import hu.bme.aut.szoftarch.kozkincsker.data.model.Session
import hu.bme.aut.szoftarch.kozkincsker.views.Mission
import hu.bme.aut.szoftarch.kozkincsker.views.helpers.FullScreenLoading
import hu.bme.aut.szoftarch.kozkincsker.views.theme.AppUiTheme1

@AndroidEntryPoint
class MissionsFragment : RainbowCakeFragment<MissionsViewState, MissionsViewModel>() {
    override fun provideViewModel() = getViewModelFromFactory()

    companion object {
        private const val EXTRA_MISSION = "MISSION"

        fun newInstance(mission: Mission): MissionsFragment {
            return MissionsFragment().applyArgs {
                putParcelable(EXTRA_MISSION, mission)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        /*
        viewModel.setMission(
            arguments?.getParcelable(EXTRA_MISSION)!!
        )
        */

        return ComposeView(requireContext()).apply {
            setContent {
                FullScreenLoading()
            }
        }
    }

    override fun render(viewState: MissionsViewState) {
        (view as ComposeView).setContent {
            AppUiTheme1 {
                when (viewState) {
                    is Loading -> FullScreenLoading()
                    is MissionsContent -> MissionsContent(
                        isListed = false,
                    )
                }.exhaustive
            }
        }
    }

    private fun onListMissions() {
        viewModel.onListMissions()
    }
}