package hu.bme.aut.szoftarch.kozkincsker.data.model

import android.os.Parcelable
import hu.bme.aut.szoftarch.kozkincsker.data.enums.TaskType
import kotlinx.parcelize.Parcelize
import java.util.UUID
@Parcelize
data class Task (
    var id: String = UUID.randomUUID().toString(),
    var mission: Mission = Mission(),
    var title: String = "",
    var descripion: String = "",
    var score: Int = 0,
    var answers: String = "",
    var taskType: TaskType = TaskType.ListedAnswer,
    var previousTask: Task? = null,
    var nextTask: Task? = null

): Parcelable