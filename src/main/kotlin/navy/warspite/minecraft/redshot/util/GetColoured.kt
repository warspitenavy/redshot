package navy.warspite.minecraft.redshot.util

import org.bukkit.ChatColor

object GetColoured {
    fun colouredMessage(string: String): String {
        return ChatColor.translateAlternateColorCodes('&', """&r[&cRedShot&r] $string""")
    }

    fun colouredText(string: String): String {
        return ChatColor.translateAlternateColorCodes('&', string)
    }
}