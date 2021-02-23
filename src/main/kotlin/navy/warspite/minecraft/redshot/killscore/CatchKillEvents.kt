package navy.warspite.minecraft.redshot.killscore

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDeathEvent

object CatchKillEvents : Listener {

    @EventHandler
    private fun entityDeathEvent(e: EntityDeathEvent) {
        val victim = e.entity as Player
        val attacker = e.entity.killer
    }
}