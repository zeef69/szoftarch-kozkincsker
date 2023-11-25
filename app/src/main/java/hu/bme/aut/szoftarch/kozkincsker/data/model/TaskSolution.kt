package hu.bme.aut.szoftarch.kozkincsker.data.model

import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import kotlinx.parcelize.Parcelize
@Parcelize
data class TaskSolution(
    @DocumentId
    var id: String = "",
    var sessionId: String = "",           //SessionId
    var taskId: String = "",              //TaskId
    var userId: String = "",              //UserId (player)
    var userAnswer: String = "",
    var checked: Boolean = false,
    var correct: Boolean = false
): Parcelable
