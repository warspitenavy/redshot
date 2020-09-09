package navy.warspite.minecraft.redshot.event

import navy.warspite.minecraft.redshot.Main
import navy.warspite.minecraft.redshot.Parse
import navy.warspite.minecraft.redshot.util.PlaySound
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.scheduler.BukkitRunnable

object Reload {
    fun cancel(player: Player) {
        CatchEvent.reloadingPlayer[player] = false
    }

    fun reloading(player: Player, itemMeta: ItemMeta, weapon: Parse.Parameters) {
        ScopeEvent.quitZoom(player)

        val reload = weapon.reload
        val ammo = GetMeta.ammo(itemMeta) ?: return
        if (ammo == reload.reloadAmount) return

        CatchEvent.reloadingPlayer[player] = true

        val sounds = reload.reloadingSounds.map { it.split('-') }
        var runCount = 0
        object : BukkitRunnable() {
            override fun run() {
                if (!CatchEvent.reloadingPlayer[player]!!) cancel()
                if (runCount < reload.reloadDuration) {
                    for (sound in sounds) {
                        if (runCount == sound[3].toInt()) {
                            PlaySound.playSound(
                                player,
                                Sound.valueOf(sound[0]),
                                sound[1].toFloat(),
                                sound[2].toFloat()
                            )
                        }
                    }
                    runCount++
                } else {
                    CatchEvent.reloadingPlayer[player] = false
                    reload(player, itemMeta, weapon)
                    cancel()
                }
            }
        }.runTaskTimer(Main.instance, 0, 1)
    }
    fun reload(player: Player, itemMeta: ItemMeta, weapon: Parse.Parameters) {
        val itemStack = player.inventory.itemInMainHand
        itemStack.itemMeta = Ammo.setAmmo(itemMeta, weapon.reload.reloadAmount, weapon)
    }
}