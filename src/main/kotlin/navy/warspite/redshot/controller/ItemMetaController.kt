package navy.warspite.redshot.controller

import navy.warspite.redshot.Main.Companion.plugin
import navy.warspite.redshot.Main.Companion.weapons
import navy.warspite.redshot.params.WeaponParam
import navy.warspite.redshot.utils.Colour
import org.bukkit.NamespacedKey
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.metadata.MetadataValue
import org.bukkit.persistence.PersistentDataType

object ItemMetaController {
    fun getProjectileMetadata(values: List<MetadataValue>): MetadataValue? {
        return values.find { it.owningPlugin?.name == plugin.name }
    }
    fun getWeaponByItemMeta(itemMeta: ItemMeta): WeaponParam.Weapon? {
        val container = itemMeta.persistentDataContainer
        val key = NamespacedKey(plugin, "weaponId")
        val weaponId = container.get(key, PersistentDataType.STRING) ?: return null
        return weapons.find { it.detail.id == weaponId }
    }
    fun weaponNameGen(itemName: String, amount: Int) = Colour.text("""${itemName}&r «${amount}»""")
}