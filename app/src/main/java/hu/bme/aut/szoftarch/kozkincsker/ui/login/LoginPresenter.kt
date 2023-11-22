package hu.bme.aut.szoftarch.kozkincsker.ui.login

import android.content.Context
import co.zsmb.rainbowcake.navigation.Navigator
import co.zsmb.rainbowcake.withIOContext
import hu.bme.aut.szoftarch.kozkincsker.domain.AuthInteractor
import javax.inject.Inject

class LoginPresenter @Inject constructor(
    private val authInteractor: AuthInteractor
) {
    suspend fun login(navigator: Navigator?, context: Context, mail: String, pass: String) = withIOContext {
        authInteractor.loginClick(navigator, context, mail, pass)
    }

    suspend fun register(context: Context, mail: String, pass: String, name: String) = withIOContext {
        authInteractor.registerClick(context, mail, pass, name)
    }
}