package hu.bme.aut.szoftarch.kozkincsker.data.model

import android.os.Parcelable
import com.google.firebase.firestore.PropertyName
import kotlinx.parcelize.Parcelize
import java.util.UUID
/**
 * Küldetés visszajelzés osztály
 * @param id
 * @param mission kapcsolódó küldetés
 * @param writer értékelést író felhasználó
 * @param stars 0-5 skálán csillaggal való küldetés értékelés
 * @param comment szöveges küldetés értékelés
 * @param commentState szöveges értékelés láthatósága
 */
@Parcelize
data class Feedback(
    var id: String = UUID.randomUUID().toString(),
    var mission: Mission = Mission(),
    var writer: User = User(),
    var stars: Double = 0.0,
    var comment: String = "",
    var commentState: CommentVisibility = CommentVisibility.SHOW
): Parcelable{
    enum class CommentVisibility{
        @PropertyName("show")
        SHOW,
        @PropertyName("hide")
        HIDE
    }
}
