package navy.warspite.minecraft.redshot.event

import navy.warspite.minecraft.redshot.LoadJsons
import navy.warspite.minecraft.redshot.parse.Parse
import navy.warspite.minecraft.redshot.util.PlaySound
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

object ScopeEvent {
    fun toggleZoom(player: Player) {
        val itemMeta = player.inventory.itemInMainHand.itemMeta!!
        val weaponId = GetMeta.weaponId(itemMeta)
        val scope = LoadJsons.weaponJson[weaponId]?.scope ?: return
        if (CatchEvent.scopingPlayer[player]!!) {
            quitZoom(player, scope)
        }
        else {
            zoom(player, scope)
        }
    }
    fun zoom(player: Player, scope: Parse.Scope) {
        CatchEvent.scopingPlayer[player] = true
        if (scope.sight) player.inventory.helmet = ItemStack(Material.CARVED_PUMPKIN)
        PlaySound.play(scope.toggleZoomSounds, player)
        player.addPotionEffect(PotionEffect(PotionEffectType.SLOW, Int.MAX_VALUE, scope.zoomAmount))
        return
    }

    fun quitZoom(player: Player, scope: Parse.Scope) {
        CatchEvent.scopingPlayer[player] = false
        player.inventory.helmet = ItemStack(Material.AIR)
        PlaySound.play(scope.toggleZoomSounds, player)
        player.removePotionEffect(PotionEffectType.SLOW)
        return
    }
    fun quitZoom(player: Player) {
        CatchEvent.scopingPlayer[player] = false
        player.inventory.helmet = ItemStack(Material.AIR)
        player.removePotionEffect(PotionEffectType.SLOW)
        return
    }
}