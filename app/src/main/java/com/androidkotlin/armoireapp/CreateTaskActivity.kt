package com.androidkotlin.armoireapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.androidkotlin.construct.DataPickerFragment
import com.androidkotlin.construct.construct_create_recipe
import com.androidkotlin.construct.construct_create_task
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activitycreatetask.*

class CreateTaskActivity : AppCompatActivity() {
    private  lateinit var dataBase: FirebaseDatabase
    private lateinit var  dbReference: DatabaseReference
    private  lateinit var auth: FirebaseAuth
    private lateinit var recipeArrayList : MutableList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activitycreatetask)

        dataBase= FirebaseDatabase.getInstance()
        recipeArrayList = mutableListOf()
        ContextMainType()
        CreateArticles_Click()
        date_task_recipe.setOnClickListener{
            showDatePickerDialog()
        }
    }
    private fun CreateArticles_Click() {
        buttoncreatetask.setOnClickListener() {

        val type = findViewById<AutoCompleteTextView>(R.id.list_type_task).text.toString()
        val name = findViewById<AutoCompleteTextView>(R.id.list_recipes_task).text.toString()
        val date = findViewById<TextInputEditText>(R.id.date_task_recipe).text.toString()
            val dbReference = dataBase.reference.child("Task")
            val create_task =
                construct_create_task(name, type, date)
                dbReference.push().setValue(create_task)
                startActivity(Intent(this,NavigatorActivity::class.java))
            }

        }
    private fun showDatePickerDialog() {
        val datePicker = DataPickerFragment{ day, month, year -> onDateSelected(day, month, year) }
        datePicker.show(supportFragmentManager, "datePicker")
    }

    private fun onDateSelected(day: Int, month: Int, year: Int) {
        date_task_recipe.setText("$day/$month/$year")
    }
    private fun ContextMainType(){
        val listPopupWindowButton = findViewById<TextInputLayout>(R.id.list_popup_button_recipe_type)
        val listPopupWindow = ListPopupWindow(this, null, R.attr.listPopupWindowStyle)
        // Set button as the list popup's anchor
        listPopupWindow.anchorView= listPopupWindowButton
        val items = listOf("Desayuno", "Almuerzo", "Cena")
        val adapter = ArrayAdapter(this, R.layout.list_item, items)
        (listPopupWindowButton.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        ContextMainRecipe()
    }
    private fun ContextMainRecipe(){
        val listPopupWindowButton = findViewById<TextInputLayout>(R.id.list_popup_button_recipe_task)
        val listPopupWindow = ListPopupWindow(this, null, R.attr.listPopupWindowStyle)
        // Set button as the list popup's anchor
        listPopupWindow.anchorView = listPopupWindowButton
        // Set list popup's content
        listPopupWindow.anchorView= listPopupWindowButton

        recipeArrayList = mutableListOf()
        getRecipeData()
        val adapter =ArrayAdapter(this, R.layout.list_item,recipeArrayList)
        (listPopupWindowButton.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }
    private fun getRecipeData() {

               dbReference = FirebaseDatabase.getInstance().getReference("Recipes_breakfast")
                dbReference.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            for (SnapShot in snapshot.children) {
                                val breackfast = SnapShot.child("name_recipe").value as? String
                                recipeArrayList.add(breackfast!!)
                            }
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })

           dbReference = FirebaseDatabase.getInstance().getReference("Recipes_lunch")
            dbReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (SnapShot in snapshot.children) {
                            val lunch = SnapShot.child("name_recipe").value as? String
                            recipeArrayList.add(lunch!!)
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
           dbReference = FirebaseDatabase.getInstance().getReference("Recipes_dinner")
            dbReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (SnapShot in snapshot.children) {
                            val dinner = SnapShot.child("name_recipe").value as? String
                            recipeArrayList.add(dinner!!)
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }
}