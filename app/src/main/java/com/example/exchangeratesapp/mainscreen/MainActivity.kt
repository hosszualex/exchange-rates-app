package com.example.exchangeratesapp.mainscreen

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.exchangeratesapp.R
import com.example.exchangeratesapp.databinding.ActivityMainBinding
import com.example.exchangeratesapp.model.ErrorResponse
import com.example.exchangeratesapp.model.ExchangeRates

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ExchangePairsAdapter
    private lateinit var loadingDialog: LoadingDialog

    private val onGetExchangePairs = Observer<List<ExchangeRates>> { exchangePairs ->
        if (!this::adapter.isInitialized) {
            initializeAdapter()
        }
        adapter.setDataSource(exchangePairs)
    }

    private fun initializeAdapter() {
        adapter = ExchangePairsAdapter()
        binding.rvExchangePairs.adapter = adapter
    }

    private val onIsBusy = Observer<Boolean> { isBusy ->
        if (isBusy) {
            loadingDialog.startDialog()
        } else {
            loadingDialog.dismissDialog()
        }
    }

    private val onError = Observer<ErrorResponse> { error ->
        Toast.makeText(this, "Error Message: " + error.errorMessage + "\nError Code: " + error.errorCode, Toast.LENGTH_LONG).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeViewModel()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        loadingDialog = LoadingDialog(this)
    }

    private fun initializeViewModel() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.onGetExchangePairs.observe(this, onGetExchangePairs)
        viewModel.isBusy.observe(this, onIsBusy)
        viewModel.onError.observe(this, onError)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getExchangeRates()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }
}