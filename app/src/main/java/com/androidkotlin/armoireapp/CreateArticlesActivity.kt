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
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.androidkotlin.construct.construct_create_article
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activitycreatearticles.*


class CreateArticlesActivity : AppCompatActivity() {
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
    private lateinit var buttoncreatearticle : Button
    private lateinit var switchenablearticle : Switch
    private lateinit var markArrayList : MutableList<String>
    private lateinit var list: List<String>
    val intentGallery = Intent(Intent.ACTION_PICK)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activitycreatearticles)
        dataBase= FirebaseDatabase.getInstance()

        buttoncreatearticle = findViewById(R.id.buttoncreatearticles)
        markArrayList = mutableListOf()
        list = listOf()
        //funciones de galerria
        openGallery_Click()
        //funciones de camara
        openCamera_Click()
        //funciones de subir imagen
        CreateArticles_Click()
        //menu contextuales
        ContextMainMark()
        ContextMainType()
        ContextMainVolume()
        ContextMainWeight()
        EnabledDimensionArticles_Switch()
    }



    /*
    funciones para realizar los gestos y operaciones del modulo del
    activity activitycreatearticles.
     */
    //funcion para subir imagenes
    private fun EnabledDimensionArticles_Switch() {
        switchenablearticle = findViewById(R.id.enable_dimension_article)
        val volumetext = findViewById<TextInputEditText>(R.id.volume)
        val volumemain = findViewById<TextInputLayout>(R.id.list_popup_button_volume)
        val weighttext= findViewById<TextInputEditText>(R.id.weight)
        val weightmain= findViewById<TextInputLayout>(R.id.list_popup_button_weigth)
        switchenablearticle.setOnCheckedChangeListener({ _, isChecked ->
            if (isChecked) {
                volumetext.isEnabled = true
                volumemain.isEnabled = true
                weighttext.isEnabled = true
                weightmain.isEnabled = true
            } else {
                volumetext.isEnabled = false
                volumemain.isEnabled = false
                weighttext.isEnabled = false
                weightmain.isEnabled = false
            }
        })
    }

    private fun CreateArticles_Click() {
        val dbReference=dataBase.reference.child("Articles")
        buttoncreatearticle.setOnClickListener() {
            val name = findViewById<TextInputEditText>(R.id.name_article).text.toString()
            val mark = findViewById<AutoCompleteTextView>(R.id.text_mark_list).text.toString()
            val type = findViewById<AutoCompleteTextView>(R.id.text_type_list).text.toString()
            val price : Double = findViewById<TextInputEditText>(R.id.price).text.toString().toDouble()
            val description = findViewById<TextInputEditText>(R.id.description).text.toString()
            val volume = findViewById<TextInputEditText>(R.id.volume).text.toString()
            val weight  = findViewById<TextInputEditText>(R.id.weight).text.toString()
            val unitvolume = findViewById<AutoCompleteTextView>(R.id.text_type_list_volume).text.toString()
            val unitweight = findViewById<AutoCompleteTextView>(R.id.text_type_list_weigth).text.toString()

            val Folder: StorageReference =
                FirebaseStorage.getInstance().getReference().child("Articles")
            val file_name: StorageReference = Folder.child("file" + FileUri!!.lastPathSegment)
            file_name.putFile(FileUri!!).addOnSuccessListener { taskSnapshot ->
                var hashMap:String

                file_name.getDownloadUrl().addOnSuccessListener { uri ->
                    hashMap = java.lang.String.valueOf(uri)
                    val create_articles =
                        construct_create_article(hashMap, name, mark, type, price, description, volume, weight, unitvolume,unitweight)
                        dbReference.push().setValue(create_articles)
                }
                startActivity(Intent(this,NavigatorActivity::class.java))
            }
        }
    }

    // userBD.child("Picture_Article").setValue(imagearticle)


    //funcion para llenar la lista del objeto menu contextual
    private fun ContextMainMark(){
        val listPopupWindowButton = findViewById<TextInputLayout>(R.id.list_popup_button_mark)
        val listPopupWindow = ListPopupWindow(this, null, R.attr.listPopupWindowStyle)

        // Set button as the list popup's anchor
        listPopupWindow.anchorView= listPopupWindowButton
        markArrayList = mutableListOf()
        getMarkData()
        val adapter =ArrayAdapter(this, R.layout.list_item,markArrayList)
        (listPopupWindowButton.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }
    private fun getMarkData() {
        val dbReference = FirebaseDatabase.getInstance().getReference("Marks")
        dbReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
               if(snapshot.exists()){
                   for (SnapShot in snapshot.children){
                           val mark = SnapShot.child("name_mark").value as? String
                            markArrayList.add(mark!!)
                   }
               }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    private fun ContextMainVolume(){
        val listPopupWindowButton = findViewById<TextInputLayout>(R.id.list_popup_button_volume)
        val listPopupWindow = ListPopupWindow(this, null, R.attr.listPopupWindowStyle)

        // Set button as the list popup's anchor
        listPopupWindow.anchorView= listPopupWindowButton
        val items = listOf("ml", "Oz", "Lt", "Gl")
        val adapter = ArrayAdapter(this, R.layout.list_item, items)
        (listPopupWindowButton.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    private fun ContextMainWeight(){
        val listPopupWindowButton = findViewById<TextInputLayout>(R.id.list_popup_button_weigth)
        val listPopupWindow = ListPopupWindow(this, null, R.attr.listPopupWindowStyle)

        // Set button as the list popup's anchor
        listPopupWindow.anchorView= listPopupWindowButton
        val items = listOf("mg", "gr", "lb", "kg")
        val adapter = ArrayAdapter(this, R.layout.list_item, items)
        (listPopupWindowButton.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    private fun ContextMainType(){
        val listPopupWindowButton = findViewById<TextInputLayout>(R.id.list_popup_button_type)
        val listPopupWindow = ListPopupWindow(this, null, R.attr.listPopupWindowStyle)
        // Set button as the list popup's anchor
        listPopupWindow.anchorView = listPopupWindowButton

        // Set list popup's content
        val items = listOf("Alimento", "Articulos de Limpieza", "Herramientas", "Accesorios")
        val adapter = ArrayAdapter(this, R.layout.list_item, items)
        (listPopupWindowButton.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }
    //funcion para obtener una imagen o una foto desde los recursos del sistema
    //funcion de abrir camara
    private fun openCamera_Click(){
        buttoncamera.setOnClickListener(){
            //condicion para revisar version de sistema de android
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED
                    || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                    val leaveCamera = arrayOf(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    requestPermissions(leaveCamera,REQUEST_CAMERA)
                }else
                    openCamera()
            }else
                openCamera()
        }
    }
    private fun openGallery_Click(){
        buttongallery.setOnClickListener(){
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
            image_article.setImageURI(data?.data)
            FileUri = data!!.data
        }
        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_CAMERA){
            image_article.setImageURI(picture!!)
        }

    }
}

















