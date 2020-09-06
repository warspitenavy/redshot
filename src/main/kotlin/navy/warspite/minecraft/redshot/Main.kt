package navy.warspite.minecraft.redshot

import navy.warspite.minecraft.redshot.commands.CommandRegister
import navy.warspite.minecraft.redshot.commands.TabComplete
import navy.warspite.minecraft.redshot.event.CatchEvent
import navy.warspite.minecraft.redshot.event.HitEvent
import navy.warspite.minecraft.redshot.event._ShotEvent
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
        LoadJsons.generateWeapon()
        getCommand("redshot")?.setExecutor(CommandRegister)
        getCommand("redshot")?.tabCompleter = TabComplete
        server.pluginManager.registerEvents(CatchEvent, this)
        server.pluginManager.registerEvents(_ShotEvent, this)
        server.pluginManager.registerEvents(HitEvent, this)
    }
}