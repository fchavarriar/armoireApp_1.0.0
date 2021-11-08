package com.androidkotlin.armoireapp

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.View.OnTouchListener
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.androidkotlin.construct.construct_create_article
import com.androidkotlin.construct.construct_create_recipe
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activitycreaterecipes.*


class CreateRecipesActivity : AppCompatActivity() {
    //variable de requerimientos de permisos
    private val REQUEST_GALLERY = 1001
    private val REQUEST_CAMERA = 1002
    //variable que obtiene un objeto tipo bite
    var picture: Uri? = null
    var FileUri: Uri? = null
    //variable para subir fotos
    private val File = 1

    private  lateinit var dataBase: FirebaseDatabase
    private  lateinit var auth: FirebaseAuth
    private lateinit var articleArrayList : MutableList<String>
    private lateinit var main_articleArrayList : ArrayList<String>
    private lateinit var list: List<String>
    private lateinit var list_view_ing : ListView
    private lateinit var miScrollView: ScrollView

    val intentGallery = Intent(Intent.ACTION_PICK)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activitycreaterecipes)
        miScrollView = findViewById(R.id.scroll_article)
        list_view_ing = findViewById(R.id.list_view_ingredient)
        miScrollView.setOnTouchListener(OnTouchListener { v, event ->
            findViewById<View>(R.id.list_view_ingredient).parent
                .requestDisallowInterceptTouchEvent(false)
            false
        })
        list_view_ing.setOnTouchListener(OnTouchListener { v, event ->
            v.parent.requestDisallowInterceptTouchEvent(true)
            false
        })
        dataBase= FirebaseDatabase.getInstance()

        articleArrayList = mutableListOf()
        main_articleArrayList = arrayListOf()
        list = listOf()
        ContextMainIngredient()
        add_ingredientToList()
        ContextMainVolume()
        //funciones de galerria
        openGallery_Click()
        //funciones de camara
        openCamera_Click()
        CreateArticles_Click()
    }
    private fun CreateArticles_Click() {

        buttoncreaterecipes.setOnClickListener() {
            val name = findViewById<TextInputEditText>(R.id.name_recipe).text.toString()
            val type = findViewById<AutoCompleteTextView>(R.id.list_type_recipes).text.toString()
            val intruction = findViewById<MultiAutoCompleteTextView>(R.id.instructions_recipes).text.toString()
            val recipes = main_articleArrayList
            when (type){
                "Desayuno" -> {
                    val dbReference=dataBase.reference.child("Recipes_breakfast")
                    val Folder: StorageReference =
                        FirebaseStorage.getInstance().getReference().child("Recipes_breakfast")
                    val file_name: StorageReference = Folder.child("file" + FileUri!!.lastPathSegment)
                    file_name.putFile(FileUri!!).addOnSuccessListener { taskSnapshot ->
                        var hashMap:String

                        file_name.getDownloadUrl().addOnSuccessListener { uri ->
                            hashMap = java.lang.String.valueOf(uri)
                            val create_recipes =
                                construct_create_recipe(hashMap, name, type, intruction , recipes)
                            dbReference.push().setValue(create_recipes)
                            startActivity(Intent(this,NavigatorActivity::class.java))
                        }
                    }
                }
                "Almuerzo" -> {
                    val dbReference=dataBase.reference.child("Recipes_lunch")
                    val Folder: StorageReference =
                        FirebaseStorage.getInstance().getReference().child("Recipes_lunch")
                    val file_name: StorageReference = Folder.child("file" + FileUri!!.lastPathSegment)
                    file_name.putFile(FileUri!!).addOnSuccessListener { taskSnapshot ->
                        var hashMap:String

                        file_name.getDownloadUrl().addOnSuccessListener { uri ->
                            hashMap = java.lang.String.valueOf(uri)
                            val create_recipes =
                                construct_create_recipe(hashMap, name, type, intruction , recipes)
                            dbReference.push().setValue(create_recipes)
                            startActivity(Intent(this,NavigatorActivity::class.java))
                        }
                    }
                }
                "Cena" -> {
                    val dbReference=dataBase.reference.child("Recipes_dinner")
                    val Folder: StorageReference =
                        FirebaseStorage.getInstance().getReference().child("Recipes_dinner")
                    val file_name: StorageReference = Folder.child("file" + FileUri!!.lastPathSegment)
                    file_name.putFile(FileUri!!).addOnSuccessListener { taskSnapshot ->
                        var hashMap:String

                        file_name.getDownloadUrl().addOnSuccessListener { uri ->
                            hashMap = java.lang.String.valueOf(uri)
                            val create_recipes =
                                construct_create_recipe(hashMap, name, type, intruction , recipes)
                            dbReference.push().setValue(create_recipes)
                            startActivity(Intent(this,NavigatorActivity::class.java))
                        }

                    }
                }
                else -> {
                    Toast.makeText(applicationContext,"No se ha podido registrar la receta", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    private fun ContextMainIngredient(){
        val listPopupWindowButton = findViewById<TextInputLayout>(R.id.list_popup_button_ingredient)
        val listPopupWindow = ListPopupWindow(this, null, R.attr.listPopupWindowStyle)
        // Set button as the list popup's anchor
        listPopupWindow.anchorView = listPopupWindowButton
        // Set list popup's content
        listPopupWindow.anchorView= listPopupWindowButton
        articleArrayList = mutableListOf()
        getArticlesData()
        val adapter =ArrayAdapter(this, R.layout.list_item,articleArrayList)
        (listPopupWindowButton.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }
    //obtiene una consulta firebase donde se obtine el menu para la lista de articulos
    private fun getArticlesData() {
        val dbReference = FirebaseDatabase.getInstance().getReference("Articles")
        dbReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (SnapShot in snapshot.children){
                        val mark = SnapShot.child("name_article").value as? String
                        val unitv = SnapShot.child("volume_article").value as? String
                        val unitw = SnapShot.child("weight_article").value as? String
                        val unitvt = SnapShot.child("unit_volume_article").value as? String
                        val unitwt = SnapShot.child("unit_weight_article").value as? String
                        articleArrayList.add(mark+" "+unitv+unitw+unitvt+unitwt)
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    //menu para tipo de alimento
    private fun ContextMainVolume(){
        val listPopupWindowButton = findViewById<TextInputLayout>(R.id.list_popup_button_recipes)
        val listPopupWindow = ListPopupWindow(this, null, R.attr.listPopupWindowStyle)

        // Set button as the list popup's anchor
        listPopupWindow.anchorView= listPopupWindowButton
        val items = listOf("Desayuno", "Almuerzo", "Cena", "Otros")
        val adapter = ArrayAdapter(this, R.layout.list_item, items)
        (listPopupWindowButton.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }
    //funcion para agregar(inflar) la lista de recetas
    private fun add_ingredientToList(){
        val ingredient = findViewById<AutoCompleteTextView>(R.id.list_ingredient)
        val amounte = findViewById<TextInputEditText>(R.id.amount_recipe)
        val button_add_ingredient = findViewById<Button>(R.id.add_ingredient)

        button_add_ingredient.setOnClickListener(){
            main_articleArrayList.add(ingredient.text.toString()+" "+amounte.text.toString())
            val adapter = ArrayAdapter(this, R.layout.list_item,main_articleArrayList)
            list_view_ing.adapter = adapter
        }
    }
    private fun openCamera_Click(){
        buttoncamerarecipes.setOnClickListener(){
            //condicion para revisar version de sistema de android
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED
                    || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                    val leaveCamera = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    requestPermissions(leaveCamera,REQUEST_CAMERA)
                }else
                    openCamera()
            }else
                openCamera()
        }
    }
    private fun openGallery_Click(){
        buttongalleryrecipes.setOnClickListener(){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                    val leaveFile = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(leaveFile,REQUEST_GALLERY)
                }else{
                    viewGallery()
                }
            }else{
                viewGallery()
            }
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST_GALLERY ->{
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    viewGallery()
                else
                    Toast.makeText(applicationContext,"No se ha podido acceder a la galeria", Toast.LENGTH_LONG).show()
            }
            REQUEST_CAMERA -> {
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    openCamera()
                else
                    Toast.makeText(applicationContext,"No se ha podido acceder a la camara", Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun viewGallery(){
        intentGallery.type = "image/*"
        startActivityForResult(intentGallery,REQUEST_GALLERY)
    }
    private fun openCamera(){
        val value = ContentValues()
        value.put(MediaStore.Images.Media.TITLE,"Nueva imagen")
        picture = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,value)
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,picture)
        startActivityForResult(cameraIntent,REQUEST_CAMERA)

    }
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?

    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_GALLERY){
            image_recipes.setImageURI(data?.data)
            FileUri = data!!.data
        }
        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_CAMERA){
            image_recipes.setImageURI(picture!!)
        }

    }
}


