package navy.warspite.minecraft.redshot.commands

import navy.warspite.minecraft.redshot.LoadWeapons
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

object TabComplete: TabCompleter {
    private val commands = CommandRegister.commands.keys
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>
    ): MutableList<String>? {
        val commands = mutableListOf<String>()
        this.commands.forEach{ commands.add(it) }
        when(args.size) {
            1 -> {
                return commands
            }
            2 -> {
                val weapons = mutableListOf<String>()
                if (args[0] == "get") {
                    LoadWeapons.weaponsAnyMap.keys.forEach{ weapons.add(it) }
                }
                return weapons
            }
        }
        return null
    }
}