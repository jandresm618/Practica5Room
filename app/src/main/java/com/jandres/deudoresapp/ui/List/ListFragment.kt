package com.jandres.deudoresapp.ui.List

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jandres.deudoresapp.DeudoresApp
import com.jandres.deudoresapp.data.dao.DebtorDao
import com.jandres.deudoresapp.data.entities.Debtor
import com.jandres.deudoresapp.databinding.FragmentListBinding

class ListFragment : Fragment() {

    private lateinit var listViewModel: ListViewModel
    private var _binding: FragmentListBinding? = null
    private lateinit var debtorsAdapter: DebtorsAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        listViewModel =
            ViewModelProvider(this).get(ListViewModel::class.java)

        _binding = FragmentListBinding.inflate(inflater, container, false)
/*
        val textView: TextView = binding.textHome
        listViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/

        debtorsAdapter = DebtorsAdapter(onItemClicked = {onDebtorItemClicked(it)})
        binding.debtorRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@ListFragment.context)
            adapter = debtorsAdapter
            setHasFixedSize(false)
        }

        val debtorDAO : DebtorDao = DeudoresApp.database.DebtorDao()
        val listDebtors : MutableList<Debtor> = debtorDAO.getDebtors()
        debtorsAdapter.appendItems(listDebtors)

        return binding.root
    }

    private fun onDebtorItemClicked(debtor: Debtor) {
        findNavController().navigate(ListFragmentDirections.actionNavigationListToDetailFragment(debtor = debtor))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}