package navy.warspite.redshot.events

import navy.warspite.redshot.Main.Companion.reloadingState
import navy.warspite.redshot.StateController.initialiseState
import navy.warspite.redshot.controller.ProjectileController.shooting
import navy.warspite.redshot.controller.ScopeController.quitZoom
import navy.warspite.redshot.controller.ScopeController.toggleScope
import navy.warspite.redshot.controller.ItemMetaController.getProjectileMetadata
import navy.warspite.redshot.controller.ItemMetaController.getWeaponByItemMeta
import navy.warspite.redshot.controller.ReloadController.reloading
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.entity.ProjectileHitEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerEggThrowEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerItemHeldEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerSwapHandItemsEvent

class CatchEvent : Listener {

    @EventHandler
    private fun playerJoinEvent(event: PlayerJoinEvent) {
        initialiseState(event.player)
    }

    /**
     * クリックのイベント
     */
    @EventHandler
    private fun playerInteract(event: PlayerInteractEvent) {
        /** 右・左クリックのイベント以外は除外 */
        if (event.action == Action.RIGHT_CLICK_AIR ||
            event.action == Action.RIGHT_CLICK_BLOCK ||
            event.action == Action.LEFT_CLICK_AIR ||
            event.action == Action.LEFT_CLICK_BLOCK
        ) {
            if (reloadingState[event.player.uniqueId] == true) return
            val itemMeta = event.player.inventory.itemInMainHand.itemMeta ?: return
            val weapon = getWeaponByItemMeta(itemMeta) ?: return
            /** 右クリックの場合、発射 */
            if (event.action == Action.RIGHT_CLICK_AIR || event.action == Action.RIGHT_CLICK_BLOCK) {
                shooting(event.player, weapon)
            } else {
                /** 左クリックの場合、ズーム */
                if (weapon.scope != null) toggleScope(event.player, weapon.scope)
            }
        }
    }

    /** アイテム持ち替えイベント */
    @EventHandler
    private fun playerItemHeldEvent(event: PlayerItemHeldEvent) {
        quitZoom(event.player, null)
        reloadingState[event.player.uniqueId] = false
    }

    /** アイテムをドロップ時 */
    @EventHandler
    private fun playerDropItemEvent(event: PlayerDropItemEvent) {
        val itemMeta = event.itemDrop.itemStack.itemMeta ?: return
        val weapon = getWeaponByItemMeta(itemMeta) ?: return

        quitZoom(event.player, null)

        if (reloadingState[event.player.uniqueId] == true) {
            event.isCancelled = true
        } else {
            reloading(event.player, itemMeta, weapon)
            event.isCancelled = true
        }
    }

    /** 持ち替え */
    @EventHandler
    private fun playerSwapHandItemEvent(event: PlayerSwapHandItemsEvent) {
        quitZoom(event.player, null)
        reloadingState[event.player.uniqueId] = false
    }

    /** インベントリ操作時 */
    @EventHandler
    private fun inventoryClickEvent(event: InventoryClickEvent) {
        val player = event.whoClicked as Player
        quitZoom(player, null)
        reloadingState[player.uniqueId] = false
    }

    /** EGG属性の弾丸で孵化させない */
    @EventHandler
    private fun playerEggThrowEvent(event: PlayerEggThrowEvent) {
        if (event.egg.hasMetadata("shooter")) {
            event.isHatching = false
        }
    }

    /** ブロック破壊禁止 */
    @EventHandler
    private fun blockBreakEvent(event: BlockBreakEvent) {
        val iteMeta = event.player.inventory.itemInMainHand.itemMeta ?: return
        getWeaponByItemMeta(iteMeta) ?: return
        event.isCancelled = true
    }

    /**  被弾のイベント */
    @EventHandler
    private fun projectileHitEvent(event: ProjectileHitEvent) {
        val projectile = event.entity

//        val weaponId = getProjectileMetadata(projectile.getMetadata("weaponId"))?: return
        val shooter = getProjectileMetadata(projectile.getMetadata("shooter")) ?: return
        val damage = getProjectileMetadata(projectile.getMetadata("damage"))

//        val weapon = weapons.find { it.detail.id == weaponId.value() as String } ?: return

        val victim = event.hitEntity as LivingEntity?
        val shooterValue = shooter.value() as Player?

        victim?.noDamageTicks = 0
        damage?.value().let { victim?.damage((it as Int).toDouble(), shooterValue) }
    }
}