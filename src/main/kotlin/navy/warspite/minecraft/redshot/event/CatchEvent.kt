package navy.warspite.minecraft.redshot.event

import navy.warspite.minecraft.redshot.LoadFiles
import navy.warspite.minecraft.redshot.Main
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.*

object CatchEvent : Listener {
    private val plugin = Main.instance
    val shootingPlayer = linkedMapOf<Player, Boolean>()
    val scopingPlayer = linkedMapOf<Player, Boolean>()
    val reloadingPlayer = linkedMapOf<Player, Boolean>()

    @EventHandler
    private fun joinEvent(e: PlayerJoinEvent) {
        InitialiseMap.initialise(e.player)
    }

    @EventHandler
    private fun clickEvent(e: PlayerInteractEvent) {
        InitialiseMap.initialise(e.player)
        if (e.action == Action.RIGHT_CLICK_AIR || e.action == Action.RIGHT_CLICK_BLOCK) {
            val itemMeta = e.player.inventory.itemInMainHand.itemMeta ?: return
            if (GetMeta.isWeapon(itemMeta)) ShootEvents.shooting(e.player)
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
        val item = e.itemDrop.itemStack.itemMeta ?: return
        if (GetMeta.isWeapon(item)) {
            e.isCancelled = true
            val weapon = LoadFiles.weaponJson[GetMeta.weaponId(item)] ?: return
            ShootEvents.waitReload(e.player, item, weapon.reload)
            return
        }
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