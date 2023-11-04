package hu.bme.aut.szoftarch.kozkincsker.data.model

import android.os.Parcelable
import java.util.UUID
import kotlinx.parcelize.Parcelize
import java.time.Duration
import java.util.Date

@Parcelize
data class Mission(
    var id: String = UUID.randomUUID().toString(),
    var name: String = "",
    var creationDate: Date,
    var modificatiobDate: Date,
    var timeToSolve: Duration,
    var missionCode: String = "",
    var missionTags : MutableList<MissionTag>,
    var isPlayableWithoutModerator: Boolean = true
): Parcelable
