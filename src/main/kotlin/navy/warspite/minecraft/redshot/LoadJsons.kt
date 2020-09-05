package navy.warspite.minecraft.redshot

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import navy.warspite.minecraft.redshot.parse.Parse
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

object LoadJsons {
    val weaponJson = linkedMapOf<String, Parse.Parameters>()

    fun generateWeapon() {
        val jsons = loadWeapon()
        weaponJson.clear()
        weaponJson.putAll(jsons)

        Main.instance.logger.info("Loaded Weapons (Json): ${jsons.keys}")
    }

    private fun loadWeapon(): LinkedHashMap<String, Parse.Parameters> {
        val jsons = linkedMapOf<String, Parse.Parameters>()
        for (f in loadWeaponFiles()) {
            val file = f.toFile().readText()
            val data = Json.decodeFromString<List<Parse.Weapon>>(file)
            for (d in data) {
                jsons[d.id] = d.parameters
            }
        }
        return jsons
    }

    private fun loadWeaponFiles(): ArrayList<Path> {
        val dir = Paths.get("./plugins/RedShot/Weapons")
        val fileExtension = ".json"
        if (!Files.isDirectory(dir)) Files.createDirectory(dir)
        if (Files.notExists(dir)) Files.createDirectory(dir)

        val weaponFiles = arrayListOf<Path>()

        Files.list(dir).forEach {
            if ("$it".endsWith(fileExtension)) weaponFiles.add(it)
        }
        return weaponFiles
    }
}