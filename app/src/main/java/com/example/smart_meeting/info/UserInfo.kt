import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import qrcode.QRCode
import qrcode.color.Colors
import qrcode.render.QRCodeGraphics
import java.nio.ByteBuffer

class UserInfoViewModel: ViewModel(){
    var reginstered by mutableStateOf(false)
    var firstName by mutableStateOf("游客")
    var lastName by mutableStateOf("")
    val profile by mutableStateOf(ByteBuffer.allocate(10000))
    var age by mutableStateOf(0)
    val phoneNumber by mutableStateOf(PhoneNumber(86,0))
    var email by mutableStateOf("")
    var department by mutableStateOf("")
    var position by mutableStateOf("")

    private fun getUserCode(): String {
        return ""
    }

    fun getFullName(): String {
        if ((firstName+lastName).any { char -> (char.code in 65..90) || (char.code in 97..122) }){
            return "$firstName $lastName"
        }else{
            return lastName+firstName
        }
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
    var countryCode: Int,
    var number: Long
){
    override fun toString(): String {
        return "+$countryCode $number";
    }
}
