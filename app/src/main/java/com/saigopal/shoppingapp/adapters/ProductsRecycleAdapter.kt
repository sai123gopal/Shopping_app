package com.saigopal.shoppingapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.saigopal.shoppingapp.R
import com.saigopal.shoppingapp.databinding.ProductItemBinding
import com.saigopal.shoppingapp.models.Item
import com.saigopal.shoppingapp.viewModels.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductsRecycleAdapter(
    private val itemList: List<Item>,
    private var viewModel: MainViewModel) : RecyclerView.Adapter<ProductsRecycleAdapter.ViewHolder>() {

    class ViewHolder(itemView: ProductItemBinding) : RecyclerView.ViewHolder(itemView.root) {
        var productItemBinding:ProductItemBinding=itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val productItemBinding : ProductItemBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.product_item,parent,false)
        return ViewHolder(productItemBinding)
    }

    override fun getItemCount(): Int {
       return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.productItemBinding.item = itemList[holder.adapterPosition]
        holder.productItemBinding.favouriteImage.setOnClickListener {
            viewModel.viewModelScope.launch {
                favorite(itemList[holder.adapterPosition],holder.adapterPosition)
            }
        }
        holder.productItemBinding.addToCart.setOnClickListener {
            viewModel.viewModelScope.launch {
                viewModel.addToCart(itemList[holder.adapterPosition])
            }
            Toast.makeText(holder.productItemBinding.root.context,"Added to cart", Toast.LENGTH_SHORT).show()
        }

    }

    private suspend fun favorite(item: Item, index:Int) {
        if (item.isFavorite) {
            viewModel.removeFromFavorite(item)
            item.isFavorite = false
        } else {
            viewModel.addToFavorite(item)
            item.isFavorite = true
        }
        withContext(Dispatchers.Main) {
            notifyItemChanged(index)
        }
    }

}