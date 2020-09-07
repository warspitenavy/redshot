package navy.warspite.minecraft.redshot

import navy.warspite.minecraft.redshot.util.GetColoured.colouredText
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

object GenerateWeapon {
    private val plugin = Main.instance
    fun itemStack(key: String): ItemStack? {
        val weapon = LoadFiles.weaponJson[key] ?: return null
        val itemInformation = weapon.itemInformation

        val weaponId = NamespacedKey(plugin, "weaponId")
        val ammo = NamespacedKey(plugin, "ammo")

        val itemStack = Material.getMaterial(itemInformation.itemType)?.let { ItemStack(it) }

        val itemMeta = itemStack?.itemMeta ?: return null

        itemMeta.persistentDataContainer.set(weaponId, PersistentDataType.STRING, key)
        itemMeta.persistentDataContainer.set(ammo, PersistentDataType.INTEGER, 0)

        itemMeta.setDisplayName(colouredText("""${itemInformation.itemName} □ «0»""")) //アイテム名

        val lore = arrayListOf<String>()
        itemInformation.itemLore.forEach { lore.add(colouredText(it)) }
        itemMeta.lore = lore

        itemStack.itemMeta = itemMeta

        return itemStack
    }
    fun sounds(key: String): ArrayList<String>? {
        val sounds = arrayListOf<String>()
        val weapon = LoadFiles.weaponJson[key] ?: return null
        weapon.itemInformation.soundsAcquired.forEach { sounds.add(it) }
        return sounds
    }
}