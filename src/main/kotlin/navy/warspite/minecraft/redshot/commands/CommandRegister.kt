package navy.warspite.minecraft.redshot.commands

import navy.warspite.minecraft.redshot.util.GetColoured.colouredText
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

object CommandRegister : CommandExecutor {
    val commands = linkedMapOf(
        "get" to GetWeapon,
        "give" to GiveWeapon,
        "reload" to Reload
    )

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        return when {
            args.isEmpty() -> {
                sender.sendMessage(
                    colouredText(
                        """
                            &r--------- &cRedShot &r----------------
                            &r/redshot get <&cWeapon ID&r>
                            &r/redshot give <&cPlayer&r> <&cWeapon ID&r>
                        """.trimIndent()
                    )
                )
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
