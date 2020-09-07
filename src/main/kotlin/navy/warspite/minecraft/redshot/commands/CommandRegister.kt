package navy.warspite.minecraft.redshot.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

object CommandRegister : CommandExecutor {
    val commands = linkedMapOf(
        "get" to GetCommand,
        "give" to GiveCommand,
        "reload" to Reload
    )

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        return when {
            args.isEmpty() -> {
                Messages.commands(sender)
                true
            }
            commands.containsKey(args[0]) -> {
                commands[args[0]]?.onCommand(sender, command, label, args)
                true
            }
            else -> {
                false
            }
        }
    }
}
