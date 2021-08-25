package com.jandres.deudoresapp.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Debug
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.jandres.deudoresapp.R
import com.jandres.deudoresapp.data.entities.Debtor

class DetailFragment : Fragment() {

    private val args : DetailFragmentArgs by navArgs()

    companion object {
        fun newInstance() = DetailFragment()
    }

    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        val debtor : Debtor = args.debtor
        Toast.makeText(requireContext(),debtor.name + " me debe " + debtor.amount.toString(),Toast.LENGTH_SHORT ).show()
    }

}