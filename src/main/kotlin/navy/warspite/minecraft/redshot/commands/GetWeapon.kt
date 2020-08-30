package navy.warspite.minecraft.redshot.commands

import navy.warspite.minecraft.redshot.GenerateItemStack
import navy.warspite.minecraft.redshot.Main
import navy.warspite.minecraft.redshot.util.GetColoured.colouredMessage
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

object GetWeapon : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        return if (sender is Player) {
            val plugin = Main.instance
            if (args.size < 2) {
                sender.sendMessage(colouredMessage("Please enter ID of weapon."))
                return false
            }
            val item = GenerateItemStack.generate(args[1])
            if (item is String) {
                sender.sendMessage(item)
                return false
            }

            item as LinkedHashMap<*, *>
            val itemStack = item["itemStack"]
            val soundsAcquired = item["soundsAcquired"]

            sender.inventory.addItem(itemStack as ItemStack)

            soundsAcquired as ArrayList<*>?
            soundsAcquired?.forEach {
                it as String
                val soundParam = it.split('-')
                Bukkit.getScheduler().runTaskLater(
                    plugin, Runnable {
                        sender.playSound(
                            sender.location,
                            Sound.valueOf(soundParam[0]),
                            soundParam[1].toFloat(),
                            soundParam[2].toFloat()
                        )
                    }, soundParam[3].toLong()
                )
            }
            true
        } else {
            sender.sendMessage(colouredMessage("This is players command."))
            false
        }
    }
}