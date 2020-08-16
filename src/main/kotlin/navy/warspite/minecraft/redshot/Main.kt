package navy.warspite.minecraft.redshot

import navy.warspite.minecraft.redshot.commands.CommandRegister
import navy.warspite.minecraft.redshot.event.ShotEvent
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    companion object {
        lateinit var instance: Plugin
        private set
    }
    override fun onEnable() {
        instance = this
        saveDefaultConfig()
        LoadWeapons.generateMap()
        logger.info("Loaded weapons: ${LoadWeapons.weaponsHashMap.keys}") // print
        getCommand("redshot")?.setExecutor(CommandRegister)
        server.pluginManager.registerEvents(ShotEvent, this)
    }
}