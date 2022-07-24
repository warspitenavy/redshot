package navy.warspite.redshot

import com.charleskorn.kaml.Yaml
import kotlinx.serialization.decodeFromString
import navy.warspite.redshot.Main.Companion.plugin
import navy.warspite.redshot.Main.Companion.weapons
import navy.warspite.redshot.params.WeaponParam
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.isDirectory
import kotlin.io.path.isRegularFile

object LoadWeapons {
    /**
     * リロード用の関数
     */
    fun reloadWeapons() {
        weapons.clear()
        weapons.addAll(loadWeaponsAsYaml().toMutableList())
    }

    /**
     * 武器のファイルをTOMLで読み込む
     * @return 武器のTOML
     */
    fun loadWeaponsAsYaml(): List<WeaponParam.Weapon> {
        val file = loadWeaponFiles()
        return file
            .map { it.toFile().readText() }
            .map { Yaml.default.decodeFromString(it) }
    }

    /**
     * ディレクトリから武器のファイル一覧を取得
     * @return ファイル一覧
     */
    private fun loadWeaponFiles(): List<Path> {
        val directory = Paths.get(plugin.dataFolder.absolutePath + "/weapons")
        val extensions = listOf(".yml", ".yaml")
        /**
         * ディレクトリが存在しない場合は作成
         */
        if (!Files.isDirectory(directory)) Files.createDirectory(directory)
        if (Files.notExists(directory)) Files.createDirectory(directory)
        /**
         * ファイルのリストを取得
         */
        return Files.list(directory)
            .filter {
                extensions.any { e -> "${it.fileName}".endsWith(e) }
            }
            .filter { it.isRegularFile() }
            .filter { !it.isDirectory() }
            .toList()
    }
}