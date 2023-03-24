package com.jetpack.tvshowsapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.jetpack.tvshowsapp.adapter.LoadMoreAdapter
import com.jetpack.tvshowsapp.adapter.PagingMoviesAdapter
import com.jetpack.tvshowsapp.databinding.FragmentMoviesBinding
import com.jetpack.tvshowsapp.repository.MoviesRepository
import com.jetpack.tvshowsapp.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MoviesFragment : Fragment() {



    lateinit var binding: FragmentMoviesBinding
    private lateinit var moviesViewModel: MoviesViewModel

    @Inject
    lateinit var apiRepository: MoviesRepository

    @Inject
    lateinit var moviesAdapter: PagingMoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoviesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMoviesViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    private fun initMoviesViewModel() {
         moviesViewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)
        binding.moviesprbr.visibility = View.GONE
        lifecycleScope.launch {
            moviesViewModel.moviesResponse.collect {
                moviesAdapter.submitData(it)
            }
        }
        binding.movierecy.adapter = moviesAdapter
        binding.movierecy.adapter = moviesAdapter.withLoadStateFooter(
            LoadMoreAdapter {
                moviesAdapter.retry()
            }
        )
        moviesAdapter.setOnClickListner {
            val direction =
                MoviesFragmentDirections.actionMoviesFragmentToMovieDetailsFragment(
                    it.id
                )
            findNavController().navigate(direction)
        }
    }




}
