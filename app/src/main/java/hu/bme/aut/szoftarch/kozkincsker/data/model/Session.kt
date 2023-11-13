package hu.bme.aut.szoftarch.kozkincsker.data.model

import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import kotlinx.parcelize.Parcelize
import java.util.Date
/**
 * Egy küldetés elindított verziója, játszása
 * @param id
 * @param name Session neve
 * @param mission Küldetés, amit lehet a Session-ön belül játszani
 * @param accessCode játékhoz csatlakozási kód
 * @param startDate Session létrehozásának/indításának dátuma
 * @param moderator játék moderátr felhsználója (lehetséges hogy nincsen)
 * @param players játékoslista
 */
@Parcelize
data class Session(
    @DocumentId
    var id: String = "",
    var name: String = "",
    var missionId: String = "",       //MissionId
    var accessCode: String = "",
    var startDate: Date = Date(),
    var moderator: String? = null,  //UserId (moderator)
    var playerIds: MutableList<String> = ArrayList() //UserId list (players)
): Parcelable
