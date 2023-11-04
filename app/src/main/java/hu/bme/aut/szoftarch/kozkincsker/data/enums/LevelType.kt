package hu.bme.aut.szoftarch.kozkincsker.data.enums

import com.google.firebase.firestore.PropertyName
import hu.bme.aut.szoftarch.kozkincsker.R

enum class LevelType(val translation: Int) {
    @PropertyName("listed_answer")
    MinOneTaskInLevel(R.string.level_type_min_one_task_per_level),
    @PropertyName("listed_answer")
    MaxOneTaskInLevel(R.string.level_type_max_one_task_per_level),
    @PropertyName("listed_answer")
    AllTaskInLevel(R.string.level_type_all_task_per_level)
}