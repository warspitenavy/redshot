package navy.warspite.minecraft.redshot

import navy.warspite.minecraft.redshot.event.ShotEvent
import org.bukkit.plugin.java.JavaPlugin

class Main: JavaPlugin() {
    override fun onEnable() {
        saveDefaultConfig()
        LoadWeapons(this).loadWeapon()
        getCommand("redshot")?.setExecutor(CommandRegister)
        server.pluginManager.registerEvents(ShotEvent, this)
    }
}