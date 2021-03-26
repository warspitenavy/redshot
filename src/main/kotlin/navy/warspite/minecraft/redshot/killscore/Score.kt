package navy.warspite.minecraft.redshot.killscore

import kotlinx.serialization.Serializable

@Serializable
data class Score(
    val victim: String,
    val victimName: String?,
    val attacker: String,
    val attackerName: String?,
    val weapon: String,
    val datetime: Long
)