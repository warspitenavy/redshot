package navy.warspite.redshot.commands

import navy.warspite.redshot.GenerateWeapon
import navy.warspite.redshot.utils.Colour
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

object GiveWeaponCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        val missingPlayer = "Missing player."
        return when {
            args.size > 2 -> {
                val player = Bukkit.getPlayerExact(args[1])
                if (player == null) {
                    sender.sendMessage(Colour.mes(missingPlayer))
                    return true
                }
                for (arg in args.copyOfRange(2, args.size)) {
                    GenerateWeapon().addInventoryWeapon(player, arg)
                }
                true
            }
            args.size > 1 -> {
                val player = Bukkit.getPlayerExact(args[1])
                if (player == null) sender.sendMessage(Colour.mes(missingPlayer))
                false
            }
            else -> false
        }
    }
}