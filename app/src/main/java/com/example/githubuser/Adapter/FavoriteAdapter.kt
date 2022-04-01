package com.example.githubuser.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.Data.DiffCallback
import com.example.githubuser.Room.FvUser
import com.example.githubuser.databinding.UserFavoriteBinding


class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavUserViewHolder>() {
    private val listFavUser = ArrayList<FvUser>()
    private lateinit var onItemClickCallback : OnItemClickCallback

    fun setListFavUsers(listNotes: List<FvUser>) {
        val diffCallback = DiffCallback(this.listFavUser, listNotes)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavUser.clear()
        this.listFavUser.addAll(listNotes)
        diffResult.dispatchUpdatesTo(this)


    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavUserViewHolder {
        val binding =UserFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavUserViewHolder(binding)
    }
    override fun onBindViewHolder(holder: FavUserViewHolder, position: Int) {
        holder.bind(listFavUser[position])
    }
    override fun getItemCount(): Int {
        return listFavUser.size
    }
    inner class FavUserViewHolder(private val binding: UserFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favoriteUser: FvUser) {
            with(binding) {
                tvNameFav.text = favoriteUser.login
                Glide.with(itemView)
                    .load(favoriteUser.avatar_url)
                    .circleCrop()
                    .into(imgPhotohFav)
                cardViewFav.setOnClickListener {
                    onItemClickCallback.onItemClicked(favoriteUser)
                }
            }
        }
    }

    fun setOnClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback{
        fun onItemClicked(data: FvUser)
    }
}
