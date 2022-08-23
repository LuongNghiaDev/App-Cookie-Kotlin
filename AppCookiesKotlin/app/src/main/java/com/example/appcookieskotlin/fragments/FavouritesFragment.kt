package com.example.appcookieskotlin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.appcookieskotlin.R
import com.example.appcookieskotlin.activities.MainActivity
import com.example.appcookieskotlin.adapters.FavouritesMealsAdapter
import com.example.appcookieskotlin.databinding.FragmentFavouritesBinding
import com.example.appcookieskotlin.databinding.FragmentHomeBinding
import com.example.appcookieskotlin.viewModel.HomeViewModel
import com.google.android.material.snackbar.Snackbar

class FavouritesFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var favouritesAdapter: FavouritesMealsAdapter
    private lateinit var binding: FragmentFavouritesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        prepareRecyclerView()
        observeFavorites()

        val itemTouchHelper = object :ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val positon = viewHolder.adapterPosition
                val deletedMeal = favouritesAdapter.differ.currentList[positon]
                viewModel.deleteMeal(deletedMeal)

                Snackbar.make(requireView(), "Meal Delete", Snackbar.LENGTH_LONG).setAction(
                    "Undo",
                    View.OnClickListener {
                        viewModel.insertMeal(deletedMeal)
                    }
                ).show()
            }
        }

        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvFavourites)
    }

    private fun prepareRecyclerView() {
        favouritesAdapter = FavouritesMealsAdapter()
        binding.rvFavourites.apply {
            layoutManager = GridLayoutManager(context, 2 , GridLayoutManager.VERTICAL, false)
            adapter = favouritesAdapter
        }
    }

    private fun observeFavorites() {
        viewModel.observeFavouritesLiveData().observe(requireActivity(), Observer { meals ->
            favouritesAdapter.differ.submitList(meals)
        })
    }
}