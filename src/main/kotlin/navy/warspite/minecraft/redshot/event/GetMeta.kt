package navy.warspite.minecraft.redshot.event

import navy.warspite.minecraft.redshot.Main
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

object GetMeta {
    private val plugin = Main.instance

    fun getMeta(itemStack: ItemStack, id: String, dataType: String): Any? {
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

    fun weaponId(itemStack: ItemStack): Any? {
        val key = NamespacedKey(plugin, "weaponId")
        val itemMeta = itemStack.itemMeta ?: return null
        val container = itemMeta.persistentDataContainer
        return (if (container.has(key, PersistentDataType.STRING)) {
            container.get(key, PersistentDataType.STRING)
        } else null)
    }

    fun isWeapon(itemStack: ItemStack): Boolean {
        val key = NamespacedKey(plugin, "weaponId")
        val itemMeta = itemStack.itemMeta ?: return false
        val container = itemMeta.persistentDataContainer
        return (container.has(key, PersistentDataType.STRING))
    }
}