package navy.warspite.minecraft.redshot.event

import org.bukkit.Bukkit
import org.bukkit.entity.Player

object InitialiseMap {
    fun initialise(player: Player) {
        if (CatchEvent.shootingPlayer[player] == null) CatchEvent.shootingPlayer[player] = false
        if (CatchEvent.scopingPlayer[player] == null) CatchEvent.scopingPlayer[player] = false
        if (CatchEvent.reloadingPlayer[player] == null) CatchEvent.reloadingPlayer[player] = false
    }

    fun initialise() {
        for (player in Bukkit.getOnlinePlayers()) {
            initialise(player)
        }
    }
}