package hu.bme.aut.szoftarch.kozkincsker.domain

import com.google.firebase.firestore.toObject
import hu.bme.aut.szoftarch.kozkincsker.data.datasource.FirebaseDataSource
import hu.bme.aut.szoftarch.kozkincsker.data.model.Feedback
import hu.bme.aut.szoftarch.kozkincsker.data.model.Mission
import hu.bme.aut.szoftarch.kozkincsker.data.model.Session
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

    suspend fun uploadMission(newMission: Mission) : String? {
        val data = firebaseDataSource.onUploadMission(newMission)
        if(data != null) {
            newMission.id = data.id
            firebaseDataSource.addMissionToUser(newMission)
        }
        return data?.id
    }

    suspend fun editMission(mission: Mission) {
        firebaseDataSource.onEditMission(mission)
    }

    suspend fun deleteMission(mission: Mission) {
        firebaseDataSource.onDeleteMission(mission)
    }

    suspend fun newFeedback(feedback: Feedback, missionId: String) {
        firebaseDataSource.onAddFeedbackToMission(feedback, missionId)
    }

    suspend fun deleteFeedback(feedback: Feedback, missionId: String) {
        firebaseDataSource.onDeleteFeedbackFromMission(feedback, missionId)
    }

    suspend fun joinPrivateGame(code: String){
        val missions = mutableListOf<Mission>()
        val documents = firebaseDataSource.getPrivateMissionByCode(code)
        for(document in documents)
            missions.add(document.toObject())
        if(missions.isNotEmpty()) {
            firebaseDataSource.addPrivateMissionToUser(missions[0])
        }
    }
}