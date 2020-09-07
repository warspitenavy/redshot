package navy.warspite.minecraft.redshot.commands

import navy.warspite.minecraft.redshot.LoadJsons
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter


object TabComplete : TabCompleter {
    private val commands = CommandRegister.commands.keys
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>
    ): MutableList<String>? {
        return when {
            3 < args.size -> {
                mutableListOf()
            }
            2 < args.size -> {
                when (args[0]) {
                    "give" -> LoadJsons.weaponJson.keys.toMutableList()
                    else -> mutableListOf()
                }
            }
            1 < args.size -> {
                when (args[0]) {
                    "get" -> LoadJsons.weaponJson.keys.toMutableList()
                    "give" -> Bukkit.getOnlinePlayers().map { it.name }.toMutableList()
                    else -> mutableListOf()
                }
            }
            args.isNotEmpty() -> {
                this.commands.toMutableList()
            }
            else -> null
        }
    }
}