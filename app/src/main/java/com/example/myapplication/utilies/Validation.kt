package com.example.catapplication.utilies

import android.util.Patterns
import java.util.regex.Pattern

object Validation {

    /**
     * Use this method to validate the email.
     *
     * @param email the String that you want to validate.
     * @return true if its correct email, false otherwise.
     */
    private fun isEmailValid(email: String): Boolean {
        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }

    fun validateEmail(emailEt: String): Boolean {
        var isValid = false
        if (emailEt.isNotEmpty()) {
            isValid = isEmailValid(emailEt.trim())

        }
        return isValid
    }

    fun validate(passwordEt: String): Boolean {
        var isValid = true
        if (passwordEt.isEmpty() && passwordEt.length < 6) {
            isValid = false

        }
        return isValid
    }

    /**
     * Use this method to validate that confirmPassword matches password.
     *
     * @param pass the password string.
     * @param confirmPass the confirm password string.
     * @return true if confirm password matches the password, false otherwise.
     */
    fun isPasswordsMatches(pass: String, confirmPass: String) = pass == confirmPass

    /**
     * use this method to validate the phone number
     * @param phone String the phone number to be validated
     * @return Boolean tru if phone is valid, false otherwise
     */
    fun isValidPhone(phone: String) = Patterns.PHONE.matcher(phone.trim { it <= ' ' }).matches()


    /**
     * use this method to validate that userName contains only numbers, letters or _
     * @param userName String the userName to be validate
     * @return Boolean tru if userName is valid
     */
    fun isValidUserName(userName: String) = userName.matches("[a-zA-Z0-9_]*".toRegex())


}