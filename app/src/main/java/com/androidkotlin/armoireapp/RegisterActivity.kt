package com.androidkotlin.armoireapp

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {
    private  lateinit var txtName: TextInputEditText
    private  lateinit var txtLastname: TextInputEditText
    private  lateinit var txtEmail: TextInputEditText
    private  lateinit var txtPass: TextInputEditText
    private  lateinit var txtPassConfirmation: TextInputEditText
    private  lateinit var progressBar: ProgressBar
    private  lateinit var dbRereference: DatabaseReference
    private  lateinit var dataBase: FirebaseDatabase
    private  lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activityregister)

        txtName=findViewById(R.id.Name)
        txtLastname=findViewById(R.id.Lastname)
        txtEmail=findViewById(R.id.Email)
        txtPass=findViewById(R.id.Password)
        txtPassConfirmation=findViewById(R.id.Password)
        progressBar = findViewById(R.id.progressBar)
        dataBase= FirebaseDatabase.getInstance()
        auth= FirebaseAuth.getInstance()
        dbRereference=dataBase.reference.child("User")
    }
    fun register(view: View){
        createNewAccount()
    }
    private fun validateNewAccount (): Boolean {
        val Password:String=txtPass.text.toString()
        val PasswordConfirmation:String=txtPassConfirmation.text.toString()
        if(TextUtils.isEmpty(PasswordConfirmation)){
            Toast.makeText(this,"Por favor confirmar la contraseña", Toast.LENGTH_LONG).show()
            return false
        }
        if(Password.toString() != PasswordConfirmation.toString()){
            Toast.makeText(this,"las contraseñas no coinciden", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }
    private fun createNewAccount(){
        val Name:String=txtName.text.toString()
        val Lastname:String=txtLastname.text.toString()
        val Email:String=txtEmail.text.toString()
        val Password:String=txtPass.text.toString()
        if (validateNewAccount() == true) {
            if (!TextUtils.isEmpty(Name) &&
                !TextUtils.isEmpty(Lastname) &&
                !TextUtils.isEmpty(Email) &&
                !TextUtils.isEmpty(Password)
            )
                progressBar.visibility = View.VISIBLE
            auth.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(this) { task ->
                    if (task.isComplete) {
                        val user: FirebaseUser? = auth.currentUser
                        verifyEmail(user)
                        val userBD = dbRereference.child(user?.uid.toString())
                        userBD.child("Id").setValue(user?.uid.toString())
                        userBD.child("Email").setValue(Email)
                        userBD.child("Name").setValue(Name)
                        userBD.child("Lastname").setValue(Lastname)
                        action()
                    }
                }
        }
    }
    private fun action(){
        startActivity(Intent(this,LoginActivity::class.java))
    }
    private fun verifyEmail(user: FirebaseUser?){
        user?.sendEmailVerification()
            ?.addOnCompleteListener(this){
                    task->
                if(task.isComplete){
                    Toast.makeText(this,"El correo se a enviado satisfactoriamente", Toast.LENGTH_LONG).show()
                }else {
                    Toast.makeText(this,"ERROR: no se completo la solicitud", Toast.LENGTH_LONG).show()
                }
            }
    }
}