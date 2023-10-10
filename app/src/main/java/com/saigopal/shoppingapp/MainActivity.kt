package com.saigopal.shoppingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.saigopal.shoppingapp.databinding.ActivityMainBinding
import com.saigopal.shoppingapp.fragments.CartFragment
import com.saigopal.shoppingapp.fragments.FavouriteFragment
import com.saigopal.shoppingapp.fragments.HomeFragment
import com.saigopal.shoppingapp.viewModels.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        val homeFragment = HomeFragment()
        val cartFragment = CartFragment()
        val favouriteFragment = FavouriteFragment()

        setFragment(homeFragment)

        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    setFragment(homeFragment)
                    true
                }
                R.id.cart->{
                    setFragment(cartFragment)
                    true
                }
                R.id.favourite->{
                    setFragment(favouriteFragment)
                    true
                }

                else -> {
                    false
                }
            }
        }
    }

    private fun setFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(binding.frameLayout.id,fragment)
            .commit()
    }
}