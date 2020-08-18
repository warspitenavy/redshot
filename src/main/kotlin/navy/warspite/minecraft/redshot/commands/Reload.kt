package navy.warspite.minecraft.redshot.commands

import navy.warspite.minecraft.redshot.LoadWeapons
import navy.warspite.minecraft.redshot.util.GetColoured.colouredMessage
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

object Reload : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        LoadWeapons.generateMap()
        sender.sendMessage(colouredMessage("Reloaded weapons."))
        return true
    }
}