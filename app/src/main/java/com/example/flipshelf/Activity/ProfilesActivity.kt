package com.example.flipshelf.Activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.flipshelf.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.io.FileOutputStream
import java.util.UUID


class ProfilesActivity : AppCompatActivity() {
    private lateinit var db: FirebaseDatabase
    private lateinit var profileNameEditText: EditText
    private lateinit var profileEmailEditText: EditText
    private lateinit var profileImageView: ImageView
    private lateinit var updateBtn: Button
    private lateinit var editImageView: ImageView
    private lateinit var mProgressDialog: ProgressDialog
    private var imageUri: Uri? = null
    private var imageUpdated: Boolean =false

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profiles)

        db = FirebaseDatabase.getInstance()
        profileNameEditText = findViewById(R.id.profileName_EditText)
        profileEmailEditText = findViewById(R.id.profileEmail_EditText)
        profileImageView = findViewById(R.id.ProfileImage_ImageView)
        editImageView = findViewById(R.id.editImage_ImageView)
        updateBtn = findViewById(R.id.update_Button)

        val name = intent.getStringExtra("name")
        val email = intent.getStringExtra("email")
        val image = intent.getStringExtra("image")

        profileNameEditText.setText(name)
        profileEmailEditText.setText(email)
        if (image != null) {
            Glide.with(this).load(image).into(profileImageView)
        }

        editImageView.setOnClickListener {

            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE),
                    112
                )
            } else {
                showImageSelectionOptions()
            }

        }


        updateBtn.setOnClickListener {
            if (profileNameEditText.text.toString().isEmpty() || profileEmailEditText.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (imageUpdated){
                uploadImage()
            } else{
                updateProfile(image!!)
            }


            mProgressDialog = ProgressDialog(this)
            mProgressDialog.setMessage("Please wait...")
            mProgressDialog.show()
        }

    }

    @SuppressLint("IntentReset")
    private fun showImageSelectionOptions() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryIntent.type = "image/*"
        val chooserIntent = Intent.createChooser(galleryIntent, "Select Image")
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(cameraIntent))
        startActivityForResult(chooserIntent, 100)
    }

    @Deprecated("This method is deprecated, but using for demonstration")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            if (data?.data != null) {
                imageUri = data.data
                profileImageView.setImageURI(imageUri)
                imageUpdated = true
            } else if (data?.extras?.get("data") != null) {
                val bitmap = data.extras?.get("data") as Bitmap
                profileImageView.setImageBitmap(bitmap)
                imageUri = getImageUri(bitmap)
                imageUpdated=true
            }
        }
    }

    private fun getImageUri(inImage: Bitmap): Uri? {
        return try {
            val fileName = "IMG_${System.currentTimeMillis()}.jpg"
            val file = File(getExternalFilesDir(null), fileName)

            val outStream = FileOutputStream(file)
            inImage.compress(Bitmap.CompressFormat.JPEG, 100, outStream)
            outStream.flush()
            outStream.close()
            Uri.fromFile(file)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 112 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            showImageSelectionOptions()
        } else {
            Toast.makeText(this, "Permissions required for this action", Toast.LENGTH_SHORT).show()
        }
    }

    private fun uploadImage() {
        val storageRef =
            FirebaseStorage.getInstance().reference.child("profile_images/${UUID.randomUUID()}.jpg")
        storageRef.putFile(imageUri!!)
            .addOnSuccessListener {
                storageRef.downloadUrl.addOnSuccessListener { uri ->
                    updateProfile(uri.toString())
                    mProgressDialog.dismiss()
                    Toast.makeText(this, "Upload Success", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                mProgressDialog.dismiss()
                Toast.makeText(this, "Image upload failed", Toast.LENGTH_SHORT).show()
            }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun updateProfile(uri: String) {
        val id = FirebaseAuth.getInstance().currentUser!!.uid
        val updateName = profileNameEditText.text.toString()
        val updateEmail = profileEmailEditText.text.toString()

        val data = HashMap<String, Any>()
        data["name"] = updateName
        data["email"] = updateEmail
        data["image"] = uri


            db.reference.child("users").child(id).updateChildren(data)
                .addOnSuccessListener {
                    Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                    profileNameEditText.text.clear()
                    profileEmailEditText.text.clear()
                    finish()

                }
                .addOnFailureListener { exception ->
                    Toast.makeText(
                        applicationContext, "Error: ${exception.message}", Toast.LENGTH_SHORT
                    ).show()
                }


        }

}



