package Activities.Client.activities

import FireStore.FireStore
import Models.ClientUser
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.auotravels.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.client_create_user.*
import kotlinx.android.synthetic.main.driver_create_user.*


class ClientCreateUser : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.client_create_user)

        buttonRegisterClient.setOnClickListener{
            register()
        }

    }

    /**
     * A function to validate the entries of a new user.
     */

    private fun clientValidateRegisterDetails(): Boolean {

        return when {
            editTextClientFirstNameCreate.text.isEmpty() -> {
                Toast.makeText(this, "Please add name", Toast.LENGTH_SHORT).show()
                false
            }
            editTextClientLastNameCreate.text.isEmpty() -> {
                Toast.makeText(this, "Please add FirstName", Toast.LENGTH_SHORT).show()
                false
            }
            editTextClientEmailCreate.text.isEmpty() -> {
                Toast.makeText(this, "Please add E-mail", Toast.LENGTH_SHORT).show()
                false
            }


            editTextClientPhoneCreate.text.isEmpty() -> {
                Toast.makeText(this, "Please add phone", Toast.LENGTH_SHORT).show()
                false
            }

            editTextPasswordClientCreate.text.isEmpty() -> {
                Toast.makeText(this, "Please add Password", Toast.LENGTH_SHORT).show()
                false
            }
            editTextClientPasswordCreate.text.toString() != editTextPasswordClientCreate.text.toString() -> {
                Toast.makeText(this, "Passwords Dont Match", Toast.LENGTH_SHORT).show()
                false
            }

            !checkBoxClient.isChecked -> {
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
        if (clientValidateRegisterDetails()) {

            val email: String = editTextClientEmailCreate.text.toString()
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
                        val clientUser = ClientUser(
                            firebaseUser.uid,
                            editTextClientFirstNameCreate.text.toString(),
                            editTextClientLastNameCreate.text.toString(),
                            editTextClientEmailCreate.text.toString(),
                            "null",
                            editTextClientPhoneCreate.text.toString(),
                            "null",
                            0

                        )
                        // Pass the required values in the constructor.
                        FireStore().registerUserClient(this@ClientCreateUser, clientUser)

                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT)
                            .show()

                        //     updateUI(null)
                    }
                }
        }

        /**
         * A function to notify the success result of Firestore entry when the user is registered successfully.
         */

    }
        fun clientRegistrationSuccess() {

            Toast.makeText(
                this@ClientCreateUser,
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