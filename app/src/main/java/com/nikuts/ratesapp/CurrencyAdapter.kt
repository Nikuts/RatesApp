package com.nikuts.ratesapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nikuts.ratesapp.databinding.ItemCurrencyBinding

class CurrencyAdapter: RecyclerView.Adapter<CurrencyAdapter.ViewHolder>() {

    var items = ArrayList<CurrencyItem>()
        private set

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCurrencyBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    fun update(newItems: List<CurrencyItem>) {
        if (items.isEmpty()) {
            items = ArrayList(newItems)
            notifyDataSetChanged()
        } else {
            val diffResult = DiffUtil.calculateDiff(DiffCallback(items, newItems))
            items = ArrayList(newItems)
            diffResult.dispatchUpdatesTo(this)
        }
    }

    inner class ViewHolder(val binding: ItemCurrencyBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CurrencyItem) {
            binding.item = item
            binding.executePendingBindings()
        }
    }

    class DiffCallback( val oldList: List<CurrencyItem>, val newList: List<CurrencyItem>) : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition].title == newList[newItemPosition].title

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            areItemsTheSame(oldItemPosition, newItemPosition) &&
                    oldList[oldItemPosition].rate == newList[newItemPosition].rate &&
                    oldList[oldItemPosition].amount == newList[newItemPosition].amount &&
                    oldList[oldItemPosition].editable == newList[newItemPosition].editable

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

    }
}