package com.saigopal.shoppingapp

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.saigopal.shoppingapp.activites.CartActivity
import com.saigopal.shoppingapp.activites.FavoriteItemsActivity
import com.saigopal.shoppingapp.adapters.HomeRecyclerAdapter
import com.saigopal.shoppingapp.databinding.ActivityMainBinding
import com.saigopal.shoppingapp.models.Categories
import com.saigopal.shoppingapp.utils.FilterDialog
import com.saigopal.shoppingapp.viewModels.MainViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private var categoriesList : MutableList<Categories> = mutableListOf()
    private lateinit var categoriesAdapter:HomeRecyclerAdapter
    private var textView:TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        categoriesAdapter = HomeRecyclerAdapter(categoriesList,mainViewModel)

        binding.categoriesRecycler.layoutManager = LinearLayoutManager(this)
        binding.categoriesRecycler.adapter = categoriesAdapter



        binding.toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.favourite->{
                    startActivity(Intent(this,FavoriteItemsActivity::class.java))
                    true
                }
                R.id.cart->{
                    startActivity(Intent(this,CartActivity::class.java))
                    true
                }
                else -> {
                    false
                }
            }
        }

        val menuItem = binding.toolbar.menu.findItem(R.id.cart)
        val actionView = menuItem.actionView

        textView = actionView!!.findViewById(R.id.cart_count)

        actionView.setOnClickListener {
            startActivity(Intent(this,CartActivity::class.java))
        }

        binding.fabOpen.setOnClickListener {
            val filterDialog = FilterDialog()
            filterDialog.categoriesList = categoriesList
            filterDialog.show(supportFragmentManager,"filter")
        }


        observeLiveData()
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch {
            mainViewModel.getAllCategoriesList()
        }
    }

    private fun observeLiveData(){

        mainViewModel.mutableCategoryData.observe(this) {
            if (!it.isNullOrEmpty()){
                categoriesList.clear()
                categoriesList.addAll(it)
                categoriesAdapter.notifyDataSetChanged()
            }
        }
        mainViewModel.cartItems.observe(this){
            if (textView != null){
                if (it.isNullOrEmpty()){
                    textView!!.text = "0"
                }else{
                    textView!!.text = it.size.toString()
                }
            }
        }
    }



}