package manacle.mcivic.adapter

import android.app.Dialog
import android.content.Context
import android.os.Build
import android.transition.TransitionManager
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.visitor_data_layout.view.*
import manacle.mcivic.R
import manacle.mcivic.data.model.VisitorDataModel


class VisitorrDataListAdapter
    (val visitorList: ArrayList<VisitorDataModel>) :
    RecyclerView.Adapter<VisitorrDataListAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VisitorrDataListAdapter.ViewHolder {

        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.visitor_data_layout, parent, false)

        return ViewHolder(v)
    }

    //this method is binding the data on the list
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onBindViewHolder(holder: VisitorrDataListAdapter.ViewHolder, position: Int) {


        holder.bindItems(visitorList[position])
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return visitorList.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var mExpandedPosition = -1
        private val recyclerView: RecyclerView? = null

        @RequiresApi(Build.VERSION_CODES.KITKAT)
        fun bindItems(myHelperModel: VisitorDataModel) {
            val name = itemView.findViewById(R.id.guest) as TextView
            val mobie_no = itemView.findViewById(R.id.mo_number) as TextView
            val cardView = itemView.findViewById(R.id.cardView) as CardView
           // val cardView1 = itemView.findViewById(R.id.cardView1) as CardView
            name.text = myHelperModel.visitor_name

            mobie_no.text = myHelperModel.visitor_mobile
            /*  val role_id:String=myHelperModel.role_id
              Prefrences.setPrefrences(context,"role_id","role_id")*/
            val context: Context = cardView.context
            var pos: Int = adapterPosition;
            cardView.setOnClickListener({




                    dialogShow(pos)


            })

        }

        private fun dialogShow(pos: Int) {

          /*  val dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.cutsom_layout_card_dialog)
            val no_image = dialog.findViewById(R.id.no_image) as TextView
            val name_ = dialog.findViewById(R.id.guest) as TextView
            name_.text = myHelperModel.visitor_name
*/
            //val mobie_no.text = myHelperModel.visitor_mobile
          /*  no_image.setOnClickListener {
                dialog.dismiss()
            }*/
        }

    }

}

