package hu.bme.aut.szoftarch.kozkincsker.ui.account

import android.content.Context
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(private val accountPresenter: AccountPresenter) : RainbowCakeViewModel<AccountViewState>(
    Loading
) {

    fun setLogin() = execute {
        viewState = AccountContent(isLoading = true)
        val userEmail = accountPresenter.getUserEmail()
        val user = accountPresenter.getUser()
        if(userEmail != null && user != null)
            viewState = AccountContent(isLoading = false, userEmail = userEmail, user = user)
    }

    fun signOut() = execute {
        accountPresenter.signOut()
    }

    fun changePassword() = execute {
        accountPresenter.changePassword()
    }

    fun changeEmail(context: Context, password: String?, newEmail: String?) = execute {
        accountPresenter.changeEmail(context, password, newEmail)
    }
}