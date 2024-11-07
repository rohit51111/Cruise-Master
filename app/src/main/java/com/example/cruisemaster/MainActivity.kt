package com.example.cruisemaster

import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.cruisemaster.localdb.database.AppDatabase
import com.example.cruisemaster.shiplist.fragment.ShipListFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(R.id.mainContainer, ShipListFragment())
                addToBackStack("ShipListFragment")
                Log.d("MainActivity", "ShipListFragment added to back stack")
            }
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
        Log.e("MainActivity", "FragmentCounts ${supportFragmentManager.backStackEntryCount}")
    }


    /*  override fun onBackPressed() {
        // Optionally perform any cleanup or additional actions here
        super.onBackPressed()
        finishAffinity() // Closes all activities in the task stack
    }*/

}