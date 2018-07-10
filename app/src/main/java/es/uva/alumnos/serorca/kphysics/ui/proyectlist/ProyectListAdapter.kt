package es.uva.alumnos.serorca.kphysics.ui.proyectlist

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import es.uva.alumnos.serorca.kphysics.R
import es.uva.alumnos.serorca.kphysics.data.database.DatabaseHelper
import es.uva.alumnos.serorca.kphysics.ui.main.MainActivity

class ProyectListAdapter(
        private val context: Context) : RecyclerView.Adapter<ProyectListAdapter.ProyectListViewHolder>() {

    private val db = DatabaseHelper(context)

    override fun getItemCount(): Int {
        return db.getAllProjectData().size
    }

    override fun onBindViewHolder(holder: ProyectListViewHolder, position: Int) {
        holder.projectIcon.setImageResource(R.drawable.ic_line_chart)
        holder.projectName.text = db.getAllProjectData()[position].name
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ProyectListViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_project_grid, parent, false)
        return ProyectListViewHolder(v)
    }

    inner class ProyectListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var projectIcon: ImageView = itemView.findViewById(R.id.project_icon)
        var projectName: TextView = itemView.findViewById(R.id.project_name)

        init {
            itemView.setOnClickListener { _ ->
                val intent = MainActivity.newIntent(context, projectName.text.toString())
                ContextCompat.startActivity(context, intent, null)
            }
        }
    }
}