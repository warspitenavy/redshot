package navy.warspite.minecraft.redshot

import kotlinx.serialization.Serializable

object ConfigParam {
    @Serializable
    data class Config(
        val killDeathRatio: KillDeathRatio = KillDeathRatio(),
    )

    @Serializable
    data class KillDeathRatio(
        val enable: Boolean = false,
        val databaseType: String = "mysql",
        val mySql: MySql = MySql()
    )

    @Serializable
    data class MySql(
        val tablePrefix: String = "kdr_",
        val host: String = "127.0.0.1",
        val port: String = "3306",
        val database: String = "database",
        val username: String = "root",
        val password: String = "password"
    )
}