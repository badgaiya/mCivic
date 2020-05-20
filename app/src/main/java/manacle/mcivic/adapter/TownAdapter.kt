package manacle.mcivic.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import manacle.mcivic.R
import manacle.mcivic.data.model.RegistrationLayer
import manacle.mcivic.ui.login.SignupInformationActivity
import java.lang.Exception

class TownAdapter(val townList: ArrayList<RegistrationLayer>) :
    RecyclerView.Adapter<TownAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TownAdapter.ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_single_item, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: TownAdapter.ViewHolder, position: Int) {
        holder.bindItems(townList[position])
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return townList.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(registrationLayer: RegistrationLayer) {
            val textViewName = itemView.findViewById(R.id.textViewItem) as TextView
            val cardView_adapter = itemView.findViewById(R.id.cardView_adapter) as CardView

            textViewName.text = registrationLayer.townName
            val context: Context = cardView_adapter.context

            cardView_adapter.setOnClickListener() {
                try {


                    val intent = Intent(context, SignupInformationActivity::class.java)
                    intent.putExtra("sign_up_information_town_id", registrationLayer.townId)
                    intent.putExtra("sign_up_information_town_name", registrationLayer.townName)
                    intent.putExtra("sign_up_information_layer", registrationLayer.TownLayer)

                    context.startActivity(intent)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}