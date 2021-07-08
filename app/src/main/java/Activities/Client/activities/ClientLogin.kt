package Activities.Client.activities

import Activities.Driver.ui.activities.DriverCreateUser
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.auotravels.R
import kotlinx.android.synthetic.main.client_login.*

class ClientLogin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.client_login)

        val intent: Intent = Intent(this, ClientCreateUser::class.java)
        createAccountTextView.setOnClickListener {

            startActivity(intent)
        }
    }


}