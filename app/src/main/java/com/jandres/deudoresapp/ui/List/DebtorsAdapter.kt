package com.jandres.deudoresapp.ui.List

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jandres.deudoresapp.R
import com.jandres.deudoresapp.data.entities.Debtor
import com.jandres.deudoresapp.databinding.CardViewDebtorsItemBinding

class DebtorsAdapter(
    private val onItemClicked : (Debtor) -> Unit,
) : RecyclerView.Adapter<DebtorsAdapter.ViewHolder>() {

    private var listDebtor : MutableList<Debtor> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_debtors_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listDebtor[position])
        holder.itemView.setOnClickListener{onItemClicked(listDebtor[position])}
    }

    override fun getItemCount(): Int {
        return listDebtor.size
    }

    fun appendItems(newItems : MutableList<Debtor>){
        listDebtor.clear()
        listDebtor.addAll(newItems)
        notifyDataSetChanged()
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        private val binding = CardViewDebtorsItemBinding.bind(view)
        fun bind(debtor: Debtor){
            with(binding){
                nameTextView.text = debtor.name
                amountTextView.text = debtor.amount.toString()
            }
        }
    }

}