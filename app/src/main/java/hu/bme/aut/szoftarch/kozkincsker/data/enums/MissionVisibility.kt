package hu.bme.aut.szoftarch.kozkincsker.data.enums

import com.google.firebase.firestore.PropertyName
enum class MissionVisibility {
    @PropertyName("private")
    Private,
    @PropertyName("public")
    Public
}