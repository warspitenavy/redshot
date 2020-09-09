package navy.warspite.minecraft.redshot.event

import navy.warspite.minecraft.redshot.Main
import navy.warspite.minecraft.redshot.Parse
import navy.warspite.minecraft.redshot.util.PlaySound
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.scheduler.BukkitRunnable

object Reload {
    fun reloading(player: Player, itemMeta: ItemMeta, reload: Parse.Reload) {
        val ammo = GetMeta.ammo(itemMeta) ?: return
        if (ammo == reload.reloadAmount) return

        val sounds = reload.reloadingSounds.map { it.split('-') }
        var runCount = 0
        object : BukkitRunnable() {
            override fun run() {
                if (!CatchEvent.scopingPlayer[player]!!) cancel()
                if (runCount <= reload.reloadDuration) {
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
                    reload(player, itemMeta, reload)
                    cancel()
                }
            }
        }.runTaskTimer(Main.instance, 0, 1)
    }
    fun reload(player: Player, itemMeta: ItemMeta, reload: Parse.Reload) {

    }
}