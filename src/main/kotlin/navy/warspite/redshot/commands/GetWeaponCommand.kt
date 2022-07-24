package navy.warspite.redshot.commands

import navy.warspite.redshot.GenerateWeapon
import navy.warspite.redshot.utils.Colour
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object GetWeaponCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage(Colour.mes("You must be a player to use this command."))
            return false
        }
        return when {
            args.size > 1 -> {
                for (arg in args.copyOfRange(1, args.size)) {
                    GenerateWeapon().addInventoryWeapon(sender, arg)
                }
                true
            }
            else -> {
                false
            }
        }
    }
}