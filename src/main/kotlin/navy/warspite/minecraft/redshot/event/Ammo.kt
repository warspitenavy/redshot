package navy.warspite.minecraft.redshot.event

import navy.warspite.minecraft.redshot.Main
import navy.warspite.minecraft.redshot.WeaponParam
import navy.warspite.minecraft.redshot.util.GetColoured.colouredText
import org.bukkit.NamespacedKey
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataType

object Ammo {
    private val plugin = Main.instance
    fun setAmmo(itemMeta: ItemMeta, amount: Int, weapon: WeaponParam.Parameters): ItemMeta {
        val key = NamespacedKey(plugin, "ammo")
        itemMeta.persistentDataContainer.set(key, PersistentDataType.INTEGER, amount)
        itemMeta.setDisplayName(getItemName(weapon.details.name, amount))
        return itemMeta
    }

    fun getItemName(itemName: String, amount: Int): String {
        return colouredText("""${itemName}&r «${amount}»""")
    }

    fun getAmmo(itemMeta: ItemMeta): Int? {
        val key = NamespacedKey(plugin, "ammo")
        val container = itemMeta.persistentDataContainer
        if (!container.has(key, PersistentDataType.INTEGER)) return null
        return container.get(key, PersistentDataType.INTEGER)
    }
}