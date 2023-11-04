package hu.bme.aut.szoftarch.kozkincsker.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class Feedback(
    var id: String = UUID.randomUUID().toString(),
    var session: Session,
    var writer: User = User(),
    var rate: Double = 0.0,
    var comment: String = "",
    var commentState: CommentVisibility = CommentVisibility.SHOW
): Parcelable{
    enum class CommentVisibility{ //TODO Csak a Designer és publikusaknál az Admin tud törölni kommentet
        SHOW, HIDE
    }
}
