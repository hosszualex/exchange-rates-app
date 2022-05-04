package com.example.exchangeratesapp.mainscreen

import android.app.Activity
import android.app.AlertDialog
import android.view.LayoutInflater
import com.example.exchangeratesapp.R
import com.example.exchangeratesapp.databinding.DialogLoadingBinding

class LoadingDialog(private val activity: Activity) {
    private var dialog: AlertDialog? = null
    private lateinit var binding : DialogLoadingBinding

    fun startDialog() {
        val builder = AlertDialog.Builder(activity, R.style.LoadingDialog)
        binding = DialogLoadingBinding.inflate(LayoutInflater.from(activity))
        builder.setView(binding.root)
        builder.setCancelable(false)
        dialog = builder.create()
        dialog?.show()
    }

    fun dismissDialog() {
        dialog?.dismiss()
    }
}