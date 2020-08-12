package navy.warspite.minecraft.redshot.json

data class Reload (
    val enable: Boolean? = true,
    val reloadAmount: Int,
    val reloadDuration: Int,
    val outOfAmmoSounds: ArrayList<String>? = null,
    val reloadingSounds: ArrayList<String>? = null
)