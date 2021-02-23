package navy.warspite.minecraft.redshot.event

import navy.warspite.minecraft.redshot.LoadWeapons
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.*

object CatchEvent : Listener {
    val shootingPlayer = linkedMapOf<Player, Boolean>()
    val scopingPlayer = linkedMapOf<Player, Boolean>()
    val reloadingPlayer = linkedMapOf<Player, Boolean>()

    @EventHandler
    private fun playerJoinEvent(e: PlayerJoinEvent) {
        InitialiseMap.initialise(e.player)
    }

    @EventHandler
    private fun playerInteractEvent(e: PlayerInteractEvent) {
        InitialiseMap.initialise(e.player)
        if (e.action == Action.RIGHT_CLICK_AIR || e.action == Action.RIGHT_CLICK_BLOCK) {
//            if (reloadingPlayer[e.player]!!) reloadingPlayer[e.player] = false
            if (reloadingPlayer[e.player]!!) return
            val itemMeta = e.player.inventory.itemInMainHand.itemMeta ?: return
            if (GetMeta.isWeapon(itemMeta)) ShootEvents.shooting(e.player)
            else return
        }
        if (e.action == Action.LEFT_CLICK_AIR || e.action == Action.LEFT_CLICK_BLOCK) {
            val itemMeta = e.player.inventory.itemInMainHand.itemMeta ?: return
            if (reloadingPlayer[e.player]!!) return
            if (GetMeta.isWeapon(itemMeta)) ScopeEvent.toggleZoom(e.player)
            else return
        }
    }

    @EventHandler
    private fun playerItemHeldEvent(e: PlayerItemHeldEvent) {
        ScopeEvent.quitZoom(e.player)
        Reload.cancel(e.player)
        return
    }

    @EventHandler
    private fun playerDropItemEvent(e: PlayerDropItemEvent) {
        ScopeEvent.quitZoom(e.player)
        val item = e.itemDrop.itemStack.itemMeta ?: return
        val weapon = LoadWeapons.weaponJson[GetMeta.weaponId(item)] ?: return

        if (!GetMeta.isWeapon(item)) return

        if (reloadingPlayer[e.player]!!) {
            e.isCancelled = true // LEFT_CLICK発火
            return
        } else {
            Reload.reloading(e.player, item, weapon)
//            reloadingPlayer[e.player] = true
            e.isCancelled = true // LEFT_CLICK発火
            return
        }
    }

    @EventHandler
    private fun playerEggThrowEvent(e: PlayerEggThrowEvent) {
        if (!e.egg.hasMetadata("shooter")) return
        e.isHatching = false
    }

    @EventHandler
    private fun playerSwapHandItemEvent(e: PlayerSwapHandItemsEvent) {
        ScopeEvent.quitZoom(e.player)
        Reload.cancel(e.player)
        return
    }

    @EventHandler
    private fun inventoryClickEvent(e: InventoryClickEvent) {
        val player = e.whoClicked as Player
        ScopeEvent.quitZoom(player)
        Reload.cancel(player)
        return
    }

    @EventHandler
    private fun blockBreakEvent(e: BlockBreakEvent) {
        val itemMeta = e.player.inventory.itemInMainHand.itemMeta ?: return
        if (GetMeta.isNotWeapon(itemMeta)) return
        e.isCancelled = true
    }

//    @EventHandler
//    private fun playerDeathEvent(e: PlayerDeathEvent) {
//        e.deathMessage = null
//    }
}