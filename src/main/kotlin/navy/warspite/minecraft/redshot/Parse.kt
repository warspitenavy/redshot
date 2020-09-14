package navy.warspite.minecraft.redshot

import kotlinx.serialization.Serializable

object Parse {
    @Serializable
    data class Weapon(
        val id: String,
        val parameters: Parameters
    )

    @Serializable
    data class Parameters(
        val details: Details,
        val shooting: Shooting,
        val sneak: Sneak? = null,
        val burstFire: BurstFire? = null,
        val reload: Reload,
        val firearmAction: FirearmAction,
        val scope: Scope? = null
    )

    @Serializable
    data class Details(
        val name: String,
        val type: String,
        val lore: ArrayList<String> = arrayListOf(),
        val inventoryControl: ArrayList<String> = arrayListOf(),
        val acquiredSounds: ArrayList<String> = arrayListOf()
    )

    @Serializable
    data class Shooting(
        val delayBetweenShots: Int = 4,
        val projectileAmount: Int = 1,
        val projectileSpeed: Int = 20,
        val projectileDamage: Int = 0,
        val projectileType: String = "snowball",
        val bulletSpread: Double = 0.0,
        val recoil: Boolean = true,
        val recoilX: Double = 0.0,
        val recoilY: Double = 0.0,
        val shootSounds: ArrayList<String> = arrayListOf(),
    )

    @Serializable
    data class Sneak(
        val recoil: Boolean = true,
        val recoilX: Double = 0.0,
        val recoilY: Double = 0.0,
        val bulletSpread: Double = 0.0
    )

    @Serializable
    data class BurstFire(
        val shotsPerBurst: Int,
        val delayBetweenShotsInBurst: Int,
    )

    @Serializable
    data class Reload(
        val reloadAmount: Int = 0,
        val reloadDuration: Int = 0,
        val outOfAmmoSounds: ArrayList<String> = arrayListOf(),
        val reloadingSounds: ArrayList<String> = arrayListOf()
    )

    @Serializable
    data class FirearmAction(
        val type: String = "slide",
        val closeDuration: Int = 0,
        val closeShootDelay: Int = 0,
        val closeSounds: ArrayList<String> = arrayListOf()
    )

    @Serializable
    data class Scope(
        val sight: Boolean = false,
        val zoomAmount: Int = 0,
        val bulletSpread: Double = 0.0,
        val toggleZoomSounds: ArrayList<String> = arrayListOf()
    )
}