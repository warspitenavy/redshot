package navy.warspite.minecraft.redshot

import navy.warspite.minecraft.redshot.util.GetColoured.colouredMessage
import navy.warspite.minecraft.redshot.util.GetColoured.colouredText
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

object GenerateItemStack {
    private val plugin = Main.instance
    fun generate(key: String): Any {
        val weapon = LoadWeapons.weaponsHashMap[key]
            ?: return colouredMessage("Weapon do not exist.")
        weapon as LinkedHashMap<*, *>

        val itemInformation = weapon["itemInformation"]
            ?: return colouredMessage("&cERROR:&r \"itemInformation\"")
        itemInformation as LinkedHashMap<*, *>

        val itemName = itemInformation["itemName"]
            ?: return colouredMessage("&cERROR&r: \"itemName\"")
        itemName as String

        val itemType = itemInformation["itemType"]
            ?: return colouredMessage("&cERROR&r: \"itemType\"")
        itemType as String

        val itemLore = arrayListOf<String>()
        val itemInfoItemLore = itemInformation["itemLore"] as ArrayList<*>?
        itemInfoItemLore?.forEach { itemLore.add(colouredText(it as String)) }

        val nameSpacedKey = NamespacedKey(plugin, "RedShotKey")

        val itemStack = Material.getMaterial(itemType)?.let { ItemStack(it) }
            ?: return colouredMessage("&cERROR&r: \"itemStack\"")

        val itemMeta = itemStack.itemMeta

        itemMeta?.persistentDataContainer
            ?.set(nameSpacedKey, PersistentDataType.STRING, key)

        itemMeta?.setDisplayName(colouredText(itemName)) //アイテム名

        itemMeta?.lore = itemLore //アイテムロール

        itemStack.itemMeta = itemMeta

        val soundsAcquired = itemInformation["soundsAcquired"]
        soundsAcquired as ArrayList<*>?

        return linkedMapOf(
            "itemStack" to itemStack,
            "soundsAcquired" to soundsAcquired
        )
    }
}