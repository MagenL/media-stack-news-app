package amagen.magen.mediastacknews

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import amagen.magen.mediastacknews.databinding.ActivityMainBinding
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //attach to mainActivity to it's view model
        mainActivityViewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]

        //init nav controller to bottom navigation menu
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.app_container)
        navView.setupWithNavController(navController)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null

    }
}