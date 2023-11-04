package hu.bme.aut.szoftarch.kozkincsker.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID
@Parcelize
data class TaskSolution(
    var id: String = UUID.randomUUID().toString(),
    var session: Session,
    var task: Task = Task(),
    var user: User = User(),
    var userAnswer: String = ""
): Parcelable
