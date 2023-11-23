package hu.bme.aut.szoftarch.kozkincsker.data.datasource

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import hu.bme.aut.szoftarch.kozkincsker.data.model.Feedback
import hu.bme.aut.szoftarch.kozkincsker.data.model.Mission
import hu.bme.aut.szoftarch.kozkincsker.data.model.Session
import hu.bme.aut.szoftarch.kozkincsker.data.model.TaskSolution
import hu.bme.aut.szoftarch.kozkincsker.data.model.User
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseDataSource @Inject constructor() {

    private val database = Firebase.firestore
    //private val uid = FirebaseAuth.getInstance().currentUser?.uid

    suspend fun getMissionsListener(): Flow<List<Mission>> = callbackFlow {
        val listenerRegistration = database.collection("missions")
            .addSnapshotListener { querySnapshot: QuerySnapshot?, firebaseFirestoreException: FirebaseFirestoreException? ->
                if (firebaseFirestoreException != null) {
                    cancel(message = "Error fetching items", cause = firebaseFirestoreException)
                    return@addSnapshotListener
                }
                val items = mutableListOf<Mission>()
                if (querySnapshot != null) {
                    for(document in querySnapshot) {
                        items.add(document.toObject())
                    }
                }
                this.trySend(items).isSuccess
            }
        awaitClose {
            Log.d("failure", "Cancelling items listener")
            listenerRegistration.remove()
        }
    }

    suspend fun getMissionsOnce(): List<Mission> {
        val missions = mutableListOf<Mission>()
        database.collection("missions").get()
            .addOnSuccessListener { documents ->
                for(document in documents)
                    missions.add(document.toObject())
            }
            .addOnFailureListener { exception ->
                Log.d("failure", "Error getting documents: ", exception)
            }
            .await()

        return missions
    }

    suspend fun onUploadMission(newMission: Mission) {
        database.collection("missions").add(newMission)
            .addOnSuccessListener { documentReference ->
                Log.d("success", "DocumentSnapshot written with ID: $documentReference.")
            }
            .addOnFailureListener { exception ->
                Log.d("failure", "Error getting documents: ", exception)
            }.await()
    }

    suspend fun onEditMission(mission: Mission) {
        database.collection("missions").document(mission.id).set(mission, SetOptions.merge())
            .addOnSuccessListener { documentReference ->
                Log.d("success", "DocumentSnapshot written with ID: $documentReference.")
            }
            .addOnFailureListener { exception ->
                Log.d("failure", "Error getting documents: ", exception)
            }.await()
    }

    suspend fun onDeleteMission(mission: Mission) {
        database.collection("trips").document(mission.id).delete()
            .addOnSuccessListener { documentReference ->
                Log.d("success", "DocumentSnapshot written with ID: $documentReference.")
            }
            .addOnFailureListener { exception ->
                Log.d("failure", "Error getting documents: ", exception)
            }.await()
    }

    suspend fun onAddFeedbackToMission(feedback: Feedback, missionId: String) {
        val user = getUser()
        if (user != null) {
            feedback.writerId = user.id
        }
        val addFeedback : HashMap<String, Any> = HashMap()
        addFeedback["feedbackIds"] = FieldValue.arrayUnion(feedback)

        database.collection("missions").document(missionId).update(addFeedback)
            .addOnSuccessListener { documentReference ->
                Log.d("success", "DocumentSnapshot written with ID: $documentReference.")
            }
            .addOnFailureListener { exception ->
                Log.d("failure", "Error getting documents: ", exception)
            }.await()
    }

    suspend fun getSessionsFromMission(mission: Mission): List<Session>{
        val sessions = mutableListOf<Session>()
        database.collection("sessions").whereEqualTo("mission", mission.id).get()
            .addOnSuccessListener { documents ->
                for(document in documents)
                    sessions.add(document.toObject())
            }
            .addOnFailureListener { exception ->
                Log.d("failure", "Error getting documents: ", exception)
            }
            .await()
        return sessions
    }

    suspend fun getUsersFromSessionListener(sessionId: String): Flow<List<User>> = callbackFlow {
        val listenerRegistration = database.collection("users").whereArrayContains("currentSessionIds", sessionId)
            .addSnapshotListener { querySnapshot: QuerySnapshot?, firebaseFirestoreException: FirebaseFirestoreException? ->
                if (firebaseFirestoreException != null) {
                    cancel(message = "Error fetching items", cause = firebaseFirestoreException)
                    return@addSnapshotListener
                }
                val items = mutableListOf<User>()
                if (querySnapshot != null) {
                    for(document in querySnapshot) {
                        items.add(document.toObject())
                    }
                }
                this.trySend(items).isSuccess
            }
        awaitClose {
            Log.d("failure", "Cancelling items listener")
            listenerRegistration.remove()
        }
    }


    suspend fun addSession(newSession: Session){
        database.collection("sessions").add(newSession)
            .addOnSuccessListener { documentReference ->
                Log.d("success", "DocumentSnapshot written with ID: $documentReference.")
            }
            .addOnFailureListener { exception ->
                Log.d("failure", "Error getting documents: ", exception)
            }.await()
    }

    suspend fun getMissionById(missionId: String): Mission {
        var mission: Mission? = null
        database.collection("missions").document(missionId).get()
            .addOnSuccessListener { document ->
                mission = document.toObject()
            }
            .addOnFailureListener { exception ->
                Log.d("failure", "Error getting documents: ", exception)
            }
            .await()

        return if(mission != null) mission as Mission
        else Mission()
    }

    suspend fun getUserFromId(designerId: String): User? {
        var user: User? = null
        database.collection("users").document(designerId).get()
            .addOnSuccessListener { document ->
                user = document.toObject()
            }
            .addOnFailureListener { exception ->
                Log.d("failure", "Error getting User: ", exception)
            }
            .await()
        return if(user != null) user as User
        else null
    }

    suspend fun onStartSession(session: Session, asModerator: Boolean): DocumentReference? {
        val user = getUser()
        if (user != null) {
            if(asModerator)
                session.moderator = user.id
            else
                session.playerIds.add(user.id)
        }

        return database.collection("sessions").add(session)
            .addOnSuccessListener { documentReference ->
                Log.d("success", "DocumentSnapshot written with ID: $documentReference.")
            }
            .addOnFailureListener { exception ->
                Log.d("failure", "Error getting documents: ", exception)
            }.await()
    }

    suspend fun joinWithCode(code: String): QuerySnapshot {
        return database.collection("sessions").whereEqualTo("accessCode", code).limit(1).get()
            .addOnSuccessListener { }
            .addOnFailureListener { exception ->
                Log.d("failure", "Error getting documents: ", exception)
            }
            .await()
    }

    suspend fun addPlayerToSession(session: Session?) {
        val user = getUser()
        if (user != null && session != null) {
            val addUser : HashMap<String, Any> = HashMap()
            addUser["playerIds"] = FieldValue.arrayUnion(user.id)

            database.collection("sessions").document(session.id).update(addUser)
                .addOnSuccessListener { documentReference ->
                    Log.d("success", "DocumentSnapshot written with ID: $documentReference.")
                }
                .addOnFailureListener { exception ->
                    Log.d("failure", "Error getting documents: ", exception)
                }.await()
        }
    }

    suspend fun addSessionToPlayer(session: Session?) {
        val user = getUser()
        if (user != null && session != null) {
            val addSession : HashMap<String, Any> = HashMap()
            addSession["currentSessionIds"] = FieldValue.arrayUnion(session.id)

            database.collection("users").document(user.id).update(addSession)
                .addOnSuccessListener { documentReference ->
                    Log.d("success", "DocumentSnapshot written with ID: $documentReference.")
                }
                .addOnFailureListener { exception ->
                    Log.d("failure", "Error getting documents: ", exception)
                }.await()
        }
    }

    suspend fun getCurrentUser(): QuerySnapshot {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        return database.collection("users").whereEqualTo("uid", uid).limit(1).get()
            .addOnSuccessListener { }
            .addOnFailureListener { exception ->
                Log.d("failure", "Error getting documents: ", exception)
            }
            .await()
    }

    private suspend fun getUser(): User? {
        val users = mutableListOf<User>()
        val documents = getCurrentUser()
        for(document in documents)
            users.add(document.toObject())

        return if(users.isNotEmpty()) users[0]
        else null
    }

    fun addUser(uid: String?, name: String) {
        val newUser = User(name = name)
        if(uid != null)
            newUser.uid = uid

        database.collection("users").add(newUser)
            .addOnSuccessListener { documentReference ->
                Log.d("success", "DocumentSnapshot written with ID: $documentReference.")
            }
            .addOnFailureListener { exception ->
                Log.d("failure", "Error getting documents: ", exception)
            }
    }

    fun setTaskSolution(solution: TaskSolution) {
        TODO("Not yet implemented")
    }
}