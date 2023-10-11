package com.saigopal.shoppingapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.saigopal.shoppingapp.R
import com.saigopal.shoppingapp.adapters.FavoritesRecyclerAdapter
import com.saigopal.shoppingapp.databinding.FragmentFavouriteBinding
import com.saigopal.shoppingapp.models.Item
import com.saigopal.shoppingapp.viewModels.FavoriteViewModel
import kotlinx.coroutines.launch

class FavouriteFragment : Fragment() {

    private lateinit var binding:FragmentFavouriteBinding
    private var favoriteItemList:MutableList<Item> = mutableListOf()
    private lateinit var favouriteAdapter:FavoritesRecyclerAdapter
    private lateinit var favouriteViewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favouriteViewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(LayoutInflater.from(container!!.context),R.layout.fragment_favourite,container,false)
        binding.lifecycleOwner = this

        favouriteAdapter = FavoritesRecyclerAdapter(favoriteItemList,favouriteViewModel)


        binding.favouritesRecycler.layoutManager = LinearLayoutManager(context)
        binding.favouritesRecycler.adapter = favouriteAdapter

        lifecycleScope.launch {
            favouriteViewModel.getFavoriteItems()
        }

        observeLiveData()

        return binding.root
    }

    private fun observeLiveData() {
        favouriteViewModel.mutableLiveItemList.observe(viewLifecycleOwner) {
            if(!it.isNullOrEmpty()){
                favoriteItemList.clear()
                favoriteItemList.addAll(it)
                favouriteAdapter.notifyDataSetChanged()
            }
        }
    }

}