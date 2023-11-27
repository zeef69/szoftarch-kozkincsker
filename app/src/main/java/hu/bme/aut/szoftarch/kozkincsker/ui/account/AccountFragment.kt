package hu.bme.aut.szoftarch.kozkincsker.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.extensions.exhaustive
import co.zsmb.rainbowcake.hilt.getViewModelFromFactory
import co.zsmb.rainbowcake.navigation.navigator
import com.google.firebase.FirebaseApp
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.szoftarch.kozkincsker.R
import hu.bme.aut.szoftarch.kozkincsker.views.AccountView
import hu.bme.aut.szoftarch.kozkincsker.views.helpers.FullScreenLoading
import hu.bme.aut.szoftarch.kozkincsker.views.theme.AppUiTheme1

@AndroidEntryPoint
class AccountFragment : RainbowCakeFragment<AccountViewState, AccountViewModel>() {
    override fun provideViewModel() = getViewModelFromFactory()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        FirebaseApp.initializeApp(requireContext())

        return ComposeView(requireContext()).apply {
            setContent {
                FullScreenLoading()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setLogin()
    }

    override fun render(viewState: AccountViewState) {
        (view as ComposeView).setContent {
            AppUiTheme1 {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    when (viewState) {
                        is Loading -> FullScreenLoading()
                        is AccountContent -> AccountView(
                            viewState.user,
                            viewState.userEmail,
                            onEmailChange = ::onEmailChange,
                            onPasswordChange = ::onPasswordChange,
                            onLogout = ::onLogout
                        )
                    }.exhaustive
                }
            }
        }
    }

    private fun onEmailChange(password: String?, newEmail: String?) {
        viewModel.changeEmail(requireContext(), password, newEmail)
    }

    private fun onPasswordChange() {
        viewModel.changePassword()
        Toast.makeText(context, R.string.verification_send_password_change, Toast.LENGTH_SHORT).show()
    }

    private fun onLogout() {
        viewModel.signOut()
        navigator?.pop()
    }
}