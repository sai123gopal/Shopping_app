package com.saigopal.shoppingapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.saigopal.shoppingapp.R
import com.saigopal.shoppingapp.databinding.FragmentFavouriteBinding

class FavouriteFragment : Fragment() {

    private lateinit var binding:FragmentFavouriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(LayoutInflater.from(container!!.context),R.layout.fragment_favourite,container,false)


        return binding.root
    }

}