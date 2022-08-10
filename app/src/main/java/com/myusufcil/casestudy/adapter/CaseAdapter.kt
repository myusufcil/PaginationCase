package com.myusufcil.casestudy.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.myusufcil.casestudy.databinding.ItemPersonBinding
import com.myusufcil.casestudy.response.Person

class CaseAdapter :
    PagingDataAdapter<Person, CaseAdapter.PersonViewHolder>(PlayersDiffCallback) {

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder =
        PersonViewHolder(
            ItemPersonBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    inner class PersonViewHolder(
        private val binding: ItemPersonBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Person?) {
            with(binding) {
                data?.let { playerItem ->
                    tvPersonFullName.text = playerItem.fullName
                    tvId.text = playerItem.id.toString()
                }
            }
        }
    }

    private object PlayersDiffCallback : DiffUtil.ItemCallback<Person>() {
        override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
            return oldItem == newItem
        }
    }
}