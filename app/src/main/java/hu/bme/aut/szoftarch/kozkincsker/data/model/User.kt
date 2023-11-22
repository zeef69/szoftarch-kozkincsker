package hu.bme.aut.szoftarch.kozkincsker.data.model

import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import kotlinx.parcelize.Parcelize
@Parcelize
data class User(
    @DocumentId
    var id: String = "",
    var uid: String = "",
    var name: String = "",
    var score: Int = 0,
    var actualLanguage: String = "English",
    var isAdmin: Boolean = false,
    var ableToEvaluate: Boolean = true,
    var privatePlayableMissionIds: MutableList<String> = ArrayList(), //MissionId list
    var designedMissionIds: MutableList<String> = ArrayList(),        //MissionId list
    var currentSessionIds: MutableList<String> = ArrayList(),         //Session List
    var badgeIds: MutableList<String>  = ArrayList(),                 //Badge list
): Parcelable
