package com.example.nasatestproject.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nasatestproject.databinding.LayoutItemPostBinding
import com.example.nasatestproject.interfaces.OnPostClickListener
import com.example.nasatestproject.models.NasaPost
import com.squareup.picasso.Picasso

class NasaAdapter(
    private val nasaPosts: ArrayList<NasaPost>,
    private val postClickListener: OnPostClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun updateNasaPosts(nasaPhotos: ArrayList<NasaPost>) {
        this.nasaPosts.clear()
        this.nasaPosts.addAll(nasaPhotos)
        notifyDataSetChanged()
        Log.i("NASA", "adapter has: $nasaPhotos")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NasaPostViewHolder(LayoutItemPostBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        (viewHolder as NasaPostViewHolder).bind(nasaPosts[position], postClickListener)
    }

    override fun getItemCount(): Int {
        Log.i("NASA", "posts size is: ${nasaPosts.size}")
        return nasaPosts.size
    }

    class NasaPostViewHolder(private val binding: LayoutItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(nasaPost: NasaPost, postClickListener: OnPostClickListener) {
            binding.image.setImageBitmap(null)
            val roverName = "Photo made by ${nasaPost.rover?.name}, camera ${nasaPost.camera?.name}"
            binding.author.text = roverName
            Picasso.get().load(nasaPost.imgSrc).into(binding.image)
            binding.root.setOnClickListener {
                postClickListener.onPostClick(nasaPost)
            }
        }
    }
}