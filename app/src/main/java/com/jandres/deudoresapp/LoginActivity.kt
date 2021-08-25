package com.jandres.deudoresapp

import android.app.Activity
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import com.jandres.deudoresapp.data.dao.UsersDao
import com.jandres.deudoresapp.data.entities.Users
import com.jandres.deudoresapp.databinding.ActivityLoginBinding
import com.jandres.deudoresapp.utils.*

class LoginActivity : AppCompatActivity() {

    private lateinit var loginBinding: ActivityLoginBinding
    private var email: String = ""
    private var password: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)


        val dataImported = intent.extras
        email = dataImported?.getString("email").toString()
        password = dataImported?.getString("password").toString()
        if(email != "null") loginBinding.emailInputEditText.setText(email)

        bindOnChangeListeners()
        loginBinding.loginButton.setOnClickListener {
            validateOnClick()
        }

        loginBinding.registerTextView.setOnClickListener {
            goToRegisterActivity()
        }

    }

    private fun goToMainActivity(email : String, password : String){
        val intent = Intent(this,MainActivity::class.java)
        intent.putExtra("email",email)
        intent.putExtra("password",password)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK)
        startActivityForResult(intent,1235)
    }

    private fun goToRegisterActivity(){
        val intent = Intent(this,RegisterActivity::class.java)
        startActivityForResult(intent,1234)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1234 && resultCode == Activity.RESULT_OK){
            loginBinding.emailTextInputLayout.error = null
            loginBinding.passwordTextInputLayout.error = null

            loginBinding.passwordInputEditText.setText("")

            email = data!!.extras?.getString("email").toString()
            password = data!!.extras?.getString("password").toString()

            Toast.makeText(this,"Result 1",2.toInt()).show()

        }
    }

    private fun bindOnChangeListeners(){
        with(loginBinding) {
            emailInputEditText.doAfterTextChanged {
                //validateEmail()
                validateFields()
            }
            passwordInputEditText.doAfterTextChanged {
                //validatePassword()
                validateFields()
            }
        }
    }

    private fun validateOnClick(){
        val emailAux : String = loginBinding.emailInputEditText.text.toString()
        val passwordAux : String = loginBinding.passwordInputEditText.text.toString()



        if(validateUser(emailAux, passwordAux)){
            loginBinding.emailTextInputLayout.error = null
            loginBinding.passwordTextInputLayout.error = null
            goToMainActivity(email,password)
        }
        else{
            Toast.makeText(this,R.string.wrong_password,2.toInt()).show()

        }
    }

    private fun validateUser(email: String, password: String) : Boolean{
        val userDao : UsersDao = DeudoresApp.database.UsersDao()
        val user : Users = userDao.searchUsers(email)

        return user != null && user.email == email && user.password == password
    }

    private fun validateEmail() {
        val emailAux = loginBinding.emailInputEditText.text.toString()
        with(loginBinding){
            emailTextInputLayout.error = if (equal(emailAux,email )) null else EMAIL_DONT_MATCH
        }
    }

    private fun validatePassword() {
        with(loginBinding){
            val minPasswordSize : Boolean =  minTextSizeValidator(passwordInputEditText.text.toString(), MIN_SIZE_PASSWORD)
            passwordTextInputLayout.error = if (minPasswordSize) null  else PASSWORD_UNLESS
        }
    }

    private fun validateFields() {
        with(loginBinding){
            val fields = listOf<Boolean>(
                    minTextSizeValidator(passwordInputEditText.text.toString(), MIN_SIZE_PASSWORD)
            )
            for (valid in fields) {
                if(!valid) {
                    loginButton.isEnabled = false
                    return
                }
            }
            loginButton.isEnabled = true
        }
    }

    private fun checkUser(email2 : String) : Boolean{
        return if (email2.isNotEmpty() && email.isNotEmpty()){
            email == email2
        }
        else false
    }
    private fun checkPassword(password2 : String) : Boolean {
        return if (password2.isNotEmpty() && password.isNotEmpty()){
            password == password2
        }
        else false
    }
}