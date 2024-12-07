package com.example.flipshelf

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.flipshelf.Fragment.BagFragment
import com.example.flipshelf.Fragment.HeartFragment
import com.example.flipshelf.Fragment.HomeFragment
import com.example.flipshelf.Fragment.WalletFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonNavigation= findViewById<BottomNavigationView>(R.id.bottom_NavigationView)
        homeFragment(HomeFragment())
        buttonNavigation.setOnItemSelectedListener { item ->

            if (item.itemId==R.id.home) {
                homeFragment(HomeFragment())
                return@setOnItemSelectedListener true
            }

            else if (item.itemId==R.id.heart) {
                homeFragment(HeartFragment())
                return@setOnItemSelectedListener true
            }

            else if (item.itemId==R.id.bag) {
                homeFragment(BagFragment())
                return@setOnItemSelectedListener true
            }

            else if (item.itemId==R.id.wallet) {
                homeFragment(WalletFragment())
                return@setOnItemSelectedListener true
            }

            else{
                return@setOnItemSelectedListener false

            }
        }
    }

    private fun homeFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment,fragment)
        transaction.commit()
    }


}