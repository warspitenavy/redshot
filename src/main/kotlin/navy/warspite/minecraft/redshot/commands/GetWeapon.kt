package navy.warspite.minecraft.redshot.commands

import navy.warspite.minecraft.redshot.GenerateWeapon
import navy.warspite.minecraft.redshot.util.GetColoured.colouredMessage
import navy.warspite.minecraft.redshot.util.PlaySound
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object GetWeapon : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            Messages.playersCommand(sender)
            return false
        }
        return when {
            args.size > 1 -> {
                for (arg in args.copyOfRange(1, args.size)) {
                    val weapon = GenerateWeapon.itemStack(arg)
                    if (weapon == null) {
                        Messages.doesNotExist(sender, arg)
                        continue
                    }
                    sender.inventory.addItem(weapon)
                    GenerateWeapon.sounds(arg)?.let { PlaySound.play(it, sender) }
                }
                true
            }
            else -> {
                Messages.missingPlayer(sender)
                false
            }
        }
    }
}