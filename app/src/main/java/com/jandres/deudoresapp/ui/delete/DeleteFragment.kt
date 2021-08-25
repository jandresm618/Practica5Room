package com.jandres.deudoresapp.ui.delete

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.jandres.deudoresapp.DeudoresApp
import com.jandres.deudoresapp.R
import com.jandres.deudoresapp.data.dao.DebtorDao
import com.jandres.deudoresapp.data.entities.Debtor
import com.jandres.deudoresapp.databinding.FragmentDeleteBinding
import com.jandres.deudoresapp.ui.search.SearchViewModel
import kotlinx.coroutines.NonCancellable.cancel

class DeleteFragment : Fragment() {

    private lateinit var deleteViewModel: DeleteViewModel
    private var _binding: FragmentDeleteBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        deleteViewModel =
            ViewModelProvider(this).get(DeleteViewModel::class.java)

        _binding = FragmentDeleteBinding.inflate(inflater, container, false)
        /*
        val textView: TextView = binding.textNotifications
        updateViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/

        binding.deleteButton.setOnClickListener {
            deleteDebtor(binding.nameEditText.text.toString())
        }

        return binding.root
    }

    private fun deleteDebtor(name: String) {
        val debtorDao : DebtorDao = DeudoresApp.database.DebtorDao()
        val debtor : Debtor = debtorDao.searchDebtor(name)

        if (debtor != null){
            val alertDialog: AlertDialog? = activity.let {
                val builder = AlertDialog.Builder(it)
                builder.apply {
                    setTitle(R.string.title_delete)
                    setMessage("¿Desea Eliminar a "+debtor.name+", con deuda de "+debtor.amount.toString()+"?")
                    setPositiveButton(R.string.accept){dialog, id ->
                        Toast.makeText(requireContext(),"DEUDOR ELIMINADO",Toast.LENGTH_SHORT).show()
                        binding.nameEditText.setText("")
                        debtorDao.deleteDebtor(debtor)
                    }
                    setNegativeButton(R.string.cancel){dialog, id ->
                    }

                }
                builder.create()

            }
            alertDialog?.show()



        }else{
            Toast.makeText(requireContext(),"NO EXISTE",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}