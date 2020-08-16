package navy.warspite.minecraft.redshot

import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.java.JavaPlugin

object GenerateItemStack {
    private val plugin = Main.instance
    fun generate(key: String): Any {
        val weapon = LoadWeapons.weaponsHashMap[key] ?: return "Weapon is null."
        weapon as LinkedHashMap<*, *>

        val itemInformation = weapon["itemInformation"] ?: return "itemInformation is null."
        itemInformation as LinkedHashMap<*, *>

        val itemType = itemInformation["itemType"] ?: return "itemType is null"
        itemType as String

        val nameSpacedKey = NamespacedKey(plugin, "RedShotKey")

        val itemStack = Material.getMaterial(itemType)
            ?.let { ItemStack(it) } ?: return "itemType Error"

        itemStack.itemMeta?.persistentDataContainer
            ?.set(nameSpacedKey, PersistentDataType.STRING, key)

        return itemStack
    }
}