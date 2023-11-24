package hu.bme.aut.szoftarch.kozkincsker.data.enums

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
            for (i in 0..<6){
                if(i<answerSize){
                    check = check && (designerAnswers.split(splitter)[2*i+1].toBoolean() ==
                        userAnswer.split(splitter)[2*i+1].toBoolean())
                }
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
            return true
        }
    },
    @PropertyName("order_answer")
    OrderAnswer(R.string.task_type_order_answer, true){
        override fun solutionCheck(designerAnswers: String, userAnswer: String) = true
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