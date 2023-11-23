package hu.bme.aut.szoftarch.kozkincsker.domain

import com.google.firebase.firestore.toObject
import hu.bme.aut.szoftarch.kozkincsker.data.datasource.FirebaseDataSource
import hu.bme.aut.szoftarch.kozkincsker.data.model.Mission
import hu.bme.aut.szoftarch.kozkincsker.data.model.Session
import hu.bme.aut.szoftarch.kozkincsker.data.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SessionInteractor @Inject constructor(
    private val firebaseDataSource: FirebaseDataSource
) {
    suspend fun startSession(session: Session, asModerator: Boolean) {
        firebaseDataSource.onStartSession(session, asModerator)
    }

    suspend fun getMissionFromSession(session: Session): Mission {
        return firebaseDataSource.getMissionById(session.missionId)
    }

    suspend fun getDesignerFromMission(mission: Mission): User {
        return firebaseDataSource.getUserFromId(mission.designerId!!)
    }

    suspend fun getPlayersFromSessionListener(session: Session): Flow<List<User>> {
        return firebaseDataSource.getUsersFromSessionListener(session.id)
    }

    suspend fun getSessionsFromUser(id: String): List<Session>{
        return firebaseDataSource.getSessionsFromUserId(id)
    }

    suspend fun joinWithCode(code: String): Session? {
        val sessions = mutableListOf<Session>()
        val documents = firebaseDataSource.joinWithCode(code)
        for(document in documents)
            sessions.add(document.toObject())

        if(sessions.isNotEmpty())
            firebaseDataSource.addPlayerToSession(sessions[0])

        return if(sessions.isNotEmpty()) sessions[0]
        else null
    }
}