package hu.bme.aut.szoftarch.kozkincsker.data.enums

import com.google.firebase.firestore.PropertyName
import hu.bme.aut.szoftarch.kozkincsker.R

enum class MissionType(val translation: Int) {
    @PropertyName("outdoor")
    Outdoor(R.string.mission_type_outdoor),
    @PropertyName("indoor")
    Indoor(R.string.mission_type_indoor),
    @PropertyName("nocturnal")
    Nocturnal(R.string.mission_type_nocturnal),
    @PropertyName("hard")
    Hard(R.string.mission_type_hard),
    @PropertyName("easy")
    Easy(R.string.mission_type_easy),
    @PropertyName("education")
    Education(R.string.mission_type_education),
    @PropertyName("adventure")
    Adventure(R.string.mission_type_adventure),
    @PropertyName("photo")
    Photo(R.string.mission_type_photo),
    @PropertyName("urban")
    Urban(R.string.mission_type_urban),
}
/*
*
* */