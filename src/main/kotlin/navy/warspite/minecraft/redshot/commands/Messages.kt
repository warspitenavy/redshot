package navy.warspite.minecraft.redshot.commands

import navy.warspite.minecraft.redshot.util.GetColoured.colouredMessage
import navy.warspite.minecraft.redshot.util.GetColoured.colouredText
import org.bukkit.command.CommandSender

object Messages {
    fun missingPlayer(sender: CommandSender) {
        sender.sendMessage(colouredMessage("Missing player."))
        return
    }

    fun doesNotExist(sender: CommandSender, string: String) {
        sender.sendMessage(colouredMessage("$string does not exist."))
    }

    fun notEnteredWeapon(sender: CommandSender) {
        sender.sendMessage(colouredMessage("Please enter ID of weapon."))
    }

    fun playersCommand(sender: CommandSender) {
        sender.sendMessage(colouredMessage("This is players command."))
    }

    fun commands(sender: CommandSender) {
        sender.sendMessage(
            colouredText(
                """
                    &r--------- &cRedShot &r----------------
                            &r/redshot get <&cWeapon ID&r>
                            &r/redshot give <&cPlayer&r> <&cWeapon ID&r>
                """.trimIndent()
            )
        )
    }
}