package com.mobulous.Adapter

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.*
import android.widget.TextView
import androidx.databinding.DataBindingUtil.setContentView
import androidx.recyclerview.widget.RecyclerView

import com.mobulous.fitscope.R
import com.mobulous.fitscope.activity.BlutoothDevice.FoundDeviceActivity
import com.mobulous.fitscope.activity.profile.ProfileActivity
import com.mobulous.fitscope.databinding.DialogSuccessLayBinding
import com.mobulous.fitscope.databinding.FounddeviceItemLayBinding
import com.mobulous.model.FoundDeviceModel
import java.util.ArrayList

class FoundDeviceAdapter(var con: Context, var lst: ArrayList<FoundDeviceModel>) :
    RecyclerView.Adapter<FoundDeviceAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        return MyViewHolder(
            FounddeviceItemLayBinding.inflate(
                LayoutInflater.from(con),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemFoundDevice.ivMainImgFoundDeviceItem.setImageResource(lst[position].mainimg)
        holder.itemFoundDevice.tvTitleFoundDeviceItem.text = lst[position].title
        holder.itemFoundDevice.ivBlutoothFoundDeviceItem.setImageResource(lst[position].bltthimg)
//        holder.itemFoundDevice.tvTitleFoundDeviceItem.setOnClickListener {
//            scs.onSuccessClk(position,
//            id = "",
//            isTxt = true)
//        }
    }

    override fun getItemCount(): Int {
        return lst.size
    }
   
    inner class MyViewHolder(val itemFoundDevice: FounddeviceItemLayBinding) :
        RecyclerView.ViewHolder(itemFoundDevice.root),View.OnClickListener
         {
        init {
            itemFoundDevice.mainFoundDeviceItemLayout.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            when (v!!.id) {
                R.id.main_FoundDeviceItem_layout -> {
                    val dialog = Dialog(con,R.style.CustomBottomSheetDialogTheme)
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    dialog.setContentView(R.layout.dialog_success_lay)
                    dialog.window!!.setLayout(

                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    val ok = dialog.findViewById<TextView>(R.id.tvOKDialogSuccessItem)
                    ok.setOnClickListener {
                        con.startActivity(Intent(con,FoundDeviceActivity::class.java))
                    }
                    dialog.show()

                }
            }
        }


    }
}