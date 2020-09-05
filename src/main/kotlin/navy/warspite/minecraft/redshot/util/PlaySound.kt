package navy.warspite.minecraft.redshot.util

import navy.warspite.minecraft.redshot.Main
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.entity.Player

object PlaySound {
    private val plugin = Main.instance
    fun playSound(sounds: ArrayList<String>, player: Player) {
        sounds.forEach {
            val soundParam = it.split('-')
            Bukkit.getScheduler().runTaskLater(
                plugin, Runnable {
                    player.location.world?.playSound(
                        player.location,
                        Sound.valueOf(soundParam[0]),
                        soundParam[1].toFloat(),
                        soundParam[2].toFloat()
                    )
                }, soundParam[3].toLong()
            )
        }
    }
}