package navy.warspite.minecraft.redshot.commands

import navy.warspite.minecraft.redshot.GenerateWeapon
import navy.warspite.minecraft.redshot.util.GetColoured.colouredMessage
import navy.warspite.minecraft.redshot.util.PlaySound
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object GiveWeapon : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        return when {
            args.size > 2 -> {
                val player = Bukkit.getPlayerExact(args[1])
                if (player == null) {
                    Messages.missingPlayer(sender)
                    return false
                }
                for (arg in args.copyOfRange(2, args.size)) {
                    val weapon = GenerateWeapon.itemStack(arg)
                    if (weapon == null) {
                        sender.sendMessage(colouredMessage("$arg does not exist."))
                        continue
                    }
                    player.inventory.addItem(weapon)
                    GenerateWeapon.sounds(arg)?.let { PlaySound.play(it, player) }
                }
                true
            }
            args.size > 1 -> {
                val player = Bukkit.getPlayerExact(args[1])
                if (player == null) Messages.missingPlayer(sender)
                else Messages.notEnteredWeapon(sender)
                false
            }
            else -> {
                Messages.missingPlayer(sender)
                false
            }
        }
    }
}