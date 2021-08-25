package com.jandres.deudoresapp.ui.create

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
import com.jandres.deudoresapp.data.dao.DebtorDao
import com.jandres.deudoresapp.data.entities.Debtor
import com.jandres.deudoresapp.databinding.FragmentCreateBinding
import java.sql.Types.NULL

class CreateFragment : Fragment() {

    private lateinit var createViewModel: CreateViewModel
    private var _binding: FragmentCreateBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        createViewModel =
            ViewModelProvider(this).get(CreateViewModel::class.java)

        _binding = FragmentCreateBinding.inflate(inflater, container, false)
/*
        val textView: TextView = binding.textDashboard
        createViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/

        with(binding){
            createButton.setOnClickListener {
                val name : String = nameEditText.text.toString()
                val phone : String = phoneEditText.text.toString()
                val amount = amountEditText.text.toString().toLong()

                if (validateData(name,phone,amount)) createDebtor(name,phone,amount)
                else TODO("Make Text")
            }
        }


        return binding.root
    }

    private fun validateData(name: String, phone: String, amount: Long): Boolean {
        return name.isNotEmpty() and phone.isNotEmpty() and !amount.equals(0)
    }
    private fun createDebtor(name: String, phone: String, amount: Long): Boolean {
        val debtor = Debtor(id = NULL, name = name, phone = phone, amount = amount)
        val debtorDAO : DebtorDao = DeudoresApp.database.DebtorDao()

        debtorDAO.createDebtor(debtor)

        cleanViews()

        return true
    }

    private fun cleanViews() {
        with(binding){
            nameEditText.setText("")
            phoneEditText.setText("")
            amountEditText.setText("")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}