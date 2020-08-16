package navy.warspite.minecraft.redshot

import navy.warspite.minecraft.redshot.util.GetColoured
import navy.warspite.minecraft.redshot.util.GetColoured.getColoured
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.java.JavaPlugin

object GenerateItemStack {
    private val plugin = Main.instance
    fun generate(key: String): Any {
        val weapon = LoadWeapons.weaponsHashMap[key] ?: return getColoured("Weapon do not exist.")
        weapon as LinkedHashMap<*, *>

        val itemInformation = weapon["itemInformation"] ?: return getColoured("\"itemInformation\" is null.")
        itemInformation as LinkedHashMap<*, *>

        val itemType = itemInformation["itemType"] ?: return getColoured("\"itemType\" is null.")
        itemType as String

        val nameSpacedKey = NamespacedKey(plugin, "RedShotKey")

        val itemStack = Material.getMaterial(itemType)
            ?.let { ItemStack(it) } ?: return getColoured("\"itemType\" error")

        itemStack.itemMeta?.persistentDataContainer
            ?.set(nameSpacedKey, PersistentDataType.STRING, key)

        return itemStack
    }
}