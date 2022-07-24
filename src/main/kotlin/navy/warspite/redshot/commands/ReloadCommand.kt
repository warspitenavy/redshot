package navy.warspite.redshot.commands

import navy.warspite.redshot.LoadWeapons.reloadWeapons
import navy.warspite.redshot.utils.Colour
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

object ReloadCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        reloadWeapons()
        sender.sendMessage(Colour.mes("You have reloaded config."))
        return true
    }
}