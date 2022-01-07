import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse
import io.mockk.mockk
import io.mockk.every
import io.mockk.verify

class TestPaymentService {

    private val userRepository: UserRepository = mockk<UserRepository>()
    val paymentGateway = mockk<PaymentGateway>()
    val paymentService = PaymentService(userRepository, paymentGateway)
    private val user: User = User()
    
    @Test
    fun paymentServiceThrowsExceptionForNotExistingUser() {
        //Given
        every { userRepository.exists(any()) } returns false

        //Then
        assertThrows<Exception>({ paymentService.processPayment(user, PaymentDetails()) })
        verify(exactly = 1) {
            userRepository.exists(any())
        }
    }

    @Test
    fun paymentServiceProcessPaymentForExistingUser() {
        //Given
        every { userRepository.exists(any()) } returns true
        every { paymentGateway.pay(any()) } returns true

        //Then
        assertTrue(paymentService.processPayment(user, PaymentDetails()))
        verify(exactly = 1) {
            paymentGateway.pay(any())
        }
    }

    @Test
    fun paymentServiceThrowsAnError() {
        //Given
        every { userRepository.exists(any()) } returns true
        every { paymentGateway.pay(any()) } throw Exception("Unavailable")

        //Then
        assertFalse(paymentService.processPayment(user, PaymentDetails()))
    }
}