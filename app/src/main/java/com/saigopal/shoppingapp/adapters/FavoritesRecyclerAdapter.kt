package com.saigopal.shoppingapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.saigopal.shoppingapp.R
import com.saigopal.shoppingapp.databinding.FavoriteItemBinding
import com.saigopal.shoppingapp.models.Item
import com.saigopal.shoppingapp.viewModels.FavoriteViewModel
import kotlinx.coroutines.launch

class FavoritesRecyclerAdapter(
    private val itemList: MutableList<Item>,
    private val viewModel:FavoriteViewModel) : RecyclerView.Adapter<FavoritesRecyclerAdapter.ViewHolder>() {

    class ViewHolder(itemView: FavoriteItemBinding) : RecyclerView.ViewHolder(itemView.root) {
        val binding:FavoriteItemBinding = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val favouriteBinding : FavoriteItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),R.layout.favorite_item,parent,false
        )
        return ViewHolder(favouriteBinding)
    }

    override fun getItemCount(): Int {
       return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.item = itemList[holder.adapterPosition]
        holder.binding.removeItem.setOnClickListener {
            viewModel.viewModelScope.launch {
                viewModel.removeItemFromFavorite(itemList[holder.adapterPosition])
            }
            itemList.removeAt(holder.adapterPosition)
            notifyItemRemoved(holder.adapterPosition)
        }

        holder.binding.addToCart.setOnClickListener {
            viewModel.viewModelScope.launch {
                viewModel.addToCart(itemList[holder.adapterPosition])
            }
            Toast.makeText(holder.binding.root.context,"Added to cart",Toast.LENGTH_SHORT).show()
        }

    }
}