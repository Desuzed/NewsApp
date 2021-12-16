package com.desuzed.newsapp.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.desuzed.newsapp.R
import com.desuzed.newsapp.databinding.FragmentDetailedContentBinding
import com.desuzed.newsapp.databinding.FragmentGalleryBinding
import com.desuzed.newsapp.model.Article

class GalleryFragment : Fragment() {
    private lateinit var binding: FragmentGalleryBinding
    private lateinit var ivGallery: ImageView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ivGallery = binding.ivGallery
        val urlImage = arguments?.getString(URL_IMAGE)
        Glide
            .with(requireContext())
            .load(urlImage)
            .into(ivGallery)
    }
    companion object {
        const val URL_IMAGE = "url_image"
    }
}