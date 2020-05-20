package manacle.mcivic.viewholder

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.adapter_dashboard_module.view.*
import manacle.mcivic.database.DashboardModuleTable
import manacle.mcivic.ui.login.CreateComplaintActivity
import manacle.mcivic.ui.login.CreateFieldInspectionActivity
import manacle.mcivic.ui.login.GarbageCollectionScanActivity

class DashboardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindView(dashboardModuleTable: DashboardModuleTable) {
        itemView.textDashboardModule.text = dashboardModuleTable.module_name
        itemView.cardView_adapter.setOnClickListener {
            val module_id:String=dashboardModuleTable.module_id
            val context: Context =itemView.cardView_adapter.context

            if(module_id.equals("9")){
                val intent=Intent(context,GarbageCollectionScanActivity::class.java)
                context.startActivity(intent)
            }
           else if(module_id.equals("10")){
                val intent=Intent(context,CreateComplaintActivity::class.java)
                context.startActivity(intent)
            }
          else  if(module_id.equals("11")){
                val intent=Intent(context,CreateFieldInspectionActivity::class.java)
                context.startActivity(intent)
            }
        }

     }

}