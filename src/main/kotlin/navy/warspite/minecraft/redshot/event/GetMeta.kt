package navy.warspite.minecraft.redshot.event

import navy.warspite.minecraft.redshot.Main
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataType

object GetMeta {
    private val plugin = Main.instance

    fun isWeapon(itemMeta: ItemMeta): Boolean {
        val key = NamespacedKey(plugin, "weaponId")
        val container = itemMeta.persistentDataContainer
        return container.has(key, PersistentDataType.STRING)
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

    fun itemMetaL(itemStack: ItemStack, id: String, dataType: String): Any? {
        val key = NamespacedKey(plugin, id)
        val itemMeta = itemStack.itemMeta ?: return null
        val container = itemMeta.persistentDataContainer
        val type =
            when (dataType) {
                "STRING" -> PersistentDataType.STRING
                "INTEGER" -> PersistentDataType.INTEGER
                else -> return null
            }
        return (if (container.has(key, type)) {
            container.get(key, type)
        } else null)
    }
}