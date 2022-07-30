package navy.warspite.redshot.controller

import navy.warspite.redshot.Main.Companion.plugin
import navy.warspite.redshot.controller.ItemMetaController.weaponNameGen
import org.bukkit.NamespacedKey
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataType

object AmmoController {
    private fun key() = NamespacedKey(plugin, "ammo")
    fun setAmmo(itemMeta: ItemMeta, amount: Int, weaponName: String): ItemMeta {
        itemMeta.persistentDataContainer.set(key(), PersistentDataType.INTEGER, amount)
        itemMeta.setDisplayName(weaponNameGen(weaponName, amount))
        return itemMeta
    }

    fun getAmmo(itemMeta: ItemMeta): Int? {
        val container = itemMeta.persistentDataContainer
//        val amount = container.get(key, PersistentDataType.INTEGER) ?: return null
        return container.get(key(), PersistentDataType.INTEGER)
    }
}