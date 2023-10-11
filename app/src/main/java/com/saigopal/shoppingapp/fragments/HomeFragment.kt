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
import com.saigopal.shoppingapp.adapters.HomeRecyclerAdapter
import com.saigopal.shoppingapp.databinding.FragmentHomeBinding
import com.saigopal.shoppingapp.models.Categories
import com.saigopal.shoppingapp.viewModels.HomeViewModel
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private var categoriesList : MutableList<Categories> = mutableListOf()
    private lateinit var categoriesAdapter:HomeRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(LayoutInflater.from(container!!.context),R.layout.fragment_home,container,false)
        binding.lifecycleOwner = this

        categoriesAdapter = HomeRecyclerAdapter(categoriesList,homeViewModel)

        binding.categoriesRecycler.layoutManager = LinearLayoutManager(context)
        binding.categoriesRecycler.adapter = categoriesAdapter


        lifecycleScope.launch {
            homeViewModel.getAllCategoriesList()
        }


       observeLiveData()

        return binding.root
    }

    private fun observeLiveData(){

        homeViewModel.mutableCategoryData.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()){
                categoriesList.clear()
                categoriesList.addAll(it)
                categoriesAdapter.notifyDataSetChanged()
            }
        }

    }

}