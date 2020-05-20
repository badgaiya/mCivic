package manacle.mcivic.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_layout.view.cardView_adapter
import manacle.mcivic.R
import manacle.mcivic.data.model.MyHelperModel
import manacle.mcivic.prefrences.Prefrences
import manacle.mcivic.ui.login.HelperDataForm
import manacle.mcivic.ui.login.HelperDataListForm

class MyHelperDataAdapter(val context: Context, val userList: ArrayList<MyHelperModel>) : RecyclerView.Adapter<MyHelperDataAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHelperDataAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_layout, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: MyHelperDataAdapter.ViewHolder, position: Int) {
        holder.bindItems(userList[position],context)
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return userList.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(
            myHelperModel: MyHelperModel,
            context: Context
        ) {
            val textViewName = itemView.findViewById(R.id.textViewUsername) as TextView
            val cardView_adapter = itemView.findViewById(R.id.cardView_adapter) as CardView
             textViewName.text = myHelperModel.rolename
            val role_id:String=myHelperModel.role_id
            Prefrences.setPrefrences(context,"role_id","role_id")

            itemView.cardView_adapter.setOnClickListener {
                 val context: Context =itemView.cardView_adapter.context

                val intent=Intent(context, HelperDataListForm::class.java)
                //intent.putExtra("role_id", myHelperModel.role_id)
                intent.putExtra("rolename", myHelperModel.rolename)

                context.startActivity(intent)




            }
         }
    }
}