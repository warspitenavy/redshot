package navy.warspite.minecraft.redshot.commands

import navy.warspite.minecraft.redshot.LoadJsons
import navy.warspite.minecraft.redshot.LoadWeapons
import navy.warspite.minecraft.redshot.util.GetColoured.colouredMessage
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

object Reload : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        LoadJsons.generateWeapon()
        sender.sendMessage(colouredMessage("Reloaded weapons."))
        return true
    }
}