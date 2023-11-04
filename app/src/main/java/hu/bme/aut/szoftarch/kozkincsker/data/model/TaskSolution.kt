package hu.bme.aut.szoftarch.kozkincsker.data.model

import java.util.UUID

data class TaskSolution(
    var id: String = UUID.randomUUID().toString(),
    var session: Session,
    var task: Task = Task(),
    var user: User = User(),
    var userAnswer: String = ""
)
