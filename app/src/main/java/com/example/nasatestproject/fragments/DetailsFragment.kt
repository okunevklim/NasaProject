package com.example.nasatestproject.fragments

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nasatestproject.databinding.FragmentDetailsBinding
import com.example.nasatestproject.models.NasaPost
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target


class DetailsFragment : Fragment(){
    private lateinit var binding: FragmentDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nasaPost = requireArguments().getSerializable("NASA") as NasaPost

        val targetNasaPhoto = object : Target {
            override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
              //  binding.detailsPhoto.setImage(ImageSource.uri(nasaPost.imgSrc))
            }

            override fun onBitmapFailed(p0: Exception?, p1: Drawable?) {
                Log.i("NASA", "Error in picasso loading: $p0")
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            }
        }
        Picasso.get().load(nasaPost.imgSrc).into(binding.detailsPhoto)
    }
}