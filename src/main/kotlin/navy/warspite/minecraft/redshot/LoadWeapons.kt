package navy.warspite.minecraft.redshot

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

object LoadWeapons {
    val plugin = Main.instance
    val weaponJson = linkedMapOf<String, WeaponParam.Parameters>()

    fun load() {
        val jsons = loadJsonObjects()
        weaponJson.clear()
        weaponJson.putAll(jsons)

        Main.instance.logger.info("Loaded Weapons (Json): ${jsons.keys}")
    }

    private fun loadJsonObjects(): LinkedHashMap<String, WeaponParam.Parameters> {
        val jsons = linkedMapOf<String, WeaponParam.Parameters>()
        for (f in loadJsonFiles()) {
            val file = f.toFile().readText()
            val data = Json.decodeFromString<List<WeaponParam.Weapon>>(file)
            for (d in data) {
                jsons[d.id] = d.parameters
            }
        }
        return jsons
    }

    private fun loadJsonFiles(): ArrayList<Path> {
        val dir = Paths.get("./plugins/${plugin.name}/Weapons")
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