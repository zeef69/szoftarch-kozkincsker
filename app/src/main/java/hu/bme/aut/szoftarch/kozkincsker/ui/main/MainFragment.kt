package hu.bme.aut.szoftarch.kozkincsker.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.extensions.exhaustive
import co.zsmb.rainbowcake.hilt.getViewModelFromFactory
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.szoftarch.kozkincsker.ui.account.AccountFragment
import hu.bme.aut.szoftarch.kozkincsker.ui.missions.MissionsFragment
import hu.bme.aut.szoftarch.kozkincsker.views.helpers.FullScreenLoading
import hu.bme.aut.szoftarch.kozkincsker.views.nav.MainScreenView

@AndroidEntryPoint
class MainFragment: RainbowCakeFragment<MainViewState, MainViewModel>() {
    override fun provideViewModel() = getViewModelFromFactory()

    private lateinit var missionsFragment: MissionsFragment
    private lateinit var accountFragment: AccountFragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        missionsFragment = MissionsFragment()
        accountFragment = AccountFragment()

        return ComposeView(requireContext()).apply {
            setContent {
                FullScreenLoading()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setNav()
    }

    override fun render(viewState: MainViewState) {
        (view as ComposeView).setContent {
            when (viewState) {
                is Loading -> FullScreenLoading()
                is MainContent -> {
                    MainScreenView(
                        parentFragmentManager,
                        missionsFragment,
                        accountFragment
                    )
                }
            }.exhaustive
        }
    }
}