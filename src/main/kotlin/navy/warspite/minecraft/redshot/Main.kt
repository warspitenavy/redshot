package navy.warspite.minecraft.redshot

import navy.warspite.minecraft.redshot.commands.CommandRegister
import navy.warspite.minecraft.redshot.commands.TabComplete
import navy.warspite.minecraft.redshot.event.CatchEvent
import navy.warspite.minecraft.redshot.event.InitialiseMap
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    companion object {
        lateinit var instance: JavaPlugin
            private set
    }
    override fun onEnable() {
        instance = this
        ConfigFile.load()
        LoadWeapons.load()
        InitialiseMap.initialise()
        getCommand("redshot")?.setExecutor(CommandRegister)
        getCommand("redshot")?.tabCompleter = TabComplete
        server.pluginManager.registerEvents(CatchEvent, this)
    }
}