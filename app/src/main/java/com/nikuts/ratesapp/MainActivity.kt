package com.nikuts.ratesapp

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.nikuts.ratesapp.databinding.ActivityMainBinding
import com.nikuts.ratesapp.network.ResponseError

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel
    lateinit var binding: ActivityMainBinding

    val adapter = CurrencyAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main).apply {
            lifecycleOwner = this@MainActivity
            model = viewModel
            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            recyclerView.adapter = adapter
        }

        viewModel.items.observe(this, itemsObserver())
        viewModel.error.observe(this, errorObserver())
        viewModel.scrollEvent.observe(this, scrollEventObserver())
    }

    private fun itemsObserver() = Observer<ArrayList<CurrencyItem>> {
        adapter.update(it)
    }

    private fun errorObserver() = Observer<ResponseError> {

        val message = when (it) {
            ResponseError.PARSING -> getString(R.string.parsing_error)
            else -> getString(R.string.general_error)
        }

        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton(R.string.general_ok) { _, _ ->  }
            .create()
            .show()
    }

    private fun scrollEventObserver() = Observer<Unit> {
        binding.recyclerView.layoutManager?.scrollToPosition(0)
    }
}
