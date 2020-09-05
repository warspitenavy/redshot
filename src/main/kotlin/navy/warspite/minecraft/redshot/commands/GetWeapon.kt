package navy.warspite.minecraft.redshot.commands

import navy.warspite.minecraft.redshot.GenerateWeapon
import navy.warspite.minecraft.redshot.Main
import navy.warspite.minecraft.redshot.util.GetColoured.colouredMessage
import navy.warspite.minecraft.redshot.util.PlaySound
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object GetWeapon : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        return if (sender is Player) {
            if (args.size < 2) {
                sender.sendMessage(colouredMessage("Please enter ID of weapon."))
                return false
            }
            val item = GenerateWeapon.itemStack(args[1])
            if (item == null) {
                sender.sendMessage(colouredMessage("Its weapon does not exist."))
                return false
            }

            val sounds = GenerateWeapon.sounds(args[1])

            sender.inventory.addItem(item)
            if (sounds != null) PlaySound.playSound(sounds, sender)
            true
        } else {
            sender.sendMessage(colouredMessage("This is players command."))
            false
        }
    }
}