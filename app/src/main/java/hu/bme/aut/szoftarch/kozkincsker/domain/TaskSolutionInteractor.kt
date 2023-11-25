package hu.bme.aut.szoftarch.kozkincsker.domain

import hu.bme.aut.szoftarch.kozkincsker.data.datasource.FirebaseDataSource
import hu.bme.aut.szoftarch.kozkincsker.data.model.TaskSolution
import javax.inject.Inject

class TaskSolutionInteractor @Inject constructor(
    private val firebaseDataSource: FirebaseDataSource
) {
    suspend fun setTaskSolution(solution: TaskSolution): String? {
        val data = firebaseDataSource.setTaskSolution(solution)
        return data?.id
    }
}