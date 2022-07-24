package navy.warspite.redshot

import navy.warspite.redshot.Main.Companion.reloadingState
import navy.warspite.redshot.Main.Companion.scopingState
import navy.warspite.redshot.Main.Companion.shootingState
import org.bukkit.entity.Player

object StateController {
    fun initialiseState (player: Player) {
        shootingState[player.uniqueId] = false
        scopingState[player.uniqueId] = false
        reloadingState[player.uniqueId] = false
    }

    fun initialiseAllState() {
        for (player in Main.plugin.server.onlinePlayers) {
            initialiseState(player)
        }
    }
}