package navy.warspite.minecraft.redshot

import navy.warspite.minecraft.redshot.commands.CommandRegister
import navy.warspite.minecraft.redshot.commands.TabComplete
import navy.warspite.minecraft.redshot.event.ShotEvent
import org.bukkit.command.TabCompleter
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
        getCommand("redshot")?.setExecutor(CommandRegister)
        getCommand("redshot")?.tabCompleter = TabComplete
        server.pluginManager.registerEvents(ShotEvent, this)
    }
}