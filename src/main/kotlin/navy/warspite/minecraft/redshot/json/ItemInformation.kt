package navy.warspite.minecraft.redshot.json

data class ItemInformation (
    val itemName: String,
    val itemType: String,
    val itemLore: ArrayList<String>? = null,
    val inventoryControl: ArrayList<String>? = null,
    val soundsAcquired: ArrayList<String>? = null
)