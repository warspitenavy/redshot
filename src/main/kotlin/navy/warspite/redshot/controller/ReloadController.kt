package navy.warspite.redshot.controller

import navy.warspite.redshot.Main.Companion.plugin
import navy.warspite.redshot.Main.Companion.reloadingState
import navy.warspite.redshot.controller.AmmoController.setAmmo
import navy.warspite.redshot.controller.ScopeController.quitZoom
import navy.warspite.redshot.params.WeaponParam
import navy.warspite.redshot.utils.Sound.playSound
import navy.warspite.redshot.utils.Sound.splitSound
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.scheduler.BukkitRunnable

object ReloadController {
    fun reloading(player: Player, itemMeta: ItemMeta, weapon: WeaponParam.Weapon) {
        /** リロードで照準を中断 */
        quitZoom(player, null)

        val reload = weapon.reload
        val sounds = reload.reloadingSounds.map { splitSound(it) }

        reloadingState[player.uniqueId] = true

        object : BukkitRunnable() {
            var count = 0
            override fun run() {
                /** リロード中ではない場合は中断 */
                if (reloadingState[player.uniqueId] == false) cancel()
                /**
                 *  playSoundByListではリロード音を中断できないので
                 *  再実装する、不本意。
                 */
                if (count < reload.reloadDuration)  {
                    for (sound in sounds) {
                        if (count == sound[3].toInt()) {
                            playSound(
                                player,
                                Sound.valueOf(sound[0]),
                                sound[1].toFloat(),
                                sound[2].toFloat()
                            )
                        }
                    }
                    count++
                } else {
                    reloadingState[player.uniqueId] = false
                    reload(player, itemMeta, weapon)
                    cancel()
                }
            }
        }.runTaskTimer(plugin, 0, 1)
    }
    fun reload(player: Player, itemMeta: ItemMeta, weapon: WeaponParam.Weapon) {
        val itemStack = player.inventory.itemInMainHand
        itemStack.itemMeta = setAmmo(itemMeta, weapon.reload.reloadAmount, weapon.detail.name)
    }
}