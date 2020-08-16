package navy.warspite.minecraft.redshot.util

import org.bukkit.ChatColor

object GetColoured {
    fun colouredMessage(text: String): String {
        return ChatColor.translateAlternateColorCodes('&', "&r[&cRedShot&r] $text")
    }
    fun colouredText(text: String): String {
        return ChatColor.translateAlternateColorCodes('&', text)
    }
}