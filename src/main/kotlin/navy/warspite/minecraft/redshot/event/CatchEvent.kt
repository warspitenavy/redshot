package navy.warspite.minecraft.redshot.event

import navy.warspite.minecraft.redshot.Main
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerItemHeldEvent
import org.bukkit.event.player.PlayerSwapHandItemsEvent

object CatchEvent : Listener{
    private val plugin = Main.instance
    val shootingPlayer = linkedMapOf<Player, Boolean>()
    val scopingPlayer = linkedMapOf<Player, Boolean>()
    val reloadingPlayer = linkedMapOf<Player, Boolean>()
    val shootCount = linkedMapOf<Player, Int>()

    @EventHandler
    private fun clickEvent(e: PlayerInteractEvent) {
        if (shootingPlayer[e.player] == null) shootingPlayer[e.player] = false
        if (scopingPlayer[e.player] == null) scopingPlayer[e.player] = false
        if (reloadingPlayer[e.player] == null) reloadingPlayer[e.player] = false
        if (shootCount[e.player] == null) shootCount[e.player] = 0

        if (e.action == Action.RIGHT_CLICK_AIR || e.action == Action.RIGHT_CLICK_BLOCK) {
            val itemMeta = e.player.inventory.itemInMainHand.itemMeta ?: return
            if (GetMeta.isWeapon(itemMeta)) ShootEvent.shooting(e.player)
            else return
        }
        if (e.action == Action.LEFT_CLICK_AIR || e.action == Action.LEFT_CLICK_BLOCK) {
            val itemMeta = e.player.inventory.itemInMainHand.itemMeta ?: return
            if (GetMeta.isWeapon(itemMeta)) ScopeEvent.toggleZoom(e.player)
            else return
        }
    }

    @EventHandler
    private fun itemHeldEvent(e: PlayerItemHeldEvent) {
        ScopeEvent.quitZoom(e.player)
        return
    }

    @EventHandler
    private fun dropEvent(e: PlayerDropItemEvent) {
        ScopeEvent.quitZoom(e.player)
        return
    }

    @EventHandler
    private fun swapHandEvent(e: PlayerSwapHandItemsEvent) {
        ScopeEvent.quitZoom(e.player)
        return
    }

    @EventHandler
    private fun moveItemEvent(e: InventoryClickEvent) {
        val player = e.whoClicked as Player
        ScopeEvent.quitZoom(player)
        return
    }
}