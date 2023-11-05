package hu.bme.aut.szoftarch.kozkincsker.data.model

import android.os.Parcelable
import hu.bme.aut.szoftarch.kozkincsker.data.enums.LevelType
import java.util.UUID
import kotlinx.parcelize.Parcelize

@Parcelize
data class Level(
    var id: String = UUID.randomUUID().toString(),
    var levelNumber: Int = 0,
    var previousLevel: Level? = null,
    var nextLevel: Level? = null,
    var levelType: LevelType = LevelType.MinOneTaskInLevel,
    var showNextLevel: Boolean = false,
    var taskList: MutableList<Task> = ArrayList()
): Parcelable
