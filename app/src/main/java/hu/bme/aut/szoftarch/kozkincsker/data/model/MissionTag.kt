package hu.bme.aut.szoftarch.kozkincsker.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class MissionTag (
    var id: String = UUID.randomUUID().toString(),
    var name: String = ""
) : Parcelable
