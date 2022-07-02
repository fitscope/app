//package com.mobulous.adapter.ClassesAdptrs.home
//
//import android.content.Context
//import android.content.Intent
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.google.gson.Gson
//import com.mobulous.Adapter.programsAdptrs.BootCampAptrs.BootCampChildAdptr
//import com.mobulous.fitscope.activity.auth.BaseActivity
//import com.mobulous.fitscope.activity.video.AboutVideoActivity
//import com.mobulous.fitscope.databinding.ChildHorizontalRvBinding
//import com.mobulous.helper.*
//import com.mobulous.pojo.ChildHorizontalDataItem
//import com.mobulous.pojo.homePojo
//
//class BuildStrengthChildAdptr(val con: Context, val lst: ArrayList<ChildHorizontalDataItem>) :
//    RecyclerView.Adapter<BootCampChildAdptr.ViewHolder>() {
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): ViewHolder {
//        return ViewHolder(
//            ChildHorizontalRvBinding.inflate(
//                LayoutInflater.from(con),
//                parent,
//                false
//            )
//        )
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.mView.imgLbl.text = lst[position].name
//        holder.mView.textView36.text =
//            Uitls.getTimeStampHMS(if (lst[position].duration!="null") lst[position].duration.toInt() else 0)
//        holder.mView.imgView.loadNormalPhoto_Dimens300(lst[position].image_url)
//        holder.itemView.setOnClickListener {
//
//
//            if (PrefUtils.with(con).getString(Enums.isLogin.toString(), "false") == "true") {
//                con.startActivity(
//                    Intent(con, AboutVideoActivity::class.java).putExtra(
//                        Constants.Data,
//                        Gson().toJson(homePojo("", arrayListOf(lst[position])))
//                    )
//                )
//            } else {
//                con.startActivity(
//                    Intent(con, BaseActivity::class.java).putExtra(
//                        Constants.From, Enums.NORMAL.toString()
//                    )
//                )
//            }
//
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return lst.size
//    }
//
//    inner class ViewHolder(val mView: ChildHorizontalRvBinding) :
//        RecyclerView.ViewHolder(mView.root)
//}