package com.compose.eventapps.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.compose.eventapps.data.response.ListEventsItem
import com.compose.eventapps.databinding.ItemRowEventBinding

class ListEventsAdapter (private val listEvent: ArrayList<ListEventsItem>) : RecyclerView.Adapter<ListEventsAdapter.ListViewHolder>() {

  private lateinit var onItemClickCallback: OnItemClickCallback

  fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
    this.onItemClickCallback = onItemClickCallback
  }

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): ListViewHolder {
    val binding = ItemRowEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return ListViewHolder(binding)
  }

  override fun getItemCount(): Int  = listEvent.size

  override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
    val mediaCover = listEvent[position].mediaCover
    val name = listEvent[position].name
    Glide.with(holder.itemView.context)
      .load(mediaCover)
      .into(holder.img)
    holder.tvTitle.text = name
    holder.itemView.setOnClickListener {
      onItemClickCallback.onItemClicked(listEvent[holder.adapterPosition])
    }
  }

  class ListViewHolder(binding: ItemRowEventBinding) : RecyclerView.ViewHolder(binding.root) {
    val img: ImageView = binding.imgBanner
    val tvTitle: TextView = binding.tvTitle
  }

  interface OnItemClickCallback {
    fun onItemClicked(data: ListEventsItem)
  }

}