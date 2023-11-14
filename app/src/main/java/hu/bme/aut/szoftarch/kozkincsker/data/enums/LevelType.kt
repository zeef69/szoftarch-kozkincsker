package hu.bme.aut.szoftarch.kozkincsker.data.enums

import com.google.firebase.firestore.PropertyName
import hu.bme.aut.szoftarch.kozkincsker.R

enum class LevelType(val translation: Int) {
    @PropertyName("min_one_task")
    MinOneTaskInLevel(R.string.level_type_min_one_task_per_level),
    @PropertyName("max_one_task")
    MaxOneTaskInLevel(R.string.level_type_max_one_task_per_level),
    @PropertyName("all_task")
    AllTaskInLevel(R.string.level_type_all_task_per_level)
}