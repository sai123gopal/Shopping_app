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
import com.saigopal.shoppingapp.adapters.CartItemsRecyclerAdapter
import com.saigopal.shoppingapp.databinding.ActivityCartBinding
import com.saigopal.shoppingapp.models.CartData
import com.saigopal.shoppingapp.viewModels.CartViewModel
import kotlinx.coroutines.launch

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var viewModel: CartViewModel
    private val cartItemsList:MutableList<CartData> = mutableListOf()
    private lateinit var cartAdapter: CartItemsRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_cart)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this)[CartViewModel::class.java]


        lifecycleScope.launch {
            viewModel.getCartItems()
        }

        cartAdapter = CartItemsRecyclerAdapter(cartItemsList,viewModel)

        binding.cartRecycler.layoutManager = LinearLayoutManager(this)
        binding.cartRecycler.adapter = cartAdapter

        binding.back.setOnClickListener { onBackPressedDispatcher.onBackPressed() }

        observeLiveData()

    }


    private fun observeLiveData() {
        viewModel.mutableLiveCartList.observe(this){
            if(!it.isNullOrEmpty()){
                cartItemsList.clear()
                cartItemsList.addAll(it)
                cartAdapter.notifyDataSetChanged()
                binding.emptyCart.visibility = View.GONE
            }else{
                binding.emptyCart.visibility = View.VISIBLE
            }
        }

        viewModel.mutableTotalCost.observe(this){
            binding.cost = it
        }
    }

}