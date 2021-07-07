package Activities.Client.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.auotravels.R
import kotlinx.android.synthetic.main.client_login.*

class ClientLogin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.client_login)

        createAccountTextView.setOnClickListener {

        }
    }


}