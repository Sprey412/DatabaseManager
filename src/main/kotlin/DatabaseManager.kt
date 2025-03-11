import java.io.File
import com.opencsv.CSVReader
import com.opencsv.CSVWriter
import java.io.FileReader
import java.io.FileWriter

class DatabaseManager(private val dbFile: File) {

    init {
        if (!dbFile.exists()) dbFile.createNewFile()
    }

    fun addRecord(id: String, name: String, age: String, phone: String) {
        val writer = CSVWriter(FileWriter(dbFile, true))
        writer.writeNext(arrayOf(id, name, age, phone))
        writer.close()
        println("✅ Запись добавлена: [$id, $name, $age, $phone]")
    }

    fun deleteRecord(id: String) {
        val tempFile = File("temp.csv")
        val reader = CSVReader(FileReader(dbFile))
        val writer = CSVWriter(FileWriter(tempFile))

        var deleted = false
        reader.forEach { row ->
            if (row[0] != id) writer.writeNext(row) else deleted = true
        }

        reader.close()
        writer.close()
        dbFile.delete()
        tempFile.renameTo(dbFile)

        if (deleted) println("✅ Запись с ID $id удалена.") else println("❌ Запись не найдена.")
    }

    fun searchRecords(query: String) {
        val reader = CSVReader(FileReader(dbFile))
        val results = reader.filter { row -> row.any { it.contains(query, ignoreCase = true) } }
        reader.close()

        if (results.isNotEmpty()) {
            println("🔍 Найденные записи:")
            results.forEach { println(it.joinToString(", ")) }
        } else {
            println("❌ Ничего не найдено.")
        }
    }

    fun editRecord(id: String, newName: String, newAge: String, newPhone: String) {
        val tempFile = File("temp.csv")
        val reader = CSVReader(FileReader(dbFile))
        val writer = CSVWriter(FileWriter(tempFile))

        var updated = false
        reader.forEach { row ->
            if (row[0] == id) {
                writer.writeNext(arrayOf(id, newName, newAge, newPhone))
                updated = true
            } else {
                writer.writeNext(row)
            }
        }

        reader.close()
        writer.close()
        dbFile.delete()
        tempFile.renameTo(dbFile)

        if (updated) println("✅ Запись обновлена.") else println("❌ Запись не найдена.")
    }

    fun sortRecordsByField(fieldIndex: Int) {
        val reader = CSVReader(FileReader(dbFile))
        val records = reader.toList().sortedBy { it[fieldIndex] }
        reader.close()

        val writer = CSVWriter(FileWriter(dbFile))
        writer.writeAll(records)
        writer.close()

        println("✅ Данные отсортированы.")
    }
}
