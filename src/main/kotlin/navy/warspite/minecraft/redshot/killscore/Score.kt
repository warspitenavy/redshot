package navy.warspite.minecraft.redshot.killscore

import kotlinx.serialization.Serializable

@Serializable
data class Score(
    val victim: String,
    val attacker: String,
    val weapon: String,
    val datetime: Long
)