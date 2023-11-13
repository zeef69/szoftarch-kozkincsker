package hu.bme.aut.szoftarch.kozkincsker.data.model

import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class MissionTag (
    @DocumentId
    var id: String = UUID.randomUUID().toString(),
    var name: String = ""
) : Parcelable
