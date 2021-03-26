package navy.warspite.minecraft.redshot.killscore

import com.github.kittinunf.fuel.Fuel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import navy.warspite.minecraft.redshot.ConfigFile

object PostScore {
    private val config = ConfigFile.config.killScore.server
    fun post(victim: String, victimName: String?, attacker: String, attackerName: String?, weapon: String, datetime: Long) = GlobalScope.launch {
        withContext(Dispatchers.Default) {
//            val (_, _, result) =
            Fuel.post("http://${config.host}:${config.port}${config.post}")
                .header(hashMapOf("Content-Type" to "application/json"))
                .body(Json.encodeToString(Score(victim, victimName, attacker, attackerName, weapon, datetime)))
                .responseString()
        }
    }
}
