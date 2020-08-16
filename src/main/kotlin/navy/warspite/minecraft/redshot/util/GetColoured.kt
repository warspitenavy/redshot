package navy.warspite.minecraft.redshot.util

import org.bukkit.ChatColor

object GetColoured {
    fun getColoured(text: String): String {
        return ChatColor.translateAlternateColorCodes('&', "&r[&cRedShot&r] $text")
    }
}