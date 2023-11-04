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
    var creationDate: Date = Date(),
    var modificatiobDate: Date = Date(),
    var timeToSolve: Duration, // TODO: Find an API-24-compatible alternative!
    var missionCode: String = "",
    var isPlayableWithoutModerator: Boolean = true,
    var visibility: Visibility = Visibility.PRIVATE,
    var state: State = State.DESIGNING,
    var designer: User? = null,
    var missionTags : MutableList<MissionTag>,
): Parcelable
{

    enum class Visibility {
        PRIVATE, PUBLIC
    }

    enum class State {
        DESIGNING, FINISHED
    }
}
