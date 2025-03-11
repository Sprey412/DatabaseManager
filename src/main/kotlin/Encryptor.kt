import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

object Encryptor {
    private const val SECRET_KEY = "1234567812345678" // 16 символов
    private val key = SecretKeySpec(SECRET_KEY.toByteArray(), "AES")

    fun encrypt(text: String): String {
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.ENCRYPT_MODE, key)
        return Base64.getEncoder().encodeToString(cipher.doFinal(text.toByteArray()))
    }

    fun decrypt(text: String): String {
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.DECRYPT_MODE, key)
        return String(cipher.doFinal(Base64.getDecoder().decode(text)))
    }
}
