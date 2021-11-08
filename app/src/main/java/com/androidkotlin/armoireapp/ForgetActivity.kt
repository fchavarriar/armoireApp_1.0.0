package com.androidkotlin.armoireapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class ForgetActivity : AppCompatActivity() {

    private  lateinit var txtEmail: TextInputEditText
    private  lateinit var recoverUser: Button
    private  lateinit var progressBar: ProgressBar
    private  lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activityforget)

        txtEmail =findViewById(R.id.Email)
        recoverUser = findViewById(R.id.recover)
        progressBar = findViewById(R.id.progressBar)
        auth = FirebaseAuth.getInstance()
        recoverUser.setOnClickListener{
            send()
        }
    }
    fun send() {
        val email = txtEmail.text.toString()
        if(!TextUtils.isEmpty(email)){
            progressBar.visibility = android.view.View.VISIBLE
            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(this){
                        task ->
                    if(task.isSuccessful){
                        startActivity(Intent(this,LoginActivity::class.java))
                    }else{
                        Toast.makeText(this,"ERROR: no se completo correctamente el proceso", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }


}