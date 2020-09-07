package navy.warspite.minecraft.redshot.event

import navy.warspite.minecraft.redshot.LoadFiles
import navy.warspite.minecraft.redshot.Main
import navy.warspite.minecraft.redshot.util.GetColoured.colouredText
import org.bukkit.NamespacedKey
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataType

object SetAmmo {
    private val plugin = Main.instance
    fun setAmmo(itemMeta: ItemMeta, amount: Int): ItemMeta {
        val key = NamespacedKey(plugin, "ammo")
        val weaponId = NamespacedKey(plugin, "weaponId")
        val container = itemMeta.persistentDataContainer

        val weapon = LoadFiles.weaponJson[container.get(weaponId, PersistentDataType.STRING)]
            ?: return itemMeta

        itemMeta.persistentDataContainer.set(key, PersistentDataType.INTEGER, amount)
        itemMeta.setDisplayName(
            colouredText(
                """${weapon.itemInformation.itemName} □ «$amount≫"""
            )
        )
        return itemMeta
    }
}