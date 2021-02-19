package navy.warspite.minecraft.redshot.commands

import navy.warspite.minecraft.redshot.LoadWeapons
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

object Reload : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        LoadWeapons.load()
        Messages.reloadedConfig(sender)
        return true
    }
}