package Activities.Driver.ui.activities

import FireStore.FireStore
import Models.DriverUser
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.auotravels.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.driver_create_user.*
import utils.Constants
import utils.GlideLoader
import java.io.IOException


class DriverCreateUser : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.driver_create_user)


        buttonDriverRegister.setOnClickListener {
            register()
        }

        imageButton2.setOnClickListener {

            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                )
                == PackageManager.PERMISSION_GRANTED

            ) {
                Constants.showImageChooser(this)
                Toast.makeText(baseContext, "Permissions Granted.", Toast.LENGTH_SHORT).show()
            } else {
                /*Requests permissions to be granted to this application. These permissions
                 must be requested in your manifest, they should not be granted to your app,
                 and they should have protection level*/
                Toast.makeText(baseContext, "Permissions Denied.", Toast.LENGTH_SHORT).show()
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    Constants.READ_STORAGE_PERMISSION_CODE

                )
            }
        }

    }


    /**
     * A function to validate the entries of a new user.
     */

    private fun validateRegisterDetails(): Boolean {

        return when {
            editDriverFirstNameCreate.text.isEmpty() -> {
                Toast.makeText(this, "Please add name", Toast.LENGTH_SHORT).show()
                false
            }
            editDriverFirstNameCreate.text.isEmpty() -> {
                Toast.makeText(this, "Please add FirstName", Toast.LENGTH_SHORT).show()
                false
            }
            editDriverEmailCreate.text.isEmpty() -> {
                Toast.makeText(this, "Please add E-mail", Toast.LENGTH_SHORT).show()
                false
            }
            editTextPasswordCreate.text.isEmpty() -> {
                Toast.makeText(this, "Please add Password", Toast.LENGTH_SHORT).show()
                false
            }
            editTextPassword2Create.text.toString() != editTextPasswordCreate.text.toString() -> {
                Toast.makeText(this, "Passwords Dont Match", Toast.LENGTH_SHORT).show()
                false
            }
            !checkBoxDriverCreate.isChecked -> {
                Toast.makeText(this, "Please agree terms and conditions", Toast.LENGTH_SHORT).show()
                false
            }

            else -> {
                true
            }
        }
    }


    /**
     * A function to register the user with email and password using FirebaseAuth.
     */

    private fun register() {
        // Check with validate function if the entries are valid or not.
        if (validateRegisterDetails()) {

            val email: String = editDriverEmailCreate.text.toString()
            val password: String = editTextPassword2Create.text.toString()

            // Create an instance and create a register a user with email and password.
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {

                    // If the registration is successfully done
                    if (it.isSuccessful) {

                        // Firebase registered user
                        val firebaseUser: FirebaseUser = it.result!!.user!!
                        Toast.makeText(baseContext, "Authentication Succeed.", Toast.LENGTH_SHORT)
                            .show()

                        // Instance of User data model class.
                        val driverUser = DriverUser(
                            firebaseUser.uid,
                            editDriverFirstNameCreate.text.toString(),
                            editDriverLastNameCreate.text.toString(),
                            editDriverEmailCreate.text.toString(),
                            imageView3.toString(),
                            editTextNumber.text.toString(),
                            "null",
                            0

                        )
                        // Pass the required values in the constructor.
                        FireStore().registerUserDriver(this@DriverCreateUser, driverUser)

                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT)
                            .show()

                        //     updateUI(null)
                    }
                }
        }
    }

    /**
     * Receive the result from a previous call to
     * {@link #startActivityForResult(Intent, int)}.  This follows the
     * related Activity API as described there in
     * {@link Activity#onActivityResult(int, int, Intent)}.
     *
     * @param requestCode The integer request code originally supplied to
     *                    startActivityForResult(), allowing you to identify who this
     *                    result came from.
     * @param resultCode The integer result code returned by the child activity
     *                   through its setResult().
     * @param data An Intent, which can return result data to the caller
     *               (various data can be attached to Intent "extras").
     */

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Constants.PICK_IMAGE_REQUEST_CODE) {
                if (data != null) {
                    try {

                        // The uri of selected image from phone storage.
                        val selectedImageUri = data.data
                        // imageView3.setImageURI(Uri.parse(selectedImageUri.toString()))

                        GlideLoader(this).loadUserPicture(selectedImageUri!!, imageView3)

                    } catch (e: IOException) {
                        e.printStackTrace()
                        Toast.makeText(
                            this,
                            "ERROR",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            // A log is printed when user close or cancel the image selection.
            Log.e("Request Cancelled", "Image selection cancelled")
        }
    }


    /**
     * A function to notify the success result of Firestore entry when the user is registered successfully.
     */
    fun userRegistrationSuccess() {

        Toast.makeText(
            this@DriverCreateUser,
            "You are registered successfully",
            Toast.LENGTH_SHORT
        ).show()

        /**
         * Here the new user registered is automatically signed-in so we just sign-out the user from firebase
         * and send him to Intro Screen for Sign-In
         */
        FirebaseAuth.getInstance().signOut()
        // Finish the Register Screen
        finish()
    }

}










