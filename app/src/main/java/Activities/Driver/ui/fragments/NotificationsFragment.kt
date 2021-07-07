package Activities.Driver.ui.fragments

import Activities.Driver.ui.activities.DriverUpdate
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.auotravels.R
import com.example.auotravels.R.id.driver_action_settings
import com.example.auotravels.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    //private lateinit var notificationsViewModel: NotificationsViewModel
    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

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
        // notificationsViewModel =  ViewModelProvider(this).get(NotificationsViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_notifications, container, false)

        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.dashboardmenu,menu)
        //if we want to use the option menu in fragment we need to add it.
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when(id){
        driver_action_settings ->{
            startActivity(Intent(activity,DriverUpdate::class.java))
            return true
        }
    }

        return super.onOptionsItemSelected(item)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}