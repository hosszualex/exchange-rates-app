package com.example.exchangeratesapp.mainscreen

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.exchangeratesapp.databinding.ListItemExchangePairBinding
import com.example.exchangeratesapp.model.ExchangeRates

class ExchangePairsAdapter : RecyclerView.Adapter<ExchangePairsAdapter.ViewHolder>() {
    private var items: List<ExchangeRates> = ArrayList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExchangePairsAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemExchangePairBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExchangePairsAdapter.ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun setDataSource(newItems: List<ExchangeRates>) {
        val diffUtil = ExchangeRatesDiffUtil(this.items, newItems)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        this.items = newItems
        diffResults.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(private val binding: ListItemExchangePairBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ExchangeRates) {
            binding.item = item
            binding.etFromNumber.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(edidText: Editable) {
                    val fromCurrencyNumber: Float = edidText.toString().toFloatOrNull() ?: 0f
                    binding.tvToNumber.text = String.format("%.2f", fromCurrencyNumber * item.rate.toFloat())
                }
            }
            )
        }
    }
}