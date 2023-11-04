package hu.bme.aut.szoftarch.kozkincsker.data.enums

import com.google.firebase.firestore.PropertyName
import hu.bme.aut.szoftarch.kozkincsker.R

enum class MissionState(val translation: Int) {
    @PropertyName("private")
    Planning(R.string.mission_state_planning),
    @PropertyName("public")
    Done(R.string.mission_state_done)
}