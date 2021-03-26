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
        val server: Server = Server()
    )

    @Serializable
    data class Server(
        val host: String = "127.0.0.1",
        val port: String = "3080",
        val post: String = "/post"
    )
}