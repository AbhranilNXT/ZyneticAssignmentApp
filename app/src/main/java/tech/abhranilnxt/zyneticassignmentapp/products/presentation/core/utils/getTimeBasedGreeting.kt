package tech.abhranilnxt.zyneticassignmentapp.products.presentation.core.utils

fun getTimeBasedGreeting(): String {
    val hour = java.time.LocalTime.now().hour
    return when (hour) {
        in 5..11 -> "Good Morning,"
        in 12..16 -> "Good Afternoon,"
        else -> "Good Evening,"
    }
}