package com.saigopal.shoppingapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.saigopal.shoppingapp.R
import com.saigopal.shoppingapp.databinding.FilterItemsBinding
import com.saigopal.shoppingapp.models.Categories

class FilterRecyclerAdapter(private val filterItemsList: List<Categories>): RecyclerView.Adapter<FilterRecyclerAdapter.ViewHolder>() {


    class ViewHolder(itemView: FilterItemsBinding) : RecyclerView.ViewHolder(itemView.root) {
        val filterItemsBinding=itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val filterItemBinding:FilterItemsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.filter_items,parent,false)

        return ViewHolder(filterItemBinding)
    }

    override fun getItemCount(): Int {
        return filterItemsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.filterItemsBinding.category = filterItemsList[position]
    }
}