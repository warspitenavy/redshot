package navy.warspite.minecraft.redshot.json

data class Shooting (
    val projectileAmount: Int,
    val projectileDamage: Int,
    val projectileSpeed: Int,
    val projectileType: String,
    val bulletSpread: Double,
    val recoilX: Double? = null,
    val recoilY: Double? = null,
    val shootSounds: ArrayList<String>? = null
)