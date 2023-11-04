package hu.bme.aut.szoftarch.kozkincsker.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

/**
 * Jelvény osztály
 * @param id
 * @param name jelvény neve
 * @param description jelvény leírása, miért jár
 * @param image resource-ok közül a megfelelő image azonosítója
 */
@Parcelize
data class Badge(
    var id: String = UUID.randomUUID().toString(),
    var name: String = "",
    var description: String = "",
    var image: Int = -1
): Parcelable

