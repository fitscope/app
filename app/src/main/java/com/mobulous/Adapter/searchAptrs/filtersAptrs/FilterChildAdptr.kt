package com.mobulous.Adapter.searchAptrs.filtersAptrs

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mobulous.fitscope.R
import com.mobulous.fitscope.databinding.FilterChildRvDataItemLayBinding
import com.mobulous.helper.Constants
import com.mobulous.helper.Enums
import com.mobulous.listner.FilterListnr

class FilterChildAdptr(
    val lst: List<String?>,
    val filter: FilterListnr
) :
    RecyclerView.Adapter<FilterChildAdptr.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            FilterChildRvDataItemLayBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    var cilkItem: Boolean = true
    private var selectionLst = arrayListOf<String>(Enums.All.toString())

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mView.childFilterLbl.text = lst[position]
        holder.mView.childFilterLbl.setOnClickListener {
            when (holder.mView.childFilterLbl.text.toString()) {
                Enums.All.toString() -> {
                    Constants.AllNextValue = lst[position + 1].toString()
                    selectionLst.clear()
                    notifyDataSetChanged()
                    selectionLst.add(holder.mView.childFilterLbl.text.toString())
                    holder.mView.childFilterLbl.setBackgroundResource(
                        R.drawable.filter_selected_bg
                    )
                    holder.mView.childFilterLbl.setTextColor(
                        ContextCompat.getColor(
                            holder.itemView.context,
                            R.color.white
                        )
                    )
                }
                else -> {
                    if (selectionLst.contains(holder.mView.childFilterLbl.text.toString())) {
                        selectionLst.remove(holder.mView.childFilterLbl.text.toString())
                    } else {
                        selectionLst.add(holder.mView.childFilterLbl.text.toString())
                    }
                    selectionLst.removeIf { it == Enums.All.toString() }
                    println("--selectionLst-->${selectionLst}")
                    notifyDataSetChanged()
                }
            }

            filter.onFilterSelect(holder.mView.childFilterLbl.text.toString())


        }

        holder.mView.childFilterLbl.setBackgroundResource(
            if (selectionLst.contains(holder.mView.childFilterLbl.text))
                R.drawable.filter_selected_bg
            else R.drawable.bordered_bg
        )
        holder.mView.childFilterLbl.setTextColor(
            ContextCompat.getColor(
                holder.itemView.context,
                if (selectionLst.contains(holder.mView.childFilterLbl.text))
                    R.color.white
                else R.color.new_black
            )
        )


    }

    fun resetSelection() {
        selectionLst.clear()
        selectionLst.add(Enums.All.toString())
    }

    override fun getItemCount(): Int {
        return lst.size
    }

    inner class ViewHolder(val mView: FilterChildRvDataItemLayBinding) :
        RecyclerView.ViewHolder(mView.root)
}