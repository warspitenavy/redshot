package navy.warspite.minecraft.redshot

import com.charleskorn.kaml.Yaml
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import navy.warspite.minecraft.redshot.util.GetColoured.coloured
import java.nio.file.Files
import java.nio.file.Paths

object ConfigFile {
    lateinit var config: ConfigParam.Config
    private val plugin = Main.instance
    private val dir = Paths.get("./plugins/${plugin.name}")
    private val configFile = dir.resolve("config.yml")

    fun load() {
        config = initialiseConfig()
        plugin.server.broadcast(coloured("Config load completed.", true), "redshot.admin")
    }

    fun save(_config: ConfigParam.Config) {
        val file = Yaml.default.encodeToString(_config).split('\n')
        Files.write(configFile, file)
        load()
    }

    private fun initialiseConfig(): ConfigParam.Config {
        // プラグインディレクトリ生成
        if (!Files.isDirectory(dir) || Files.notExists(dir)) Files.createDirectory(dir)
        // config生成
        if (Files.notExists(configFile)) {
            val file = Yaml.default.encodeToString(ConfigParam.Config()).split('\n')
            Files.write(configFile, file)
        }
        val file = configFile.toFile().readText()
        return Yaml.default.decodeFromString(file)
    }
}