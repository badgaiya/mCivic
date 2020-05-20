package manacle.mcivic.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import manacle.mcivic.R
import manacle.mcivic.database.DashboardModuleTable
import manacle.mcivic.viewholder.DashboardViewHolder

class DashBoardGridRecyclerAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var listOfMovies = listOf<DashboardModuleTable>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return DashboardViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.adapter_dashboard_module,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = listOfMovies.size

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val movieViewHolder = viewHolder as DashboardViewHolder
        movieViewHolder.bindView(listOfMovies[position])
    }

    fun setMovieList(listOfMovies: List<DashboardModuleTable>) {
        this.listOfMovies = listOfMovies
        notifyDataSetChanged()
    }
}