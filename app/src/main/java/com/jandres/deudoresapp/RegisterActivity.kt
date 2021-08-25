package com.jandres.deudoresapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.widget.doAfterTextChanged
import com.jandres.deudoresapp.data.dao.DebtorDao
import com.jandres.deudoresapp.data.dao.UsersDao
import com.jandres.deudoresapp.data.entities.Debtor
import com.jandres.deudoresapp.data.entities.Users
import com.jandres.deudoresapp.databinding.ActivityLoginBinding
import com.jandres.deudoresapp.databinding.ActivityRegisterBinding
import com.jandres.deudoresapp.utils.*
import java.sql.Types


class RegisterActivity : AppCompatActivity() {
    val PASSWORD_LENGTH : Int = 6
    private lateinit var registerBinding: ActivityRegisterBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(registerBinding.root)

        bindOnChangeListeners()
        registerBinding.registerButton.setOnClickListener {
            validateOnClick()
        }

    }

    private fun goBack(email: String, password: String) {
        val intent = Intent()
        intent.putExtra("email", email)
        intent.putExtra("password", password)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        setResult(Activity.RESULT_OK, intent)
        onBackPressed()
    }

    /*private fun exceptionsData(email: String, password: String, password2: String) {
        if (email.isBlank()) registerBinding.emailRegisterTextInputLayout.error = "Campo Requerido."
        else if (!validateEmail(email)) registerBinding.emailRegisterTextInputLayout.error =
            "Correo No Valido."
        else {
            registerBinding.emailRegisterTextInputLayout.error = null
            if (password.isBlank()) registerBinding.passwordRegisterTextInputLayout.error =
                "Campo Requerido."
            if (password2.isBlank()) registerBinding.password2RegisterTextInputLayout.error =
                "Campo Requerido."
            else {
                registerBinding.passwordRegisterTextInputLayout.error = null
                if (password != password2) registerBinding.password2RegisterTextInputLayout.error =
                    "Contraseñas No Coinciden."
                else {
                    registerBinding.passwordRegisterTextInputLayout.error = "Minimo 6 Caracteres."
                    registerBinding.password2RegisterTextInputLayout.error = "Minimo 6 Caracteres."
                }
            }

        }
    }*/

    private fun bindOnChangeListeners() {
        with(registerBinding) {
            emailTextEdit.doAfterTextChanged {
                validateEmail()
                validateFields()
            }
            passwordTextEdit.doAfterTextChanged {
                validatePassword()
                validateFields()
            }
            password2TextEdit.doAfterTextChanged {
                validateFields()
                validatePassword()
            }
        }
    }

    private fun validatePassword() {
        with(registerBinding) {
            val pass1Valid =
                minTextSizeValidator(passwordTextEdit.text.toString(), MIN_SIZE_PASSWORD)
            val pass2Valid =
                minTextSizeValidator(password2TextEdit.text.toString(), MIN_SIZE_PASSWORD)
            val passMatched =
                equal(passwordTextEdit.text.toString(), password2TextEdit.text.toString())

            passwordRegisterTextInputLayout.error = if (pass1Valid) null else PASSWORD_UNLESS

            if (!pass2Valid) password2RegisterTextInputLayout.error = PASSWORD_UNLESS
            else if (!passMatched) password2RegisterTextInputLayout.error = PASSWORD_DONT_MATCH
            else{
                password2RegisterTextInputLayout.error =  null
                password2RegisterTextInputLayout.error =  null
            }

        }
    }

    private fun validateFields() {
        with(registerBinding) {
            val pass1 = passwordTextEdit.text.toString()
            val pass2 = password2TextEdit.text.toString()
            val fields = listOf<Boolean>(
                minTextSizeValidator(pass1, MIN_SIZE_PASSWORD),
                minTextSizeValidator(pass2, MIN_SIZE_PASSWORD),
                emailValidator(emailTextEdit.text.toString()),
                equal(pass1, pass2)
            )
            for (valid in fields) {
                if (!valid) {
                    registerButton.isEnabled = false
                    return
                }
            }
            registerButton.isEnabled = true
        }
    }

    private fun validateEmail() {
        with(registerBinding) {
            val emailValid: Boolean = emailValidator(emailTextEdit.text.toString())
            emailRegisterTextInputLayout.error = if (emailValid) null else EMAIL_EXAMPLE
        }

    }

    private fun validateOnClick() {
        val email: String = registerBinding.emailTextEdit.text.toString()
        val password: String = registerBinding.passwordTextEdit.text.toString()
        val password2: String = registerBinding.password2TextEdit.text.toString()

        if (validateData(email, password, password2)) {
            if (!userExist(email)) {
                createUser(email,password)
                goBack(email, password)
            }
            else Toast.makeText(this,"USUARIO YA EXISTE", Toast.LENGTH_SHORT).show()
        } else {
            validateAllFields()
        }
    }

    private fun validateAllFields() {
        validateFields()
        validateEmail()
        validatePassword()
    }

    private fun userExist(email: String) : Boolean {
        val userDao : UsersDao = DeudoresApp.database.UsersDao()
        val user : Users = userDao.searchUsers(email)

        return user != null
    }

    private fun createUser(email: String, password: String) {
        val user = Users(id = Types.NULL, email = email, password = password)
        val userDao : UsersDao = DeudoresApp.database.UsersDao()

        userDao.createUser(user)
    }

    private fun validateData(email: String, password: String, password2: String): Boolean {
        return checkLengthPassword(
            password,
            PASSWORD_LENGTH
        ) && password == password2
    }


    private fun checkLengthPassword(password: String, length: Int): Boolean {
        return password.length >= length
    }


}