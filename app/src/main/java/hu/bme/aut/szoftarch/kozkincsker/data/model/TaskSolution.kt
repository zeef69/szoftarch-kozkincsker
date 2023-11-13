package hu.bme.aut.szoftarch.kozkincsker.data.model

import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import kotlinx.parcelize.Parcelize
import java.util.UUID
@Parcelize
data class TaskSolution(
    @DocumentId
    var id: String = UUID.randomUUID().toString(),
    var session: Session = Session(),
    var task: Task = Task(),
    var user: User = User(),
    var userAnswer: String = ""
): Parcelable
