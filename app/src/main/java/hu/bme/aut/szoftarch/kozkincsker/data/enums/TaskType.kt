package hu.bme.aut.szoftarch.kozkincsker.data.enums

import com.google.firebase.firestore.PropertyName
import hu.bme.aut.szoftarch.kozkincsker.R
//TODO solutionCheck megírása minden feladattípushoz
enum class TaskType(val translation: Int, val checkable: Boolean) {
    @PropertyName("listed_answer")
    ListedAnswer(R.string.task_type_listed_answer, true){
        override fun solutionCheck(designerAnswers: String, userAnswer: String) = true
    },
    @PropertyName("number_answer")
    NumberAnswer(R.string.task_type_number_answer, true){
        override fun solutionCheck(designerAnswers: String, userAnswer: String) = true
    },
    @PropertyName("date_answer")
    DateAnswer(R.string.task_type_date_answer, true){
        override fun solutionCheck(designerAnswers: String, userAnswer: String) = true
    },
    @PropertyName("map_answer")
    MapAnswer(R.string.task_type_map_answer, true){
        override fun solutionCheck(designerAnswers: String, userAnswer: String) = true
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