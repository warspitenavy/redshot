package navy.warspite.redshot

import navy.warspite.redshot.LoadWeapons.loadWeaponsAsYaml
import navy.warspite.redshot.commands.*
import navy.warspite.redshot.events.CatchEvent
import navy.warspite.redshot.params.WeaponParam
import org.bukkit.plugin.java.JavaPlugin
import java.util.UUID

class Main : JavaPlugin() {
    companion object {
        internal lateinit var plugin: JavaPlugin
        internal val weapons: MutableList<WeaponParam.Weapon> = mutableListOf()

        /**
         * 発射中、リロード中などの状態管理
         */
        internal val shootingState = linkedMapOf<UUID, Boolean>()
        internal val scopingState = linkedMapOf<UUID, Boolean>()
        internal val reloadingState = linkedMapOf<UUID, Boolean>()
    }

    init {
        plugin = this
    }

    private val commands = linkedMapOf(
        "get" to GetWeaponCommand,
        "give" to GiveWeaponCommand,
        "reload" to ReloadCommand
    )

    override fun onEnable() {
        plugin.saveDefaultConfig()

        weapons.clear()
        weapons.addAll(loadWeaponsAsYaml().toMutableList())

        val pluginCommand = getCommand("redshot")
        pluginCommand?.setExecutor(Register(commands))
        pluginCommand?.tabCompleter = TabComplete(commands)

        server.pluginManager.registerEvents(CatchEvent(), this)
    }
}