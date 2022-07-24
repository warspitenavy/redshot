package navy.warspite.redshot.commands

import navy.warspite.redshot.Main.Companion.weapons
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class TabComplete(private val commands: LinkedHashMap<String, CommandExecutor>) : TabCompleter {
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): MutableList<String>? {
        return when {
            args.size > 2 -> {
                when (args[0]) {
                    "get" -> weapons.map { it.detail.id }.toMutableList()
                    "give" -> weapons.map { it.detail.id }.toMutableList()
                    else -> mutableListOf()
                }
            }
            args.size > 1 -> {
                when (args[0]) {
                    "get" -> weapons.map { it.detail.id }.toMutableList()
                    "give" -> Bukkit.getOnlinePlayers().map { it.name }.toMutableList()
                    else -> mutableListOf()
                }
            }
            args.isNotEmpty() -> commands.keys.toMutableList()
            else -> null
        }
    }
}