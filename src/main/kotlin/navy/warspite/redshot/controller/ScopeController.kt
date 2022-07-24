package navy.warspite.redshot.controller

import navy.warspite.redshot.Main.Companion.scopingState
import navy.warspite.redshot.params.WeaponParam
import navy.warspite.redshot.utils.Sound.playSoundByList
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

object ScopeController {
    fun toggleScope(player: Player, scope: WeaponParam.Scope) {
        if (scopingState[player.uniqueId] == true) quitZoom(player, scope)
        else zoom(player, scope)
    }
    private fun zoom(player: Player, scope: WeaponParam.Scope) {
        scopingState[player.uniqueId] = true
        player.addPotionEffect(PotionEffect(PotionEffectType.SLOW, Int.MAX_VALUE, scope.zoomAmount))
        /** かぼちゃ */
        if (scope.sight) player.inventory.helmet = ItemStack(Material.CARVED_PUMPKIN)
        if (scope.toggleZoomSounds.isNotEmpty()) playSoundByList(scope.toggleZoomSounds, player)
    }
    fun quitZoom(player: Player, scope: WeaponParam.Scope?) {
        scopingState[player.uniqueId] = false
        player.removePotionEffect(PotionEffectType.SLOW)
        /** かぼちゃ */
        if (player.inventory.helmet == ItemStack(Material.CARVED_PUMPKIN)) player.inventory.helmet = null
        if (scope != null) {
            if (scope.toggleZoomSounds.isNotEmpty()) playSoundByList(scope.toggleZoomSounds, player)
        }
    }
}