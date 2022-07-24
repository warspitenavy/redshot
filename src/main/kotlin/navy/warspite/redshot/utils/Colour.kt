package navy.warspite.redshot.utils

import navy.warspite.redshot.Main.Companion.plugin
import org.bukkit.ChatColor

object Colour {
    /**
     * @param string 変換対象のテキスト
     * @return 変換後のテキスト
     */
    fun mes(string: String): String {
        return ChatColor.translateAlternateColorCodes('&', "&r[&c${plugin.name}&r] $string")
    }

    /**
     * @param string 変換対象のテキスト
     * @return 変換後のテキスト
     */
    fun text(string: String): String {
        return ChatColor.translateAlternateColorCodes('&', string)
    }

    /**
     * カラーコード逆変換用
     * @param colour Bukkitカラーコード
     * @return 変換後のカラーコード
     */
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