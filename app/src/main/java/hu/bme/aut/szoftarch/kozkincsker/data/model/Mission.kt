package hu.bme.aut.szoftarch.kozkincsker.data.model

import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName
import kotlinx.parcelize.Parcelize
import java.util.Date
/**
 * Küldetés osztály
 * @param id
 * @param name Küoldetés neve
 * @param descripion Küldetés leírása
 * @param creationDate Létrehozás dátuma
 * @param modificationDate Utolsó módosítás dátuma
 * @param hoursToSolve megoldási idő - óra
 * @param daysToSolve megoldási idő - nap
 * @param accessCode játék hozzáférési kód (csak privátnál számít)
 * @param isPlayableWithoutModerator indítható-e Session a Küldtésből külön Moderátro nélkül
 * @param visibility játék láthatósága
 * @param state játék tervezési-kész állapota
 * @param designer tervező
 * @param levelList küldetészint lista
 * @param missionTags kulcsszavak, jellegzetes leírás lista a küldetésről
 * @param feedbacks játékosok visszajelzései
 * @param badge játékért kapható jelvény
 */
@Parcelize
data class Mission(
    @DocumentId
    var id: String = "",
    var name: String = "",
    var descripion: String = "",
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
