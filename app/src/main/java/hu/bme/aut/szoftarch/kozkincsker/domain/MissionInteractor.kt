package hu.bme.aut.szoftarch.kozkincsker.domain

import hu.bme.aut.szoftarch.kozkincsker.data.datasource.FirebaseDataSource
import hu.bme.aut.szoftarch.kozkincsker.data.model.Mission
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MissionInteractor @Inject constructor(
    private val firebaseDataSource: FirebaseDataSource
) {
    suspend fun getMissionsListener(): Flow<List<Mission>> {
        return firebaseDataSource.getMissionsListener()
    }

    suspend fun getMissionsOnce(): List<Mission> {
        return firebaseDataSource.getMissionsOnce()
    }

    suspend fun uploadMission(newMission: Mission) {
        firebaseDataSource.onUploadMission(newMission)
    }

    suspend fun editMission(mission: Mission) {
        firebaseDataSource.onEditMission(mission)
    }

    suspend fun deleteMission(mission: Mission) {
        firebaseDataSource.onDeleteMission(mission)
    }
}