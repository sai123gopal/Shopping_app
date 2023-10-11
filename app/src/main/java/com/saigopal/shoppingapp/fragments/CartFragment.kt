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
import com.saigopal.shoppingapp.adapters.CartItemsRecyclerAdapter
import com.saigopal.shoppingapp.databinding.FragmentCartBinding
import com.saigopal.shoppingapp.models.CartData
import com.saigopal.shoppingapp.viewModels.CartViewModel
import kotlinx.coroutines.launch

class CartFragment : Fragment() {

    private lateinit var binding:FragmentCartBinding
    private lateinit var viewModel:CartViewModel
    private val cartItemsList:MutableList<CartData> = mutableListOf()
    private lateinit var cartAdapter:CartItemsRecyclerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[CartViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(LayoutInflater.from(container!!.context),R.layout.fragment_cart,container,false)
        binding.lifecycleOwner = this

        lifecycleScope.launch {
            viewModel.getCartItems()
        }

        cartAdapter = CartItemsRecyclerAdapter(cartItemsList,viewModel)

        binding.cartRecycler.layoutManager = LinearLayoutManager(context)
        binding.cartRecycler.adapter = cartAdapter

        observeLiveData()

        return binding.root
    }

    private fun observeLiveData() {
        viewModel.mutableLiveCartList.observe(viewLifecycleOwner){
            if(!it.isNullOrEmpty()){
                cartItemsList.clear()
                cartItemsList.addAll(it)
                cartAdapter.notifyDataSetChanged()
            }
        }

        viewModel.mutableTotalCost.observe(viewLifecycleOwner){
            binding.cost = it
        }
    }

}