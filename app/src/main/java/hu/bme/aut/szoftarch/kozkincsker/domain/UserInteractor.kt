package hu.bme.aut.szoftarch.kozkincsker.domain

import com.google.firebase.firestore.toObject
import hu.bme.aut.szoftarch.kozkincsker.data.datasource.FirebaseDataSource
import hu.bme.aut.szoftarch.kozkincsker.data.model.User
import javax.inject.Inject

class UserInteractor @Inject constructor(
    private val firebaseDataSource: FirebaseDataSource
) {
    suspend fun getCurrentUser(): User? {
        val users = mutableListOf<User>()
        val documents = firebaseDataSource.getCurrentUser()
        for(document in documents)
            users.add(document.toObject())

        return if(users.isNotEmpty()) users[0]
        else null
    }

    suspend fun getUserFromId(id: String?): User? {
        return firebaseDataSource.getUserFromId(id)
    }
}