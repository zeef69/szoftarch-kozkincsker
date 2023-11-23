package hu.bme.aut.szoftarch.kozkincsker.data.model

import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import kotlinx.parcelize.Parcelize

/**
 * User osztály
 * @param id   Firebase generált id
 * @param uid  Autentikációs id
 * @param name Felhasználó neve
 * @param score összes szerzett pont a küldetésekből
 * @param actualLanguage nyelvbeállítás
 * @param isAdmin admin státus, default false
 * @param ableToEvaluate értékelés írási engedély
 * @param privatePlayableMissionIds privát játszható játékok
 * @param designedMissionIds által tervezett játékok
 * @param currentSessionIds által játszott/moderált sessionök
 * @param badgeIds gyűjtött jelvények
 * */
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
