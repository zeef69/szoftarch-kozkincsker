package hu.bme.aut.szoftarch.kozkincsker.data.model

import android.opengl.Visibility
import android.os.Parcelable
import com.google.firebase.firestore.PropertyName
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
    var visibility: Visibility = Visibility.PRIVATE,
    var state: State = State.DESIGNING,
    var designer: User? = null,
    var missionTags : MutableList<MissionTag> = ArrayList(),
    var feedbacks: MutableList<Feedback> = ArrayList(),
): Parcelable
{
    enum class  Visibility {
        @PropertyName("private")
        PRIVATE,

        @PropertyName("public")
        PUBLIC,
    }

    enum class  State {
        @PropertyName("designing")
        DESIGNING,

        @PropertyName("finished")
        FINISHED,
    }
}
