package navy.warspite.minecraft.redshot

import navy.warspite.minecraft.redshot.event.Ammo
import navy.warspite.minecraft.redshot.util.GetColoured.colouredText
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

object GenerateWeapon {
    private val plugin = Main.instance
    fun itemStack(key: String): ItemStack? {
        val weapon = LoadWeapons.weaponJson[key] ?: return null
        val itemInformation = weapon.details

        val weaponId = NamespacedKey(plugin, "weaponId")
        val ammo = NamespacedKey(plugin, "ammo")

        val itemStack = Material.getMaterial(itemInformation.type)?.let { ItemStack(it) }

        val itemMeta = itemStack?.itemMeta ?: return null

        itemMeta.persistentDataContainer.set(weaponId, PersistentDataType.STRING, key)
        itemMeta.persistentDataContainer.set(ammo, PersistentDataType.INTEGER, weapon.reload.reloadAmount)
        itemMeta.setDisplayName(Ammo.getItemName(itemInformation.name, weapon.reload.reloadAmount))

        val lore = arrayListOf<String>()
        itemInformation.lore.forEach { lore.add(colouredText(it)) }
        itemMeta.lore = lore

        itemStack.itemMeta = itemMeta

        return itemStack
    }

    fun sounds(key: String): ArrayList<String>? {
        val weapon = LoadWeapons.weaponJson[key] ?: return null
        return weapon.details.acquiredSounds
    }
}