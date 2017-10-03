package demo

//data class Visitor(
//        val id: String?,
//        val name: String?,
//        val lastName: String?,
//        val email: String?,
//        val isEmailVerified: Boolean,
//        val isAnonymous: Boolean
//)

sealed class Visitor {

    data class VerifiedUser(
        val id: String,
        val name: String,
        val lastName: String,
        val email: String
    ): Visitor()

    data class UnverifiedUser(
        val name: String,
        val lastName: String,
        val email: String
    ): Visitor()

    object Anonymous: Visitor()

}

fun sayHello(visitor: Visitor): String = when(visitor) {
    is Visitor.VerifiedUser -> "Hello ${visitor.name}"
    is Visitor.UnverifiedUser -> "Hello ${visitor.name}," +
            "we have sent a verification email to ${visitor.email}"
    is Visitor.Anonymous -> "Hello, would you like to register?"
}