package navy.warspite.minecraft.redshot.commands

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

object GiveCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        return when {
            args.size > 2 -> {
                val player = Bukkit.getPlayerExact(args[1])
                if (player == null) {
                    Messages.missingPlayer(sender)
                    return false
                }
                for (arg in args.copyOfRange(2, args.size)) {
                    GiveWeapon.give(player, arg)
                }
                true
            }
            args.size > 1 -> {
                val player = Bukkit.getPlayerExact(args[1])
                if (player == null) Messages.missingPlayer(sender)
                else Messages.notEnteredWeapon(sender)
                false
            }
            else -> {
                Messages.missingPlayer(sender)
                false
            }
        }
    }
}