package hu.bme.aut.szoftarch.kozkincsker.ui.rating

import hu.bme.aut.szoftarch.kozkincsker.data.model.Session

sealed class RatingViewState

data object Loading : RatingViewState()

data class RatingContent(
    var isLoading: Boolean = true,
    var session: Session? = null
) : RatingViewState()