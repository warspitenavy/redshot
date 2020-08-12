package navy.warspite.minecraft.redshot.json

data class WeaponParameters (
    val itemInformation: ItemInformation,
    val shooting: Shooting? = null,
    val fullyAutomatic: FullyAutomatic? = null,
    val sneak: Sneak? = null,
    val scope: Scope? = null,
    val reload: Reload? = null
)