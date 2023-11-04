package hu.bme.aut.szoftarch.kozkincsker.data.model

import android.os.Parcelable
import java.util.UUID
import kotlinx.parcelize.Parcelize
@Parcelize
data class User(
    var id: String = UUID.randomUUID().toString(),
    var name: String = "",
    var score: Int = 0,
    var actualLanguage: String = "English",
    var options: UserOptions = UserOptions(),
    var isAdmin: Boolean = false,
    var ableToEvaluate: Boolean = true,
    var privatePlayableMissions: MutableList<Mission> = ArrayList()
): Parcelable
