package com.saigopal.shoppingapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saigopal.shoppingapp.R
import com.saigopal.shoppingapp.databinding.CategoriesItemBinding
import com.saigopal.shoppingapp.models.Categories
import com.saigopal.shoppingapp.viewModels.HomeViewModel

class HomeRecyclerAdapter(
    private var list: List<Categories>,
    private var viewModel: HomeViewModel) :
    RecyclerView.Adapter<HomeRecyclerAdapter.ViewHolder>() {


    class ViewHolder(itemView: CategoriesItemBinding) : RecyclerView.ViewHolder(itemView.root) {
        var binding:CategoriesItemBinding = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
         val item : CategoriesItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
             R.layout.categories_item,parent,false)
        return ViewHolder(item)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.category = list[position]

        holder.binding.itemsRecycler.layoutManager = LinearLayoutManager(
            holder.binding.root.context,
            LinearLayoutManager.HORIZONTAL,
            false)

        holder.binding.itemsRecycler.adapter = ProductsRecycleAdapter(list[position].items,viewModel)

    }
}