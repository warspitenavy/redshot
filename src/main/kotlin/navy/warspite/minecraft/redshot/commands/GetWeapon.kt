package navy.warspite.minecraft.redshot.commands

import navy.warspite.minecraft.redshot.GenerateItemStack
import navy.warspite.minecraft.redshot.util.GetColoured.getColoured
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

object GetWeapon : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        return if (sender is Player) {
            if (args.size < 2) {
                sender.sendMessage(getColoured("Please enter weapons ID."))
                return false
            }
            val itemStack = GenerateItemStack.generate(args[1])
            if (itemStack is String) { sender.sendMessage(itemStack) }
            if (itemStack is ItemStack) { sender.inventory.addItem(itemStack) }
            true
        } else {
            sender.sendMessage(getColoured("This is players command."))
            false
        }
    }
}