package hu.bme.aut.szoftarch.kozkincsker.data.datasource

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import hu.bme.aut.szoftarch.kozkincsker.data.model.Feedback
import hu.bme.aut.szoftarch.kozkincsker.data.model.Mission
import hu.bme.aut.szoftarch.kozkincsker.data.model.Session
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
    private val uid = FirebaseAuth.getInstance().currentUser?.uid

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
        if (uid != null) {
            feedback.writerId = uid
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
        database.collection("sessions").whereEqualTo("mission", mission).get()
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

    suspend fun addSession(newSession: Session){
        database.collection("sessions").add(newSession)
            .addOnSuccessListener { documentReference ->
                Log.d("success", "DocumentSnapshot written with ID: $documentReference.")
            }
            .addOnFailureListener { exception ->
                Log.d("failure", "Error getting documents: ", exception)
            }.await()
    }

    suspend fun onStartSession(session: Session, asModerator: Boolean) {
        if(asModerator && uid != null) {
            session.moderator = uid
        }
        val allowedChars = ('A'..'Z') + ('0'..'9')
        session.accessCode = (1..6)
            .map { allowedChars.random() }
            .joinToString("")

        database.collection("sessions").add(session)
            .addOnSuccessListener { documentReference ->
                Log.d("success", "DocumentSnapshot written with ID: $documentReference.")
            }
            .addOnFailureListener { exception ->
                Log.d("failure", "Error getting documents: ", exception)
            }.await()
    }

    suspend fun getMissionById(missionId: String): Mission {
        var mission = Mission()
        database.collection("mission").document(missionId).get()
            .addOnSuccessListener { document ->
                mission = document.toObject()!!
            }
            .addOnFailureListener { exception ->
                Log.d("failure", "Error getting documents: ", exception)
            }
            .await()

        return mission
    }
}