package navy.warspite.minecraft.redshot

import navy.warspite.minecraft.redshot.event.ShotEvent
import org.bukkit.plugin.java.JavaPlugin

class Main: JavaPlugin() {
    override fun onEnable() {
        getCommand("redshot")?.setExecutor(CommandRegister)
        server.pluginManager.registerEvents(ShotEvent, this)
    }
}