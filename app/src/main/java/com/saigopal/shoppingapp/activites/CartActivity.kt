package com.saigopal.shoppingapp.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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
    private lateinit var cartViewModel: CartViewModel
    private val cartItemsList:MutableList<CartData> = mutableListOf()
    private lateinit var cartAdapter: CartItemsRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_cart)
        binding.lifecycleOwner = this

        cartViewModel = ViewModelProvider(this)[CartViewModel::class.java]
        binding.viewModel = cartViewModel
        binding.status = "Cart is empty"
        lifecycleScope.launch {
            cartViewModel.getCartItems()
        }

        cartAdapter = CartItemsRecyclerAdapter(cartItemsList,cartViewModel)

        binding.cartRecycler.layoutManager = LinearLayoutManager(this)
        binding.cartRecycler.adapter = cartAdapter

        binding.back.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
        binding.checkoutCard.setOnClickListener {
            lifecycleScope.launch {
                cartViewModel.clearCart()
                cartViewModel.getCartItems()
                Toast.makeText(this@CartActivity,"Order placed",Toast.LENGTH_SHORT).show()
            }
            binding.animationView.setAnimation(R.raw.order_placed)
            binding.status = "Order placed"
        }

        observeLiveData()

    }


    private fun observeLiveData() {
        cartViewModel.mutableLiveCartList.observe(this){
            if(!it.isNullOrEmpty()){
                cartItemsList.clear()
                cartItemsList.addAll(it)
                cartAdapter.notifyDataSetChanged()
            }
        }
        cartViewModel.cartItemsList.observe(this){
            lifecycleScope.launch {
                cartViewModel.getCartItems()
            }
        }
    }

}