package com.jandres.deudoresapp.ui.update

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jandres.deudoresapp.databinding.FragmentUpdateBinding
import com.jandres.deudoresapp.ui.search.SearchViewModel

class UpdateFragment : Fragment() {

    private lateinit var updateViewModel: SearchViewModel
    private var _binding: FragmentUpdateBinding ? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        updateViewModel =
            ViewModelProvider(this).get(SearchViewModel::class.java)

        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        /*
        val textView: TextView = binding.textNotifications
        updateViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}