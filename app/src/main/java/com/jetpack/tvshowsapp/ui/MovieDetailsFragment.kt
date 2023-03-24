package com.jetpack.tvshowsapp.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.jetpack.tvshowsapp.R
import com.jetpack.tvshowsapp.adapter.ImageSliderAdapter
import com.jetpack.tvshowsapp.databinding.FragmentMovieDetailsBinding
import com.jetpack.tvshowsapp.model.movie_details.MovieDetailsResponseModel
import com.jetpack.tvshowsapp.network.apis.ApiListner
import com.jetpack.tvshowsapp.repository.MoviesRepository
import com.jetpack.tvshowsapp.utils.setImageURL
import com.jetpack.tvshowsapp.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailsFragment : Fragment(), ApiListner {
    private lateinit var binding: FragmentMovieDetailsBinding

    @Inject
    lateinit var apiRepository: MoviesRepository

    private val args: MovieDetailsFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMovieDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            backBtn.setOnClickListener {
                findNavController().popBackStack()
            }
            initMovieDetailsVideModel()
        }
    }

    override fun onStarted() {
        binding.moviedetailssprbr.visibility = View.VISIBLE
    }

    override fun onSuccess(movideDetails: Any) {
        val it: MovieDetailsResponseModel = movideDetails as MovieDetailsResponseModel
        binding.apply {
            moviedetailssprbr.visibility = View.GONE
            setImageURL(imgTVShow, it.tvShow.imageThumbnailPath)
            tvShowName.text = it.tvShow.name
            tvNetworkCountry.text = it.tvShow.network
            tvStatus.text = it.tvShow.status
            tvStarted.text = "Started on: " + it.tvShow.startDate
            if (it.tvShow.description.isNotEmpty()) {
                tvTxtMore.visibility = View.VISIBLE
                tvDescription.text = HtmlCompat.fromHtml(
                    it.tvShow.description, HtmlCompat.FROM_HTML_MODE_LEGACY
                )
                tvTxtMore.setOnClickListener {
                    if (tvTxtMore.text.toString().equals("Read More")) {
                        tvDescription.maxLines = Int.MAX_VALUE
                        tvDescription.ellipsize = null
                        tvTxtMore.text = "Read Less"
                    } else {
                        tvDescription.maxLines = 4
                        tvDescription.ellipsize = TextUtils.TruncateAt.END
                        tvTxtMore.text = "Read More"
                    }

                }
            } else {
                tvTxtMore.visibility = View.GONE
            }
            loadImageSlider(it.tvShow.pictures)
        }
    }

    override fun onFailure(message: String) {
        binding.moviedetailssprbr.visibility = View.GONE
        Toast.makeText(requireContext(), "Failed to load movie details", Toast.LENGTH_SHORT).show()
    }

    private fun initMovieDetailsVideModel() {
        val moviesViewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)
        moviesViewModel.apiListner = this
        moviesViewModel.getMovieDetailsById(args.movieId)
    }

    private fun loadImageSlider(pictures: List<String>) {
        binding.sliderViewPager.offscreenPageLimit = 1
        binding.sliderViewPager.adapter = ImageSliderAdapter(pictures)
        binding.sliderViewPager.visibility = View.VISIBLE
        binding.viewFadingEdge.visibility = View.VISIBLE
        setupSliderIndicators(pictures.size)
        binding.sliderViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setupCurrentSliderIndicators(position)
            }
        })
    }

    private fun setupSliderIndicators(count: Int) {
        val indicators = arrayOfNulls<ImageView>(count)
        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(8, 0, 8, 0)
        for (i in (0 until count)) {
            indicators[i] = ImageView(requireContext())
            indicators[i]?.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(), R.drawable.background_slider_indicator_inactive
                )
            )
            indicators[i]?.layoutParams = layoutParams
            binding.layoutSliderIndicator.addView(indicators[i])
        }
        binding.layoutSliderIndicator.visibility = View.VISIBLE
        setupCurrentSliderIndicators(0)
    }

    fun setupCurrentSliderIndicators(position: Int) {
        val childCount = binding.layoutSliderIndicator.childCount
        for (i in (0 until childCount)) {
            val img: ImageView = binding.layoutSliderIndicator.getChildAt(i) as ImageView
            if (i == position) {
                img.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(), R.drawable.background_slider_indicator_active
                    )
                )
            } else {
                img.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(), R.drawable.background_slider_indicator_inactive
                    )
                )
            }
        }
    }


}




