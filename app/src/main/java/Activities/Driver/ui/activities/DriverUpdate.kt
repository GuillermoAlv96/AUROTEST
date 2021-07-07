package Activities.Driver.ui.activities

import FireStore.FireStore
import Models.DriverUser
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.auotravels.R
import kotlinx.android.synthetic.main.driver_create_user.*
import kotlinx.android.synthetic.main.driver_update.*
import utils.Constants
import utils.GlideLoader

class DriverUpdate : AppCompatActivity() {

    // A variable for user details which will be initialized later on.
    private lateinit var mUserDetails: DriverUser

    // Add a global variable for URI of a selected image from phone storage.
    private var mSelectedImageFileUri: Uri? = null

    private var mUserProfileImageURL: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.driver_update)

        buttonUpdate.setOnClickListener {

            if (validateUserProfileDetails()) {

                /*if (mSelectedImageFileUri != null) {

                     FireStore().uploadImageToCloudStorage(
                         this@DriverUpdate,
                         mSelectedImageFileUri,
                         Constants.USER_PROFILE_IMAGE
                     )
                 } else {*/

                updateUserProfileDetails()

            }
        }
    }

    override fun onResume() {
        super.onResume()

        getUserDetails()
    }

    /**
     * A function to get the user details from firestore.
     */
    private fun getUserDetails() {

        // Call the function of Firestore class to get the user details from firestore which is already created.
        FireStore().getUserDetails(this)

    }


    /**
     * A function to receive the user details and populate it in the UI.
     */
    fun userDetailsSuccess(user: DriverUser) {

        mUserDetails = user

        // Load the image using the Glide Loader class.
        GlideLoader(this@DriverUpdate).loadUserPicture(user.image, imageViewDriverUpdate)

        // Assign the on click event to the user profile photo.
        imageViewDriverUpdate.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
                == PackageManager.PERMISSION_GRANTED
            ) {
                Constants.showImageChooser(this@DriverUpdate)
            } else {
                /*Requests permissions to be granted to this application. These permissions
                 must be requested in your manifest, they should not be granted to your app,
                 and they should have protection level*/
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    Constants.READ_STORAGE_PERMISSION_CODE
                )
            }

        }

        //Load the info of the driver
        editTextFirstNameUpdate.setText(user.firstName)
        editTextLastNameUpdate.setText(user.lastName)
        editTextEmailUpdate.setText(user.email)
        editTextPhoneUpdate.setText(user.mobile)
        editTextSexUpdate.setText(user.gender)

    }


    /**
     * A function to validate the input entries for profile details.
     */
    private fun validateUserProfileDetails(): Boolean {
        return when {

            // We have kept the user profile picture is optional.
            editTextFirstNameUpdate.text.isEmpty() -> {
                Toast.makeText(this, "Please add name", Toast.LENGTH_SHORT).show()
                false
            }
            editTextLastNameUpdate.text.isEmpty() -> {
                Toast.makeText(this, "Please add FirstName", Toast.LENGTH_SHORT).show()
                false
            }
            editTextEmailUpdate.text.isEmpty() -> {
                Toast.makeText(this, "Please add E-mail", Toast.LENGTH_SHORT).show()
                false
            }
            editTextPhoneUpdate.text.isEmpty() -> {
                Toast.makeText(this, "Please add Password", Toast.LENGTH_SHORT).show()
                false
            }

            else -> {
                true
            }
        }
    }

    /**
     * A function to update user profile details to the firestore.
     */
    private fun updateUserProfileDetails() {

        val userHashMap = HashMap<String, Any>()

        // Get the FirstName from editText
        val firstName = editTextFirstNameUpdate.text.toString()
        if (firstName != mUserDetails.firstName) {
            userHashMap[Constants.FIRST_NAME] = firstName
        }

        // Get the LastName from editText
        val lastName = editTextLastNameUpdate.text.toString()
        if (lastName != mUserDetails.lastName) {
            userHashMap[Constants.LAST_NAME] = lastName
        }

        // Here we get the text from editText
        val mobileNumber = editTextPhoneUpdate.text.toString()
        if (mobileNumber != mUserDetails.mobile) {
            userHashMap[Constants.MOBILE] = mobileNumber
        }

        if (mUserProfileImageURL.isNotEmpty()) {
            userHashMap[Constants.IMAGE] = mUserProfileImageURL
        }


        // Here if user is about to complete the profile then update the field or else no need.
        // 0: User profile is incomplete.
        // 1: User profile is completed.
        if (mUserDetails.profileCompleted == 0) {
            userHashMap[Constants.COMPLETE_PROFILE] = 1
        }

        // call the registerUser function of FireStore class to make an entry in the database.
        FireStore().updateUserProfileData(
            this@DriverUpdate,
            userHashMap
        )
    }

    /**
     * A function to notify the success result and proceed further accordingly after updating the user details.
     */
    fun userProfileUpdateSuccess() {


        Toast.makeText(
            this@DriverUpdate,
            "Your profile is updated successfully",
            Toast.LENGTH_SHORT
        ).show()
        // Redirect to the Main Screen after profile completion.
        startActivity(Intent(this@DriverUpdate, DriverDashBoard::class.java))
        //finish()
    }

}