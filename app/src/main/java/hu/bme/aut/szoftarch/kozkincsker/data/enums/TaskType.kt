package hu.bme.aut.szoftarch.kozkincsker.data.enums

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.PropertyName
import hu.bme.aut.szoftarch.kozkincsker.R
//TODO solutionCheck megírása minden feladattípushoz
enum class TaskType(val translation: Int, val checkable: Boolean) {
    @PropertyName("listed_answer")
    ListedAnswer(R.string.task_type_listed_answer, true){
        override fun solutionCheck(designerAnswers: String, userAnswer: String) : Boolean{
            val splitter =  '|'
            var check = true
            if(designerAnswers.split(splitter).size < 13) return false
            var answerSize = designerAnswers.split(splitter)[0].toInt()
            for (i in 0..<answerSize){
                check = check && (designerAnswers.split(splitter)[2*i+1].toBoolean() ==
                        userAnswer.split(splitter)[2*i+1].toBoolean())
            }
            return check
        }
    },
    @PropertyName("number_answer")
    NumberAnswer(R.string.task_type_number_answer, true){
        override fun solutionCheck(designerAnswers: String, userAnswer: String) : Boolean{
            return designerAnswers == userAnswer
        }
    },
    @PropertyName("date_answer")
    DateAnswer(R.string.task_type_date_answer, true){
        override fun solutionCheck(designerAnswers: String, userAnswer: String) : Boolean{
            return designerAnswers == userAnswer
        }
    },
    @PropertyName("map_answer")
    MapAnswer(R.string.task_type_map_answer, true){
        override fun solutionCheck(designerAnswers: String, userAnswer: String) : Boolean {
            val splitter =  '|'
            if(designerAnswers.split(splitter).size < 4) return false
            var actualRadius = designerAnswers.split(splitter)[0].toDouble()
            var goal = LatLng(designerAnswers.split(splitter)[2].toDouble(), designerAnswers.split(splitter)[3].toDouble())
            var device = LatLng(userAnswer.split(splitter)[0].toDouble(), userAnswer.split(splitter)[1].toDouble())
            var results = FloatArray(1)
            Location.distanceBetween(
                goal.latitude,goal.longitude,
                device.latitude, device.longitude,
                results)
            if(results[0]<=actualRadius) return true
            return false
        }
    },
    @PropertyName("order_answer")
    OrderAnswer(R.string.task_type_order_answer, true){
        override fun solutionCheck(designerAnswers: String, userAnswer: String): Boolean {
            val splitter =  '|'
            var check = true
            if(designerAnswers.split(splitter).size < 7) return false
            var answerSize = designerAnswers.split(splitter)[0].toInt()
            for (i in 0..<answerSize){
                check = check && (designerAnswers.split(splitter)[i+1]
                    .compareTo(userAnswer.split(splitter)[i+1]) == 0)
            }
            return check
        }
    },
    @PropertyName("text_answer")
    TextAnswer(R.string.task_type_text_answer, false){
        override fun solutionCheck(designerAnswers: String, userAnswer: String) = false
    },
    @PropertyName("image_answer")
    ImageAnswer(R.string.task_type_image_answer, false){
        override fun solutionCheck(designerAnswers: String, userAnswer: String) = false
    };

    /**
     * Egy feladatípus ellenőrzési módja alapján megadja, hogy helyes-e a megoldás.
     * A válaszlehetőségek és a felhasználó válasza alapján dönt.
     * @param designerAnswers tervező által megadott válaszlehetősége és helyes válaszból generált string
     * @param userAnswer játékos válasza a fealdatra stringként
     */
    abstract fun solutionCheck(designerAnswers: String, userAnswer: String): Boolean
}