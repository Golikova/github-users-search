package com.example.github_user_search.ui

import android.R
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.github_user_search.data.entity.User
import com.example.github_user_search.databinding.ListItemBinding
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item.view.*


class RecyclerViewAdapter(private var items: List<User>,
                          private var listener: OnItemClickListener)
    : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val binding = ListItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    fun replaceData(items: List<User>) {
        this.items = items
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], listener)
    }

    override fun getItemCount(): Int = items.size

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    class ViewHolder(private var binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User, listener: OnItemClickListener?) {
            binding.user = user

            if (user.avatar_url != null && !user.avatar_url.isNullOrEmpty()) {
                Picasso.with(itemView.context).load(user.avatar_url).into(itemView.avatar)
            }

            if (listener != null) {
                binding.root.setOnClickListener({ _ -> listener.onItemClick(layoutPosition) })
            }

            binding.executePendingBindings()
        }

    }

}

