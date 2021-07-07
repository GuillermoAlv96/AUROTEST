package Activities.Driver.ui.activities


import FireStore.FireStore
import Models.DriverUser
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.auotravels.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.driver_login.*
import utils.Constants

/**
 * Login Screen of the application for drivers.
 */

class DriverLogin : AppCompatActivity() {

    /**
     * This function is auto created by Android when the Activity Class is created.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.driver_login)

        val intent: Intent = Intent(this, DriverCreateUser::class.java)


        createDriverAccountDriver.setOnClickListener {
            startActivity(intent)
        }

        driverLoginButton.setOnClickListener {
            login()
        }
    }

    /**
     * A function to Log-In. The user will be able to log in using the registered email and password with Firebase Authentication.
     */

    open fun login() {


        // Check with validate function if the entries are valid or not.
        val email: String = editDriverEmailLogin.text.toString()
        val password: String = editDriverPasswordLogin.text.toString()

        // Log-In using FirebaseAuth
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {

                    FireStore().getUserDetails(this@DriverLogin)

                } else {

                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    /**
     * A function to notify user that logged in success and get the user details from the FireStore database after authentication.
     */

    fun userLoggedInSuccess(user: DriverUser) {

        if (user.profileCompleted == 0) {
            // If the user profile is incomplete then launch the UserProfileActivity.
            val intent: Intent = Intent(this, DriverUpdate::class.java)
            intent.putExtra(Constants.EXTRA_USER_DETAILS, user)
            startActivity(intent)

        } else {

            val intent: Intent = Intent(this, DriverDashBoard::class.java)
            intent.putExtra(Constants.EXTRA_USER_DETAILS, user)
            startActivity(intent)
        }

        finish()
    }

}