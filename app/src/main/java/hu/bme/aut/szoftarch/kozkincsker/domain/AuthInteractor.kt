package hu.bme.aut.szoftarch.kozkincsker.domain

import android.content.Context
import android.widget.Toast
import co.zsmb.rainbowcake.navigation.Navigator
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import hu.bme.aut.szoftarch.kozkincsker.R
import hu.bme.aut.szoftarch.kozkincsker.data.datasource.FirebaseDataSource
import hu.bme.aut.szoftarch.kozkincsker.ui.main.MainFragment
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthInteractor @Inject constructor(
    private val firebaseDataSource: FirebaseDataSource
) {
    private var firebaseAuth = FirebaseAuth.getInstance()

    suspend fun registerClick(context: Context, mail: String, pass: String, name: String) {
        firebaseAuth
            .createUserWithEmailAndPassword(mail, pass)
            .addOnSuccessListener { result ->
                val firebaseUser = result.user
                val profileChangeRequest = UserProfileChangeRequest.Builder()
                    .setDisplayName(firebaseUser?.email?.substringBefore('@'))
                    .build()
                firebaseUser?.updateProfile(profileChangeRequest)
                firebaseUser?.sendEmailVerification()

                firebaseDataSource.addUser(firebaseUser?.uid, name)
                Toast.makeText(context, R.string.registration_success, Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(context, exception.message, Toast.LENGTH_SHORT).show()
            }
            .await()
    }

    fun loginClick(navigator: Navigator?, context: Context, mail: String, pass: String) {
        firebaseAuth
            .signInWithEmailAndPassword(mail, pass)
            .addOnSuccessListener {
                if(firebaseAuth.currentUser!!.isEmailVerified) {
                    navigator?.add(
                        MainFragment(),
                        enterAnim = 0,
                        exitAnim = 0,
                        popEnterAnim = 0,
                        popExitAnim = 0
                    )
                }
                else
                    Toast.makeText(context, R.string.verify_your_email, Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(context, exception.localizedMessage, Toast.LENGTH_SHORT).show()
            }
    }

    fun signOut() {
        firebaseAuth.signOut()
    }

    fun getCurrentUserEmail(): String? {
        return firebaseAuth.currentUser?.email
    }

    fun getCurrentUser(): String? {
        return firebaseAuth.currentUser?.uid
    }

    fun sendPasswordReset() {
        val mail = getCurrentUserEmail() as String
        firebaseAuth.sendPasswordResetEmail(mail)
    }

    fun changeEmail(context: Context, password: String?, newEmail: String?) {
        if(password.isNullOrEmpty()){
            Toast.makeText(context, R.string.valid_password, Toast.LENGTH_SHORT).show()
        }
        else if(newEmail.isNullOrEmpty() || !newEmail.contains('@') || newEmail == getCurrentUserEmail() as String){
            Toast.makeText(context, R.string.valid_email, Toast.LENGTH_SHORT).show()
        }
        else {
            val mail = getCurrentUserEmail() as String
            val credential = EmailAuthProvider.getCredential (
                mail,
                password
            )

            firebaseAuth.currentUser?.reauthenticate(credential)
                ?.addOnSuccessListener {
                    firebaseAuth.currentUser?.verifyBeforeUpdateEmail(newEmail)

                    Toast.makeText(context, R.string.verification_email, Toast.LENGTH_SHORT).show()
                }
                ?.addOnFailureListener { exception ->
                    Toast.makeText(context, exception.localizedMessage, Toast.LENGTH_SHORT).show()
                }
        }
    }
}