package Activities

import Activities.Client.activities.ClientLogin
import Activities.Driver.ui.activities.DriverLogin
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.auotravels.R
import kotlinx.android.synthetic.main.pre_login.*

class PreLogin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pre_login)

        driverButton.setOnClickListener {

            val intent: Intent = Intent(this, DriverLogin::class.java)
            startActivity(intent)
        }
        clientButton.setOnClickListener {

            val intent: Intent = Intent(this, ClientLogin::class.java)
            startActivity(intent)

        }

    }


}