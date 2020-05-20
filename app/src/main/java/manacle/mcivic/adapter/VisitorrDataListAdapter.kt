package manacle.mcivic.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import manacle.mcivic.R
import manacle.mcivic.data.model.MyHelperDataModel
import manacle.mcivic.data.model.VisitorDataModel

class VisitorrDataListAdapter
    (val visitorList: ArrayList<VisitorDataModel>) : RecyclerView.Adapter<VisitorrDataListAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VisitorrDataListAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.visitor_data_layout, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: VisitorrDataListAdapter.ViewHolder, position: Int) {
        holder.bindItems(visitorList[position])
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return visitorList.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(
            myHelperModel: VisitorDataModel
        ) {
            val name = itemView.findViewById(R.id.name) as TextView
            val mobie_no = itemView.findViewById(R.id.mobie_no) as TextView
            val cardView_adapter = itemView.findViewById(R.id.cardView_adapter) as CardView
            name.text = myHelperModel.visitor_name

            mobie_no.text = myHelperModel.visitor_mobile
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