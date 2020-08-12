package navy.warspite.minecraft.redshot

import navy.warspite.minecraft.redshot.commands.GetWeapon
import navy.warspite.minecraft.redshot.commands.GiveWeapon
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

object CommandRegister: CommandExecutor {
    private val commands = mapOf(
        "get" to GetWeapon,
        "give" to GiveWeapon
    )
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        return when {
            args.isEmpty() -> {
                sender.sendMessage("""
                    /redshot get <Weapon>
                    /redshot give <Player> <Weapon>
                """.trimIndent())
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
