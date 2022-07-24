package navy.warspite.redshot

import navy.warspite.redshot.Main.Companion.plugin
import navy.warspite.redshot.Main.Companion.weapons
import navy.warspite.redshot.utils.Colour
import navy.warspite.redshot.controller.ItemMetaController.weaponNameGen
import navy.warspite.redshot.utils.Sound
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

class GenerateWeapon {
    private fun generateWeaponItemStack(id: String): ItemStack? {
        val weapon = weapons.find { it.detail.id == id } ?: return null
        val itemStack = Material.getMaterial(weapon.detail.type)?.let { ItemStack(it) }
        val itemMeta = itemStack?.itemMeta ?: return null

        val weaponId = NamespacedKey(plugin, "weaponId")
        val ammo = NamespacedKey(plugin, "ammo")

        itemMeta.persistentDataContainer.set(weaponId, PersistentDataType.STRING, weapon.detail.id)
        itemMeta.persistentDataContainer.set(ammo, PersistentDataType.INTEGER, weapon.reload.reloadAmount)

        itemMeta.setDisplayName(weaponNameGen(weapon.detail.name, weapon.reload.reloadAmount))

        itemMeta.lore = weapon.detail.lore.map { Colour.text(it) }

        itemMeta.isUnbreakable = true

        itemStack.itemMeta = itemMeta
        return itemStack
    }

    private fun sounds(id: String): List<String> {
        val weapon = weapons.find { it.detail.id == id } ?: return listOf()
        return weapon.detail.acquiredSounds
    }

    fun addInventoryWeapon(player: Player, id: String): Boolean {
        val weaponItemStack = generateWeaponItemStack(id) ?: return false
        player.inventory.addItem(weaponItemStack)
        Sound.playSoundByList(sounds(id), player)
        return true
    }
}
