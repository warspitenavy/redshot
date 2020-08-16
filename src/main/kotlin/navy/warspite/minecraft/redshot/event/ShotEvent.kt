package navy.warspite.minecraft.redshot.event

import org.bukkit.Material
import org.bukkit.entity.Snowball
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

object ShotEvent : Listener {
    @EventHandler
    fun shot(e: PlayerInteractEvent) {
        if (e.action == Action.RIGHT_CLICK_AIR || e.action == Action.RIGHT_CLICK_BLOCK) {
            if (e.player.inventory.itemInMainHand.type == Material.DIAMOND_PICKAXE) {
                e.player.sendMessage("shoot!")
                val snowball: Snowball = e.player.launchProjectile(Snowball::class.java)
            }
        }
    }
}