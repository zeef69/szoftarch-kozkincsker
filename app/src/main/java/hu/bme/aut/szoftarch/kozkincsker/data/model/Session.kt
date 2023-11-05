package hu.bme.aut.szoftarch.kozkincsker.data.model

import android.os.Parcelable
import java.util.UUID
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Session(
    var id: String = UUID.randomUUID().toString(),
    var name: String = "",
    var mission: Mission,
    var accessCode: String = "",
    var startDate: Date = Date(),
    var moderator: User? = null,
    var players: MutableList<User> = ArrayList()
): Parcelable
