package navy.warspite.minecraft.redshot.util

import navy.warspite.minecraft.redshot.Main
import org.bukkit.ChatColor

object GetColoured {
    private val plugin = Main.instance
    fun colouredMessage(string: String): String {
        return ChatColor.translateAlternateColorCodes('&', """&r[&cRedShot&r] $string""")
    }
    fun colouredText(string: String): String {
        return ChatColor.translateAlternateColorCodes('&', string)
    }
    fun coloured(string: String, prefix: Boolean = false): String {
        if (prefix) return ChatColor.translateAlternateColorCodes('&', """&r[&c${plugin.name}&r] $string""")
        return ChatColor.translateAlternateColorCodes('&', string)
    }
    fun colourCode(colour: ChatColor): String {
        return when (colour) {
            ChatColor.BLACK -> "&0"
            ChatColor.DARK_BLUE -> "&1"
            ChatColor.DARK_GREEN -> "&2"
            ChatColor.DARK_AQUA -> "&3"
            ChatColor.DARK_RED -> "&4"
            ChatColor.DARK_PURPLE -> "&5"
            ChatColor.GOLD -> "&6"
            ChatColor.GRAY -> "&7"
            ChatColor.DARK_GRAY -> "&8"
            ChatColor.BLUE -> "&9"
            ChatColor.GREEN -> "&a"
            ChatColor.AQUA -> "&b"
            ChatColor.RED -> "&c"
            ChatColor.LIGHT_PURPLE -> "&d"
            ChatColor.YELLOW -> "&e"
            ChatColor.WHITE -> "&f"
            ChatColor.MAGIC -> "&m"
            ChatColor.BOLD -> "&b"
            ChatColor.STRIKETHROUGH -> "&m"
            ChatColor.UNDERLINE -> "&n"
            ChatColor.ITALIC -> "&i"
            ChatColor.RESET -> "&r"
            else -> "&r"
        }
    }
}