package com.androidkotlin.armoireapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import com.androidkotlin.construct.construct_create_article
import com.androidkotlin.construct.construct_create_mark
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activitycreatemark.*

class CreateMarkActivity : AppCompatActivity() {
    private  lateinit var dbRereference: DatabaseReference
    private  lateinit var dataBase: FirebaseDatabase
    private  lateinit var auth: FirebaseAuth
    private lateinit var buttoncreatemark : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activitycreatemark)
        dataBase= FirebaseDatabase.getInstance()
        dbRereference=dataBase.reference.child("Marks")
        buttoncreatemark = findViewById(R.id.buttoncreatemarks)
        CreateMark_Click()
    }
    private fun CreateMark_Click() {
        buttoncreatemark.setOnClickListener() {
            val name = findViewById<TextInputEditText>(R.id.name_mark).text.toString()
            val create_marks =
                construct_create_mark(name)
            dbRereference.child("").push().setValue(create_marks)
            startActivity(Intent(this,NavigatorActivity::class.java))
        }

    }
}