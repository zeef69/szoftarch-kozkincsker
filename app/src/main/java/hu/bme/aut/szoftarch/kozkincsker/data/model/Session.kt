package hu.bme.aut.szoftarch.kozkincsker.data.model

import android.os.Parcelable
import java.util.UUID
import kotlinx.parcelize.Parcelize
@Parcelize
data class Session(
    var id: String = UUID.randomUUID().toString(),
    var name: String = ""

): Parcelable
