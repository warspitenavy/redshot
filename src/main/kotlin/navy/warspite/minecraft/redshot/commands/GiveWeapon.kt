package navy.warspite.minecraft.redshot.commands

import navy.warspite.minecraft.redshot.GenerateWeapon
import navy.warspite.minecraft.redshot.util.PlaySound
import org.bukkit.entity.Player

object GiveWeapon {
    fun give(player: Player, string: String) {
        val weapon = GenerateWeapon.itemStack(string)
        if (weapon == null) { Messages.doesNotExist(player, string) }
        else {
            player.inventory.addItem(weapon)
            GenerateWeapon.sounds(string)?.let { PlaySound.play(it, player) }
        }
    }
}