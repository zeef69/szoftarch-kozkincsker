package hu.bme.aut.szoftarch.kozkincsker.ui.user_list


import hu.bme.aut.szoftarch.kozkincsker.data.model.User

sealed class UserListViewState

data object Loading : UserListViewState()

data class UsersListContent(
    var isLoading: Boolean = false,
    var actualUser: User? = null,
    var users: List<User> = emptyList(),
) : UserListViewState()