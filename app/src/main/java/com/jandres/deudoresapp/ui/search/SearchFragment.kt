package com.jandres.deudoresapp.ui.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jandres.deudoresapp.DeudoresApp
import com.jandres.deudoresapp.R
import com.jandres.deudoresapp.data.dao.DebtorDao
import com.jandres.deudoresapp.data.entities.Debtor
import com.jandres.deudoresapp.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel
    private var _binding: FragmentSearchBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        searchViewModel =
            ViewModelProvider(this).get(SearchViewModel::class.java)

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
/*
        val textView: TextView = binding.textNotifications
        searchViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/
        binding.phoneTextView.text = getString(R.string.phone_value,"0")
        binding.amountTextView.text = getString(R.string.amount_value,"0")

        binding.searchButton.setOnClickListener {
            searchDebtors(binding.nameEditText.text.toString())
        }


        return binding.root
    }

    private fun searchDebtors(name: String) {
        val debtorDao : DebtorDao  = DeudoresApp.database.DebtorDao()
        val debtor : Debtor = debtorDao.searchDebtor(name)

        if (debtor != null){
            with(binding){
                phoneTextView.text = getString(R.string.phone_value,debtor.phone)
                amountTextView.text = getString(R.string.amount_value,debtor.amount.toString())
            }
        }
        else {
            Toast.makeText(requireContext(),"NO EXISTE",Toast.LENGTH_SHORT).show()
            with(binding){
                phoneTextView.text = getString(R.string.phone_value,"0")
                amountTextView.text = getString(R.string.amount_value,"0")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}