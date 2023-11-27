package hu.bme.aut.szoftarch.kozkincsker.ui.user_list

import co.zsmb.rainbowcake.withIOContext
import hu.bme.aut.szoftarch.kozkincsker.data.model.User
import hu.bme.aut.szoftarch.kozkincsker.domain.UserInteractor
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserListPresenter @Inject constructor(
    private val userInteractor: UserInteractor
) {
    suspend fun getUser(): User? = withIOContext {
        userInteractor.getCurrentUser()
    }
    suspend fun addListener(): Flow<List<User>> = withIOContext {
        userInteractor.getUsersListener()
    }
    suspend fun updateUser(editedUser: User): Unit = withIOContext {
        userInteractor.onEditUser(editedUser)
    }
}