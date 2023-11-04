package hu.bme.aut.szoftarch.kozkincsker.data.model

import android.os.Parcelable
import hu.bme.aut.szoftarch.kozkincsker.data.enums.MissionState
import hu.bme.aut.szoftarch.kozkincsker.data.enums.MissionVisibility
import java.util.UUID
import kotlinx.parcelize.Parcelize
import java.time.Duration
import java.util.Date

@Parcelize
data class Mission(
    var id: String = UUID.randomUUID().toString(),
    var name: String = "",
    var creationDate: Date = Date(),
    var modificatiobDate: Date = Date(),
    var hoursToSolve: Int = 0,
    var daysToSolve: Int = 0,
    var accessCode: String = "",
    var isPlayableWithoutModerator: Boolean = true,
    var visibility: MissionVisibility = MissionVisibility.Private,
    var state: MissionState = MissionState.Planning,
    var designer: User? = null,
    var missionTags : MutableList<MissionTag> = ArrayList(),
    var feedbacks: MutableList<Feedback> = ArrayList(),
): Parcelable
