package hu.bme.aut.szoftarch.kozkincsker.ui.user_list

import androidx.lifecycle.viewModelScope
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.szoftarch.kozkincsker.data.model.User
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val userListPresenter: UserListPresenter
) : RainbowCakeViewModel<UserListViewState>(
    Loading
) {
    fun addListener() = execute {
        val user = userListPresenter.getUser()
        viewModelScope.launch {
            launch {
                userListPresenter.addListener().collect() {
                    viewState = UsersListContent(
                        users = it,
                        actualUser = user,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun updateUser(editedUser: User) = execute{
        userListPresenter.updateUser(editedUser)
    }
}