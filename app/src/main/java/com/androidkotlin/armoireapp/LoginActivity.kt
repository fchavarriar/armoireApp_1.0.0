package com.androidkotlin.armoireapp

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.androidkotlin.models.User
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*


enum class ProviderType{
    GOOGLE
}
class LoginActivity : AppCompatActivity() {
    private lateinit var fragment: LivingFragment
    private  lateinit var txtEmail: TextInputEditText
    private  lateinit var txtPass: TextInputEditText
    private  lateinit var progressBar: ProgressBar
    private  lateinit var registerUser: Button
    private  lateinit var LoginUser: Button
    private  lateinit var forgerUser: CheckedTextView
    private  lateinit var auth: FirebaseAuth
    private lateinit var dbReference: DatabaseReference
    private lateinit var user:String
    private lateinit var password:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activitylogin)

        txtEmail =findViewById(R.id.Email)
        txtPass =findViewById(R.id.Password)
        progressBar = findViewById(R.id.progressBar)
        auth = FirebaseAuth.getInstance()
        LoginUser = findViewById(R.id.LoginUser)
        forgerUser = findViewById(R.id.forget)
        registerUser = findViewById(R.id.register)

        registerUser.setOnClickListener{
            startActivity(Intent(this,RegisterActivity::class.java))
        }

        forgerUser.setOnClickListener{
            startActivity(Intent(this,ForgetActivity::class.java))
        }
        LoginUser.setOnClickListener{

            loginUser()
        }


    }

    private fun loginUser(){
        user=txtEmail.text.toString()
        password=txtPass.text.toString()

        if(!TextUtils.isEmpty(user)&&!TextUtils.isEmpty(password)){
            progressBar.visibility=android.view.View.VISIBLE
            auth.signInWithEmailAndPassword(user,password)
                .addOnCompleteListener(this){
                        task ->
                    if(task.isSuccessful){
                        action()
                    }
                    else{
                        Toast.makeText(this,"ERROR: el correo o la clave son incorrectos", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }


    private fun action(){

        startActivity(Intent(this,NavigatorActivity::class.java))
    }
}


