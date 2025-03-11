import java.io.File

fun main() {
    val dbFile = File("database.csv")
    val dbManager = DatabaseManager(dbFile)
    val backupManager = BackupManager(dbFile, File("backups"))

    while (true) {
        println("\n📂 Выберите действие:")
        println("1 - Добавить запись")
        println("2 - Удалить запись")
        println("3 - Найти запись")
        println("4 - Редактировать запись")
        println("5 - Отсортировать записи")
        println("6 - Создать резервную копию")
        println("7 - Восстановить резервную копию")
        println("0 - Выход")

        when (readln().trim()) {
            "1" -> {
                println("Введите ID, Имя, Возраст, Телефон через запятую:")
                val (id, name, age, phone) = readln().split(",").map { it.trim() }
                dbManager.addRecord(id, name, age, phone)
            }
            "2" -> {
                println("Введите ID записи для удаления:")
                val id = readln().trim()
                dbManager.deleteRecord(id)
            }
            "3" -> {
                println("Введите строку для поиска:")
                val query = readln().trim()
                dbManager.searchRecords(query)
            }
            "4" -> {
                println("Введите ID записи и новые данные (Имя, Возраст, Телефон):")
                val (id, name, age, phone) = readln().split(",").map { it.trim() }
                dbManager.editRecord(id, name, age, phone)
            }
            "5" -> {
                println("Введите номер поля для сортировки (1 - Имя, 2 - Возраст, 3 - Телефон):")
                val index = readln().trim().toIntOrNull()?.minus(1)
                if (index in 1..3) dbManager.sortRecordsByField(index!!) else println("❌ Некорректный номер поля.")
            }
            "6" -> backupManager.createBackup()
            "7" -> {
                println("Введите путь к файлу резервной копии:")
                val backupPath = readln().trim()
                backupManager.restoreBackup(File(backupPath))
            }
            "0" -> {
                println("👋 Выход из программы.")
                return
            }
            else -> println("⚠ Некорректный ввод, попробуйте снова.")
        }
    }
}
