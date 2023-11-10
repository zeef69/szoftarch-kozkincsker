package hu.bme.aut.szoftarch.kozkincsker.data.model

import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import kotlinx.parcelize.Parcelize
@Parcelize
data class User(
    @DocumentId
    var id: String = "",
    var name: String = "",
    var score: Int = 0,
    var actualLanguage: String = "English",
    var isAdmin: Boolean = false,
    var ableToEvaluate: Boolean = true,
    var privatePlayableMissions: MutableList<Mission> = ArrayList(),
    var designedMissions: MutableList<Mission> = ArrayList(),
    var currentSessions: MutableList<Session> = ArrayList(),
    var badges: MutableList<Badge>  = ArrayList(),
): Parcelable
