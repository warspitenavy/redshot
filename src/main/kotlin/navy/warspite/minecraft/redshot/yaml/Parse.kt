package navy.warspite.minecraft.redshot.yaml

import kotlinx.serialization.Serializable

object Parse {
    @Serializable
    data class ID (
        val id: String
    )

    @Serializable
    data class ItemInformation (
        val itemName: String,
        val itemType: String,
        val itemLore: Unit?,
        val inventoryControl: ArrayList<String>?,
        val soundsAcquired: ArrayList<String>?
    )

    @Serializable
    data class Shooting (
        val delayBetweenShots: Int?,
        val projectileAmount: Int? = 0,
        val projectileDamage: Int? = 0,
        val projectileType: String,
        val bulletSpread: Double? = 0.0,
        val noRecoil: Boolean? = false,
        val recoilX: Double? = 0.0,
        val recoilY: Double? = 0.0,
        val shootSounds: ArrayList<String>?,
    )

    @Serializable
    data class Sneak (
        val noRecoil: Boolean?,
        val recoilX: Double,
        val recoilY: Double,
        val bulletSpread: Double
    )

    @Serializable
    data class BurstFire (
        val shotPerBurst: Int,
        val reloadDuration: Int,
        val outOfAmmoSounds: ArrayList<String>,
        val reloadingSounds: ArrayList<String>
    )

    @Serializable
    data class Scope (
        val zoomAmount: Int,
        val bulletSpread: Double,
        val toggleZoomSounds: ArrayList<String>
    )
}