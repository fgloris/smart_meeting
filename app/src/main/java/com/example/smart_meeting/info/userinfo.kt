import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import qrcode.QRCode
import qrcode.color.Colors
import qrcode.render.QRCodeGraphics
import java.nio.ByteBuffer

class UserInfoViewModel: ViewModel(){
    val firstName by mutableStateOf("")
    val lastName by mutableStateOf("")
    val profile by mutableStateOf(ByteBuffer.allocate(10000))
    var age by mutableStateOf(0)
    val phoneNumber by mutableStateOf(PhoneNumber(86,0))
    val email by mutableStateOf("")
    val department by mutableStateOf("")
    val position by mutableStateOf("")

    private fun getUserCode(): String {
        return ""
    }
    fun encode(): QRCodeGraphics {
        val code = getUserCode()
        val raw = "first:$firstName;last:$lastName;code:$code";
        val qrcode = QRCode.ofSquares()
            .withColor(Colors.DEEP_SKY_BLUE)
            .withSize(100)
            .withLogo(profile.array(), 100, 100, true)
            .build(raw)
            .render()
        return qrcode
    }
}

class PhoneNumber(
    val countryCode: Int,
    val number: Int
){
    override fun toString(): String {
        return "+$countryCode $number";
    }
}
