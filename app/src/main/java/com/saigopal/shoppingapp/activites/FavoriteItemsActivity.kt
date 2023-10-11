package com.saigopal.shoppingapp.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.window.OnBackInvokedDispatcher
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.saigopal.shoppingapp.R
import com.saigopal.shoppingapp.adapters.FavoritesRecyclerAdapter
import com.saigopal.shoppingapp.databinding.ActivityFavoriteItemsBinding
import com.saigopal.shoppingapp.models.Item
import com.saigopal.shoppingapp.viewModels.FavoriteViewModel
import kotlinx.coroutines.launch

class FavoriteItemsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteItemsBinding
    private var favoriteItemList:MutableList<Item> = mutableListOf()
    private lateinit var favouriteAdapter: FavoritesRecyclerAdapter
    private lateinit var favouriteViewModel: FavoriteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_favorite_items)
        binding.lifecycleOwner = this

        favouriteViewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]


        favouriteAdapter = FavoritesRecyclerAdapter(favoriteItemList,favouriteViewModel)


        binding.favouritesRecycler.layoutManager = LinearLayoutManager(this)
        binding.favouritesRecycler.adapter = favouriteAdapter

        lifecycleScope.launch {
            favouriteViewModel.getFavoriteItems()
        }

        binding.back.setOnClickListener { onBackPressedDispatcher.onBackPressed() }


        observeLiveData()

    }


    private fun observeLiveData() {
        favouriteViewModel.mutableLiveItemList.observe(this) {
            if(!it.isNullOrEmpty()){
                favoriteItemList.clear()
                favoriteItemList.addAll(it)
                favouriteAdapter.notifyDataSetChanged()
                binding.emptyFav.visibility = View.GONE
            }else{
                binding.emptyFav.visibility = View.VISIBLE
            }
        }
    }
}