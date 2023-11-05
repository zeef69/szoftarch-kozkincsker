package hu.bme.aut.szoftarch.kozkincsker.data.model

import android.os.Parcelable
import com.google.firebase.firestore.PropertyName
import kotlinx.parcelize.Parcelize
import java.util.UUID

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
