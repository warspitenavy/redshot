package navy.warspite.minecraft.redshot.event

import navy.warspite.minecraft.redshot.Main
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.metadata.MetadataValue
import org.bukkit.persistence.PersistentDataType

object GetMeta {
    private val plugin = Main.instance

    fun meta(list: List<MetadataValue>): MetadataValue? {
        for (value in list) {
            if (value.owningPlugin?.name == plugin.name) return value
        }
        return null
    }

    fun isWeapon(itemMeta: ItemMeta): Boolean {
        val key = NamespacedKey(plugin, "weaponId")
        val container = itemMeta.persistentDataContainer
        return container.has(key, PersistentDataType.STRING)
    }

    fun isNotWeapon(itemMeta: ItemMeta): Boolean {
        val key = NamespacedKey(plugin, "weaponId")
        val container = itemMeta.persistentDataContainer
        return !container.has(key, PersistentDataType.STRING)
    }

    fun weaponId(itemMeta: ItemMeta): String? {
        val key = NamespacedKey(plugin, "weaponId")
        val container = itemMeta.persistentDataContainer
        return if (container.has(key, PersistentDataType.STRING)) {
            container.get(key, PersistentDataType.STRING)
        } else {
            null
        }
    }

    fun ammo(itemMeta: ItemMeta): Int? {
        val key = NamespacedKey(plugin, "ammo")
        val container = itemMeta.persistentDataContainer
        return if (container.has(key, PersistentDataType.INTEGER)) {
            container.get(key, PersistentDataType.INTEGER)
        } else {
            null
        }
    }
}