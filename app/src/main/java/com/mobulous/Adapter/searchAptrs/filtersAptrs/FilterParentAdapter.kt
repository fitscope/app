package com.mobulous.Adapter.searchAptrs.filtersAptrs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.mobulous.fitscope.databinding.FilterParentRvItemLayBinding
import com.mobulous.listner.FilterListnr
import com.mobulous.pojo.search.FilterItem

class FilterParentAdapter(
    var filterLst: ArrayList<FilterItem?>, val filterLisntr: FilterListnr
) :
    RecyclerView.Adapter<FilterParentAdapter.FilterViewHolder>() {
    private val viewPool = RecyclerView.RecycledViewPool()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        return FilterViewHolder(
            FilterParentRvItemLayBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        holder.mView.parentLbl.text = filterLst[position]?.key
        val childLayoutManager = FlexboxLayoutManager(holder.itemView.context).apply {
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
            alignItems = AlignItems.STRETCH
        }
//         childLayoutManager.initialPrefetchItemCount = 4
        holder.mView.childFilterRv.apply {
            layoutManager = childLayoutManager
            adapter =
                FilterChildAdptr(
                    arrayListOf("All").union(filterLst[position]?.value ?: arrayListOf()).toList(),
                    filterLisntr
                )
            setRecycledViewPool(viewPool)
        }

    }


    override fun getItemCount(): Int {
        return filterLst.size
    }


    class FilterViewHolder(val mView: FilterParentRvItemLayBinding) :
        RecyclerView.ViewHolder(mView.root) {

    }


}