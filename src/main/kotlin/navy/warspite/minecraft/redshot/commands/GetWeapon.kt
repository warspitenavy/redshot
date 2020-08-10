package navy.warspite.minecraft.redshot.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object GetWeapon: CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        return if (sender is Player) {
            sender.sendMessage("/redshot get")
            true
        } else {
            sender.sendMessage("/redshot get is players command")
            false
        }
    }
}