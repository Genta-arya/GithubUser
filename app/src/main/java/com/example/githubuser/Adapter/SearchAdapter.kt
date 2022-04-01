package com.example.githubuser.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.githubuser.Data.DataUserSearch
import com.example.githubuser.databinding.ItemSearchBinding

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.ListViewHolder>() {

    private val listData = ArrayList<DataUserSearch>()
    private  var onItemClickCallback : onItemClickCall? = null

    fun setonItemClick(onItemClickCall: onItemClickCall){
        this.onItemClickCallback = onItemClickCall
    }

    fun setDataList(Data: ArrayList<DataUserSearch>){
        listData.clear()
        listData.addAll(Data)
        notifyDataSetChanged()

    }

    inner class ListViewHolder(val binding: ItemSearchBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(user : DataUserSearch){
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(user)
            }
            binding.apply {
                Glide.with(itemView)
                    .load(user.avatar_url)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .circleCrop()
                    .into(imgPhotoh)
                tvNameSearch.text = user.login

            }


        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchAdapter.ListViewHolder {
        val view = ItemSearchBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ListViewHolder((view))

    }

    override fun onBindViewHolder(holder: SearchAdapter.ListViewHolder, position: Int) {
        holder.bind(listData[position])

    }

    override fun getItemCount(): Int = listData.size

    interface  onItemClickCall{
        fun onItemClicked(user: DataUserSearch)
    }


}
