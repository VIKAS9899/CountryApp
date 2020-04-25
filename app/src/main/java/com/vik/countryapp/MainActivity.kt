package com.vik.countryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.vik.countryapp.adapters.CountryListAdapter
import com.vik.countryapp.network.Resource
import com.vik.countryapp.viewModels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.error_layout.*
import java.net.ConnectException
import android.app.Activity
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged


class MainActivity : AppCompatActivity() {

    private lateinit var mViewModel: MainViewModel
    private lateinit var mListAdapter: CountryListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        initViews()
        observeLiveData()
    }

    private fun observeLiveData() {
        mViewModel.fetchCountryLiveData.observe(this, Observer {
            when (it) {
                is Resource.Loading -> {
                    progressBar?.visibility = View.VISIBLE
                    errorLayout?.visibility = View.GONE
                }
                is Resource.Success -> {
                    progressBar?.visibility = View.GONE
                    mainLayout?.visibility = View.VISIBLE
                    mListAdapter.dispatchToAdapter(it.data ?: arrayListOf(), mViewModel.flagMap)
                }
                is Resource.Error -> {
                    progressBar?.visibility = View.GONE
                    errorLayout?.visibility = View.VISIBLE
                    handleError(it.error)
                }
            }
        })
    }

    private fun handleError(error: Throwable?) {
        val errorMessage = when (error) {
            is ConnectException -> {
                "Connection Error, try again"
            }
            else -> {
                "Something went wrong, try again"
            }
        }
        errorTitle?.text = errorMessage
    }

    private fun initViews() {

        retryBtn?.setOnClickListener { mViewModel.fetchCountryList() }
        mListAdapter = CountryListAdapter()
        countryRV?.adapter = mListAdapter


        countryCodeEt?.setText(mViewModel.query)
        countryCodeEt?.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                mViewModel.searchText(s?.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })
    }

    private val _queryListener = object : SearchView.OnQueryTextListener {

        override fun onQueryTextSubmit(query: String?): Boolean {
            hideKeyboard()
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {

            return true
        }

    }

    fun hideKeyboard() {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view: View? = currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(this)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
