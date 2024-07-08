package com.example.techexactly.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.techexactly.databinding.LayoutApplicationsItemBinding
import com.example.techexactly.models.App

class ApplicationsAdapter : RecyclerView.Adapter<ApplicationsAdapter.ApplicationsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApplicationsViewHolder {
        return ApplicationsViewHolder(
            LayoutApplicationsItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ApplicationsViewHolder, position: Int) {
        val application = differ.currentList[position]

        holder.binding.apply {
            Glide.with(ivIcon).load(application.app_icon).diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivIcon)
            tvTitle.text = application.app_name

            swStatus.isChecked = application.status.lowercase() == "active"
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val diffUtil = object : DiffUtil.ItemCallback<App>() {
        override fun areItemsTheSame(oldItem: App, newItem: App): Boolean {
            return oldItem.app_id == newItem.app_id
        }

        override fun areContentsTheSame(oldItem: App, newItem: App): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffUtil)

    inner class ApplicationsViewHolder(val binding: LayoutApplicationsItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}