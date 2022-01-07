
class User

class PaymentDetails

interface UserRepository {
    fun exists(user: User): Boolean
}

interface PaymentGateway {
    fun pay(paymentDetails: PaymentDetails): Boolean
}

public class PaymentService(val userRepository: UserRepository, val paymentGateway: PaymentGateway) {
	
	fun processPayment(user: User, paymentDetails: PaymentDetails): Boolean {
		if (userRepository.exists(user)) return paymentGateway.pay(paymentDetails)
		
		throw Exception("User not found")
	}
}