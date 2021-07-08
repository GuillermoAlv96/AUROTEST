package FireStore

import Activities.Client.activities.ClientCreateUser
import Activities.Driver.ui.activities.DriverCreateUser
import Activities.Driver.ui.activities.DriverLogin
import Activities.Driver.ui.activities.DriverUpdate
import Activities.Driver.ui.fragments.HomeFragment
import Models.ClientUser
import Models.DriverUser
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import utils.Constants


/**
 * A custom class where we will add the operation performed for the FireStore database.
 */

open class FireStore {

    private val mFireStore: FirebaseFirestore = FirebaseFirestore.getInstance()


    /**
     * A function to make an entry of the registered user in the FireStore database.
     */

    open fun registerUserDriver(activity: DriverCreateUser, driverUserInfo: DriverUser) {

        //The "user" is collection name. If the collection is already created then it will not create the same one again.
        mFireStore.collection(Constants.DRIVERUSERS)

            //Document Id for users fields. Here the document it is the User ID.
            .document(driverUserInfo.id)

            //Here the userInfo are Field amd SetOption is set to merge. It is in case we want to merge to merge later on instead of replacing the fields.
            .set(driverUserInfo, SetOptions.merge())
            .addOnSuccessListener { documentReference ->
                activity.userRegistrationSuccess()
            }
            .addOnFailureListener { e ->

            }
    }


    /**
     * A function to get the user id of current logged user.
     */

    fun getCurrentUserID(): String {
        // An Instance of currentUser using FirebaseAuth
        val currentUser = FirebaseAuth.getInstance().currentUser

        // A variable to assign the currentUserId if it is not null or else it will be blank.
        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }
        return currentUserID
    }


    /**
     * A function to get the logged user details from from FireStore Database.
     */

    fun getUserDetails(activity: Activity) {

        // Here we pass the collection name from which we wants the data.
        mFireStore.collection(Constants.DRIVERUSERS)
            // The document id to get the Fields of user.
            .document(getCurrentUserID())
            .get()
            .addOnSuccessListener {

                // Here we have received the document snapshot which is converted into the User Data model object.
                val user = it.toObject(DriverUser::class.java)!!
                val sharedPreferences =
                    activity.getSharedPreferences(
                        Constants.MYSHOPPAL_PREFERENCES,
                        Context.MODE_PRIVATE
                    )

                // Create an instance of the editor which will help us to edit the SharedPreference.
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putString(
                    Constants.LOGGED_IN_USERNAME,
                    "${user.firstName} ${user.lastName}"
                )
                editor.apply()
                when (activity) {
                    is DriverLogin -> {
                        // Call a function of base activity for transferring the result to it.
                        activity.userLoggedInSuccess(user)
                    }
                //    is DriverDashBoard -> {
                //        activity.userDetailsSuccess(user)
                //    }
                    is DriverUpdate -> {
                        activity.userDetailsSuccess(user)
                    }

                }
            }

        Log.e(
            activity.javaClass.simpleName,
            "Error while getting user details.",

            )
    }

    /**
     * A function to update the user profile data into the database.
     *
     * @param activity The activity is used for identifying the Base activity to which the result is passed.
     * @param userHashMap HashMap of fields which are to be updated.
     */
    fun updateUserProfileData(activity: Activity, userHashMap: HashMap<String, Any>) {
        // Collection Name
        mFireStore.collection(Constants.DRIVERUSERS)
            // Document ID against which the data to be updated. Here the document id is the current logged in user id.
            .document(getCurrentUserID())
            // A HashMap of fields which are to be updated.
            .update(userHashMap)
            .addOnSuccessListener {

                // Notify the success result.
                when (activity) {
                    is DriverUpdate -> {
                        // Call a function of base activity for transferring the result to it.
                        activity.userProfileUpdateSuccess()
                    }
                }
            }
            .addOnFailureListener { e ->

                when (activity) {
                    is DriverUpdate -> {
                       Log.d("didntenter","didntenter")
                    }
                }

                Log.e(
                    activity.javaClass.simpleName,
                    "Error while updating the user details.",
                    e
                )
            }
    }



    /**
     * A function to make an entry of the registered user in the FireStore database.
     */

    open fun registerUserClient(activity: ClientCreateUser, clientUserInfo: ClientUser) {

        //The "user" is collection name. If the collection is already created then it will not create the same one again.
        mFireStore.collection("Clients")

            //Document Id for users fields. Here the document it is the User ID.
            .document(clientUserInfo.id)

            //Here the userInfo are Field amd SetOption is set to merge. It is in case we want to merge to merge later on instead of replacing the fields.
            .set(clientUserInfo, SetOptions.merge())
            .addOnSuccessListener { documentReference ->
                activity.clientRegistrationSuccess()
            }
            .addOnFailureListener { e ->

                Log.d("error","error")

            }
    }
}


