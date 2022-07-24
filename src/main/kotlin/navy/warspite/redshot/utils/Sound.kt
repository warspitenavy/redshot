package navy.warspite.redshot.utils

import navy.warspite.redshot.Main.Companion.plugin
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

object Sound {
    fun splitSound(sound: String) = sound.split('-')
    fun playSoundByList(sounds: List<String>, player: Player) {
        for (sound in sounds) {
            val soundParam = splitSound(sound)
            object: BukkitRunnable() {
                override fun run() {
                    playSound(
                        player,
                        Sound.valueOf(soundParam[0]),
                        soundParam[1].toFloat(),
                        soundParam[2].toFloat()
                    )
                }
            }.runTaskLater(plugin, soundParam[1].toLong())
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