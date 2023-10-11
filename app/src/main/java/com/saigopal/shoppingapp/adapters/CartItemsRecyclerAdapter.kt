package com.saigopal.shoppingapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.saigopal.shoppingapp.R
import com.saigopal.shoppingapp.databinding.CartItemBinding
import com.saigopal.shoppingapp.models.CartData
import com.saigopal.shoppingapp.viewModels.CartViewModel
import kotlinx.coroutines.launch

class CartItemsRecyclerAdapter(
    private var mutableCartList: MutableList<CartData>,
    private var viewModel:CartViewModel) : RecyclerView.Adapter<CartItemsRecyclerAdapter.ViewHolder>() {

    class ViewHolder(itemView: CartItemBinding) : RecyclerView.ViewHolder(itemView.root) {
        val binding = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cartItemBinding:CartItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),R.layout.cart_item,parent,false
        )
       return ViewHolder(cartItemBinding)
    }

    override fun getItemCount(): Int {
        return mutableCartList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemQuantity = mutableCartList[holder.adapterPosition].cartItem.quantity

        holder.binding.quantity = itemQuantity
        holder.binding.item = mutableCartList[holder.adapterPosition].item


        holder.binding.increase.setOnClickListener {
            viewModel.viewModelScope.launch {
                viewModel.increaseCartItemQuantity(mutableCartList[holder.adapterPosition].cartItem)
                holder.binding.quantity = itemQuantity+1
                notifyItemChanged(holder.adapterPosition)
            }
        }

        holder.binding.decrease.setOnClickListener {
            viewModel.viewModelScope.launch {
                viewModel.decreaseCartItemQuantity(mutableCartList[holder.adapterPosition].cartItem)
                if(itemQuantity == 1){
                    mutableCartList.removeAt(holder.adapterPosition)
                    notifyItemRemoved(holder.adapterPosition)
                }else{
                    holder.binding.quantity = itemQuantity-1
                    notifyItemChanged(holder.adapterPosition)
                }

            }
        }
    }
}