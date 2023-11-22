package hu.bme.aut.szoftarch.kozkincsker.ui.account

import android.content.Context
import co.zsmb.rainbowcake.withIOContext
import hu.bme.aut.szoftarch.kozkincsker.data.model.User
import hu.bme.aut.szoftarch.kozkincsker.domain.AuthInteractor
import hu.bme.aut.szoftarch.kozkincsker.domain.UserInteractor
import javax.inject.Inject

class AccountPresenter @Inject constructor(
    private val authInteractor: AuthInteractor, private val userInteractor: UserInteractor
) {
    suspend fun getUserEmail(): String? = withIOContext {
        return@withIOContext authInteractor.getCurrentUserEmail()
    }

    suspend fun signOut() = withIOContext {
        authInteractor.signOut()
    }

    suspend fun changePassword() = withIOContext {
        authInteractor.sendPasswordReset()
    }

    suspend fun changeEmail(context: Context, password: String?, newEmail: String?) = withIOContext {
        authInteractor.changeEmail(context, password, newEmail)
    }

    suspend fun getUser(): User? = withIOContext {
        return@withIOContext userInteractor.getCurrentUser()
    }
}