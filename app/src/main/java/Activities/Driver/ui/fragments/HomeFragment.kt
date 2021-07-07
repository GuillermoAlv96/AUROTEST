package Activities.Driver.ui.fragments

import Activities.Driver.ui.activities.DriverUpdate
import Models.DriverUser
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.auotravels.R
import com.example.auotravels.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    //private lateinit var dashboardViewModel: DashboardViewModel
    private var _binding: FragmentHomeBinding? = null

    // A variable for user details which will be initialized later on.
    private lateinit var mUserDetails: DriverUser


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        // If we want to use the option menu in fragment we need to add it.
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_home, container, false)

        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.dashboardmenu, menu)
        //if we want to use the option menu in fragment we need to add it.
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.driver_action_settings -> {
                startActivity(Intent(activity, DriverUpdate::class.java))
                return true
            }
        }

        return super.onOptionsItemSelected(item)

    }
}







