package navy.warspite.minecraft.redshot.util

import navy.warspite.minecraft.redshot.Main
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

object PlaySound {
    private val plugin = Main.instance
    fun playByList(sounds: ArrayList<String>, player: Player) {
        for (sound in sounds) {
            val soundParam = sound.split('-')
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
    fun playSound(player: Player, sound: Sound, volume: Float, pitch: Float) {
        player.location.world?.playSound(
            player.location,
            sound,
            volume,
            pitch
        )
    }
}