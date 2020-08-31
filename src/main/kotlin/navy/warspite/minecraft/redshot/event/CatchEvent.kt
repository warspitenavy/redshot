package navy.warspite.minecraft.redshot.event

import navy.warspite.minecraft.redshot.Main
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

object CatchEvent : Listener{
    private val plugin = Main.instance

    @EventHandler
    private fun clickEvent(e: PlayerInteractEvent) {
        if (e.action == Action.RIGHT_CLICK_AIR || e.action == Action.RIGHT_CLICK_BLOCK) {
            //TODO
        }
        if (e.action == Action.LEFT_CLICK_AIR || e.action == Action.LEFT_CLICK_BLOCK) {
            //TODO
        }
    }
}