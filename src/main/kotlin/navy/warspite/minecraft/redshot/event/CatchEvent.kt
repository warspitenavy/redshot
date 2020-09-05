package navy.warspite.minecraft.redshot.event

import navy.warspite.minecraft.redshot.Main
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

object CatchEvent : Listener{
    private val plugin = Main.instance
    val shootingPlayer = linkedMapOf<Player, Boolean>()
    val zoomingPlayer = linkedMapOf<Player, Boolean>()
    val reloadingPlayer = linkedMapOf<Player, Boolean>()
    val shootCount = linkedMapOf<Player, Int>()

    @EventHandler
    private fun clickEvent(e: PlayerInteractEvent) {
        if (shootingPlayer[e.player] == null) shootingPlayer[e.player] = false
        if (zoomingPlayer[e.player] == null) zoomingPlayer[e.player] = false
        if (reloadingPlayer[e.player] == null) reloadingPlayer[e.player] = false
        if (shootCount[e.player] == null) shootCount[e.player] = 0

        if (e.action == Action.RIGHT_CLICK_AIR || e.action == Action.RIGHT_CLICK_BLOCK) {
            val itemMeta = e.player.inventory.itemInMainHand.itemMeta ?: return
            if (GetMeta.isWeapon(itemMeta)) ShootProjectile.shoot(e.player)
            else return
        }
        if (e.action == Action.LEFT_CLICK_AIR || e.action == Action.LEFT_CLICK_BLOCK) {
            //TODO
        }
    }
}