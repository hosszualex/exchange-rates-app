package com.example.exchangeratesapp.mainscreen

import androidx.recyclerview.widget.DiffUtil
import com.example.exchangeratesapp.model.ExchangeRates

class ExchangeRatesDiffUtil
    (
    private val oldList: List<ExchangeRates>,
    private val newList: List<ExchangeRates>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.count()
    }

    override fun getNewListSize(): Int {
        return newList.count()
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].rate == newList[newItemPosition].rate
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

}