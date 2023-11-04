package hu.bme.aut.szoftarch.kozkincsker.data.enums

import com.google.firebase.firestore.PropertyName
import hu.bme.aut.szoftarch.kozkincsker.R

enum class MissionVisibility(val translation: Int) {
    @PropertyName("private")
    Private(R.string.mission_visibility_private),
    @PropertyName("public")
    Public(R.string.mission_visibility_public)
}