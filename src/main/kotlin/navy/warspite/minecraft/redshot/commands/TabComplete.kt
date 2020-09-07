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
//            args.size > 3 -> {
//                when (args[0]) {
//                    "get" -> LoadJsons.weaponJson.keys.toMutableList()
//                    "give" -> LoadJsons.weaponJson.keys.toMutableList()
//                    else -> mutableListOf()
//                }
//            }
            args.size > 2 -> {
                when (args[0]) {
                    "get" -> LoadJsons.weaponJson.keys.toMutableList()
                    "give" -> LoadJsons.weaponJson.keys.toMutableList()
                    else -> mutableListOf()
                }
            }
            args.size > 1 -> {
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