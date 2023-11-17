package hu.bme.aut.szoftarch.kozkincsker.domain

import hu.bme.aut.szoftarch.kozkincsker.data.datasource.FirebaseDataSource
import hu.bme.aut.szoftarch.kozkincsker.data.model.Session
import javax.inject.Inject

class SessionInteractor @Inject constructor(
    private val firebaseDataSource: FirebaseDataSource
) {
    suspend fun startSession(session: Session, asModerator: Boolean) {
        firebaseDataSource.onStartSession(session, asModerator)
    }
}