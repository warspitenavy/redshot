package navy.warspite.minecraft.redshot.commands

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
        }
        return null
    }
}