package es.uva.alumnos.serorca.kphysics.ui.proyectlist

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.uva.alumnos.serorca.kphysics.R
import es.uva.alumnos.serorca.kphysics.data.database.DatabaseHelper
import es.uva.alumnos.serorca.kphysics.utils.csv.OpenCSVReader
import kotlinx.android.synthetic.main.fragment_project.*

class ProjectFragment : Fragment(), View.OnClickListener {
    companion object {

        const val PROJECT_NAME = "PROJECT_NAME"

        fun newInstance() = ProjectFragment()

    }

    private val db = DatabaseHelper(context!!)
    private var projectName: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_project, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        projectName = this.arguments!!.getString(PROJECT_NAME)
        fileText.setSingleLine(false)
        projectTitle.text = "Project: $projectName\n"
        OpenCSVReader.readCSVFile(context!!.filesDir.toString() + "/" + projectName, fileText)
        deleteButton.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        db.deleteProject(projectName!!)
    }
}
