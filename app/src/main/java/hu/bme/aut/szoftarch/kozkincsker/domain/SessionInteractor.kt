package hu.bme.aut.szoftarch.kozkincsker.domain

import hu.bme.aut.szoftarch.kozkincsker.data.datasource.FirebaseDataSource
import hu.bme.aut.szoftarch.kozkincsker.data.model.Mission
import hu.bme.aut.szoftarch.kozkincsker.data.model.Session
import hu.bme.aut.szoftarch.kozkincsker.data.model.TaskSolution
import hu.bme.aut.szoftarch.kozkincsker.data.model.User
import javax.inject.Inject

class SessionInteractor @Inject constructor(
    private val firebaseDataSource: FirebaseDataSource
) {
    suspend fun startSession(session: Session, asModerator: Boolean) {
        firebaseDataSource.onStartSession(session, asModerator)
    }

    suspend fun getMissionFromSession(session: Session): Mission {
        print("reka" + session.missionId.toString())
        return firebaseDataSource.getMissionById(session.missionId)
    }

    suspend fun getDesignerFromMission(mission: Mission): User {
        return firebaseDataSource.getUserFromId(mission.designerId!!)
    }

    suspend fun getPlayersFromSession(session: Session): List<User> {
        return firebaseDataSource.getUsersFromIds(session.playerIds)
    }
}