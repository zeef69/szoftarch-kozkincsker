package hu.bme.aut.szoftarch.kozkincsker.data.model

import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Mission(
    @DocumentId
    var id: String = "",
    var name: String = "",
    var creationDate: Date = Date(),
    var modificationDate: Date = Date(),
    var hoursToSolve: Int = 0,
    var daysToSolve: Int = 0,
    var accessCode: String = "",
    var isPlayableWithoutModerator: Boolean = true,
    var visibility: Visibility = Visibility.PRIVATE,
    var state: State = State.DESIGNING,
    var designer: User? = null,
    var levelList : MutableList<Level> = ArrayList(),
    var missionTags : MutableList<MissionTag> = ArrayList(),
    var feedbacks: MutableList<Feedback> = ArrayList(),
    var badge: Badge? = null,
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
