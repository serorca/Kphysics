package es.uva.alumnos.serorca.kphysics.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import es.uva.alumnos.serorca.kphysics.R
import es.uva.alumnos.serorca.kphysics.data.database.DatabaseHelper
import es.uva.alumnos.serorca.kphysics.di.component.DaggerActivityComponent
import es.uva.alumnos.serorca.kphysics.di.module.ActivityModule
import es.uva.alumnos.serorca.kphysics.ui.experiment.activity.ExperimentActivity
import es.uva.alumnos.serorca.kphysics.ui.proyectlist.ProjectFragment
import es.uva.alumnos.serorca.kphysics.ui.proyectlist.ProyectListFragment
import es.uva.alumnos.serorca.kphysics.ui.sensorlist.SensorListFragment
import es.uva.alumnos.serorca.kphysics.utils.addFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import org.jetbrains.anko.support.v4.withArguments
import javax.inject.Inject


class MainActivity : AppCompatActivity(), MainContract.View, NavigationView.OnNavigationItemSelectedListener {

    private val db = DatabaseHelper(this)
    private var projectName : String? = null

    @Inject
    lateinit var presenter: MainContract.Presenter

    companion object {

        private const val PROJECT_NAME = "PROJECT_NAME"

        fun newIntent(context: Context,
                      projectName: String): Intent {

            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra(PROJECT_NAME, projectName)
            return intent
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        injectDependency()
        presenter.attach(this)
        projectName = intent.getStringExtra(PROJECT_NAME)
        if (projectName!= null)
            navigateTo(ProjectFragment.newInstance().withArguments(
                    ProjectFragment.PROJECT_NAME to projectName
            ))
        initNavigation()

        fab.setOnClickListener { _ ->
            showFirstDialog()
        }
    }

    override fun showProyectFragment() {
        addFragment(ProyectListFragment(), R.id.contentFrame, ProyectListFragment.TAG)
    }

    override fun showSensorFragment() {
        addFragment(SensorListFragment(), R.id.contentFrame, SensorListFragment.TAG)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun initNavigation() {
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    private fun navigateTo(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.contentFrame, fragment).commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sensor_list -> {
//                navigateTo(SensorListFragment.newInstance())
                showSensorFragment()
            }
            R.id.project_gallery -> {
//                showProyectFragment()
                navigateTo(ProyectListFragment.newInstance())
            }
            R.id.nav_manage -> {
                drawer_layout!!.closeDrawers()
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }


    private fun injectDependency() {
        val activityComponent = DaggerActivityComponent.builder()
                .activityModule(ActivityModule(this))
                .build()

        activityComponent.inject(this)
    }

    private fun showFirstDialog() {

        lateinit var dialog: AlertDialog

        val builder = AlertDialog.Builder(this)
        val input = EditText(this)

        builder.setTitle("Introduce el nombre de tu proyecto:")
        input.hint = "Nombre del proyecto"
        builder.setView(input)

        builder.setPositiveButton("Siguiente") { _, _ ->
            db.insertProjectData(input.text.toString(), "Accelerometer")
            showDialog(input.text.toString())
        }
        builder.setNegativeButton("Cancelar") { _, _ -> }
        dialog = builder.create()
        dialog.show()
    }

    private fun showDialog(currentProject: String) {

        lateinit var dialog: AlertDialog

        val sensorOptions = arrayOf(
                "Accelerometer",
                "Ambient Temperature",
                "Gravity",
                "Gyroscope",
                "Light",
                "Linear Acceleration",
                "Magnetic Field",
                "Orientation",
                "Pressure",
                "Proximity",
                "Relative Humidity",
                "Rotation Vector",
                "Temperature")

        val arrayChecked = booleanArrayOf(
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false
        )

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Escoge los sensores de tu experimento:")
        builder.setMultiChoiceItems(sensorOptions, arrayChecked) { _, which, isChecked ->
            arrayChecked[which] = isChecked
        }
        builder.setPositiveButton("Aceptar") { _, _ ->
            val intent = ExperimentActivity.newIntent(this, arrayChecked, 0,
                    currentProject, true)

            startActivity(intent)
        }
        builder.setNegativeButton("Cancelar") { _, _ -> }
        dialog = builder.create()
        dialog.show()
    }
}
