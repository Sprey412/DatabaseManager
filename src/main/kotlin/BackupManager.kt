import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption

class BackupManager(private val dbFile: File, private val backupDir: File) {

    init {
        if (!backupDir.exists()) backupDir.mkdirs()
    }

    fun createBackup() {
        val backupFile = File(backupDir, "backup_${System.currentTimeMillis()}.csv")
        Files.copy(dbFile.toPath(), backupFile.toPath(), StandardCopyOption.REPLACE_EXISTING)
        println("✅ Резервная копия создана: ${backupFile.absolutePath}")
    }

    fun restoreBackup(backupFile: File) {
        Files.copy(backupFile.toPath(), dbFile.toPath(), StandardCopyOption.REPLACE_EXISTING)
        println("✅ Восстановлено из резервной копии: ${backupFile.absolutePath}")
    }
}
