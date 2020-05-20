package manacle.mcivic.adapter

import android.app.ProgressDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
 import manacle.mcivic.R
import manacle.mcivic.data.model.*

class RegistrationLocationLayerAdapter (val town_id:String, val townList: ArrayList<TownLayer1>
                         ) : RecyclerView.Adapter<RegistrationLocationLayerAdapter.ViewHolder>() {
     //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):  ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_single_item_autocomplete, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder:  RegistrationLocationLayerAdapter.ViewHolder, position: Int) {

         holder.bindItems(townList[position],town_id)
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return townList.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var progerssProgressDialog: ProgressDialog
         fun bindItems(townLayer: TownLayer1, townId: String) {
            val textViewName = itemView.findViewById(R.id.textViewItem) as TextView
            val cardView_adapter = itemView.findViewById(R.id.cardView_adapter) as CardView
            val editTextItem = itemView.findViewById(R.id.editTextItem) as AutoCompleteTextView

            textViewName.text = townLayer.name
            val context: Context = cardView_adapter.context

             val adapter = ArrayAdapter(context,
                 R.layout.adapter_single_item,R.id.textViewItem, townLayer.layerArrayList)
             editTextItem.setThreshold(0); //will start working from first character

             editTextItem.setAdapter(adapter)
             editTextItem.onItemClickListener=AdapterView.OnItemClickListener {
                     parent,view,position,id->

                 val selectedItem = parent.getItemAtPosition(position).toString()

                 val companyid=   townLayer.layerArrayList.get(position).company_id
                 val layerid=   townLayer.layerArrayList.get(position).id
                 val layername=   townLayer.layerArrayList.get(position).name
                 editTextItem.setText(layername)
                 townLayer.location_id=layerid
                 townLayer.company_id=companyid

             }



        }


    }
}