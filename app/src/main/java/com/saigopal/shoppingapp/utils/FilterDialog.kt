package com.saigopal.shoppingapp.utils

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.saigopal.shoppingapp.R
import com.saigopal.shoppingapp.adapters.FilterRecyclerAdapter
import com.saigopal.shoppingapp.databinding.FilterDialogBinding
import com.saigopal.shoppingapp.models.Categories

class FilterDialog: BottomSheetDialogFragment() {

    private lateinit var binding:FilterDialogBinding
    private lateinit var filterRecyclerAdapter: FilterRecyclerAdapter
    var categoriesList: List<Categories> = mutableListOf()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener { setupBottomSheet(it) }
        return dialog
    }

    @SuppressLint("ResourceType")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),R.layout.filter_dialog,null,false)

        filterRecyclerAdapter = FilterRecyclerAdapter(categoriesList)

        binding.filterRecycler.layoutManager = LinearLayoutManager(context)
        binding.filterRecycler.adapter = filterRecyclerAdapter

        binding.fabClose.setOnClickListener{ dismiss() }

        return binding.root
    }

    private fun setupBottomSheet(dialogInterface: DialogInterface) {
        val bottomSheetDialog = dialogInterface as BottomSheetDialog
        val bottomSheet = bottomSheetDialog.findViewById<View>(
            com.google.android.material.R.id.design_bottom_sheet)
            ?: return
        bottomSheet.setBackgroundColor(Color.TRANSPARENT)
    }




}