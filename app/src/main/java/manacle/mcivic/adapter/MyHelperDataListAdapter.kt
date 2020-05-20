package manacle.mcivic.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_layout.view.*
import manacle.mcivic.R
import manacle.mcivic.data.model.MyHelperDataModel



class MyHelperDataListAdapter(val userList: ArrayList<MyHelperDataModel>) : RecyclerView.Adapter<MyHelperDataListAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHelperDataListAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_data_layout, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: MyHelperDataListAdapter.ViewHolder, position: Int) {
        holder.bindItems(userList[position])
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return userList.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(
            myHelperModel: MyHelperDataModel
        ) {
            val name = itemView.findViewById(R.id.name) as TextView
            val address = itemView.findViewById(R.id.address) as TextView
            val mobie_no = itemView.findViewById(R.id.mobie_no) as TextView
            val cardView_adapter = itemView.findViewById(R.id.cardView_adapter) as CardView
            name.text = myHelperModel.fullname
            address.text = myHelperModel.address
            mobie_no.text = myHelperModel.mobile_no
          /*  val role_id:String=myHelperModel.role_id
            Prefrences.setPrefrences(context,"role_id","role_id")*/

          /*  itemView.cardView_adapter.setOnClickListener {
                val context: Context =itemView.cardView_adapter.context

                val intent=Intent(context, HelperDataListForm::class.java)
                //intent.putExtra("role_id", myHelperModel.role_id)
                intent.putExtra("rolename", myHelperModel.rolename)

                context.startActivity(intent)




            }*/
        }
    }
}