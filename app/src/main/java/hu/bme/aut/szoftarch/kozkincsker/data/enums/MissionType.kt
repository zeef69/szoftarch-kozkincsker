package hu.bme.aut.szoftarch.kozkincsker.data.enums

import com.google.firebase.firestore.PropertyName
import hu.bme.aut.szoftarch.kozkincsker.R

enum class MissionType(val translation: Int) {
    @PropertyName("outdoor")
    Outdoor(R.string.mission_type_outdoor),
    @PropertyName("indoor")
    Indoor(R.string.mission_type_indoor),
}