//package com.mobulous.Adapter.programsAdptrs.buildStrengthAptrs
//
//import android.content.Context
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.google.gson.Gson
//import com.mobulous.Adapter.programsAdptrs.BootCampAptrs.BootCampParentAdptr
//import com.mobulous.Adapter.programsAdptrs.BootCampAptrs.BootCampChildAdptr
//import com.mobulous.fitscope.databinding.ParentHorizontalRvViewAllBinding
//import com.mobulous.listner.BuildStrengthViewAllListnr
//import com.mobulous.pojo.homePojo
//
//class BuildStrengthParentAdptr(
//    val con: Context,
//    val lst: ArrayList<homePojo>,
//    val lisntr: BuildStrengthViewAllListnr
//) :
//    RecyclerView.Adapter<BootCampParentAdptr.ViewHolder>() {
//    private val viewPool = RecyclerView.RecycledViewPool()
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        return ViewHolder(
//            ParentHorizontalRvViewAllBinding.inflate(
//                LayoutInflater.from(con),
//                parent,
//                false
//            )
//        )
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val childLayoutManager =
//            LinearLayoutManager(
//                holder.mView.innerHorizonRv.context,
//                LinearLayoutManager.HORIZONTAL,
//                false
//            )
//        // childLayoutManager.initialPrefetchItemCount = 4
//        holder.mView.innerHorizonRv.apply {
//            layoutManager = childLayoutManager
//            adapter =
//                BootCampChildAdptr(
//                    holder.mView.innerHorizonRv.context,
//                    lst[position].parent_object
//                )
//            setRecycledViewPool(viewPool)
//        }
//        holder.mView.lbl1.text = lst[position].parent_lbl
//        holder.mView.lbl2.setOnClickListener {
//
//            lisntr.onBuildStrengthViewAllClick(lst[position].parent_lbl, data = Gson().toJson(lst[position]))
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return lst.size
//    }
//
//    inner class ViewHolder(val mView: ParentHorizontalRvViewAllBinding) :
//        RecyclerView.ViewHolder(mView.root)
//}