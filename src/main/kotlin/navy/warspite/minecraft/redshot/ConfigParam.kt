package navy.warspite.minecraft.redshot

import kotlinx.serialization.Serializable

object ConfigParam {
    @Serializable
    data class Config(
        val killScore: KillScore = KillScore(),
    )

    @Serializable
    data class KillScore(
        val enable: Boolean = false,
        val mySql: MySql = MySql()
    )

    @Serializable
    data class MySql(
        val host: String = "127.0.0.1",
        val port: String = "3306",
        val database: String = "redshot",
        val user: String = "root",
        val password: String = "password"
    )
}