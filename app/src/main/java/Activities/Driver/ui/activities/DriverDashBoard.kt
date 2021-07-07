package Activities.Driver.ui.activities

import FireStore.FireStore
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import Models.DriverUser
import androidx.core.content.ContextCompat
import com.example.auotravels.R
import com.example.auotravels.databinding.DriverDashBoardBinding

class DriverDashBoard : AppCompatActivity() {

    private lateinit var binding: DriverDashBoardBinding

    // A variable for user details which will be initialized later on.
    private lateinit var mUserDetails: DriverUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // Update the background color of the action bar as per our design requirement.
        supportActionBar!!.setBackgroundDrawable(
            ContextCompat.getDrawable(
                this@DriverDashBoard,
                R.drawable.img_splash_background
            )
        )

        binding = DriverDashBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_driver_dash_board)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }
}




