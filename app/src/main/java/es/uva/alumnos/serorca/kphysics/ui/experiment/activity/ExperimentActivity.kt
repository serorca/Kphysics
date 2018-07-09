package es.uva.alumnos.serorca.kphysics.ui.experiment.activity

import android.content.Context

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import es.uva.alumnos.serorca.kphysics.R
import es.uva.alumnos.serorca.kphysics.di.component.DaggerActivityComponent
import es.uva.alumnos.serorca.kphysics.di.module.ActivityModule
import es.uva.alumnos.serorca.kphysics.ui.experiment.fragment.ExperimentFragment
import es.uva.alumnos.serorca.kphysics.ui.experiment.fragment.ExperimentFragmentContract
import kotlinx.android.synthetic.main.activity_experiment.*
import kotlinx.android.synthetic.main.tab_experiment_layout.*
import org.jetbrains.anko.support.v4.withArguments
import javax.inject.Inject
class ExperimentActivity : AppCompatActivity(), ExperimentContract.View{

    companion object {

        private const val SENSOR_LIST = "sensor_list"
        private const val POSITION = "position"
        private const val IS_EXPERIMENT = "is_experiment"
        const val CURRENT_EXPERIMENT = "current_experiment"

        fun newIntent(context: Context,
                      sensorList: BooleanArray,
                      position: Int?,
                      currentProject: String?,
                      isExperiment: Boolean): Intent {

            val intent = Intent(context, ExperimentActivity::class.java)
            intent.putExtra(SENSOR_LIST, sensorList)
            intent.putExtra(CURRENT_EXPERIMENT, currentProject)
            intent.putExtra(IS_EXPERIMENT, isExperiment)
            intent.putExtra(POSITION, position)

            return intent
        }
    }

    private var sensorList: BooleanArray = BooleanArray(13)
    private var isExperiment: Boolean = true
    private var position: Int = 0

//    private var currentProject: String? = null
    lateinit var experimentTabAdapter: ExperimentTabAdapter

    @Inject
    lateinit var presenter: ExperimentContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_experiment)

        sensorList = intent.getBooleanArrayExtra(SENSOR_LIST)
        position = intent.getIntExtra(POSITION, 0)
//        currentProject = intent.getStringExtra(CURRENT_EXPERIMENT)
        isExperiment = intent.getBooleanExtra(IS_EXPERIMENT, false)

        injectDependency()
        initTabLayout()
        initViewPager()

        presenter.attach(this)

    }

    override fun showExperimentFragment() {
        val tabList: ArrayList<String>

        if (!isExperiment) {
            val auxList = arrayListOf(position)

            tabList = positionToSensorList(auxList)
            experimentTabAdapter.fragments = tabList.map {
                ExperimentFragment().withArguments(
                        ExperimentFragment.ARG_TYPE_NAME to it

                )
            }

        } else {
            tabList = positionToSensorList(booleanListToPositionList(sensorList))
            experimentTabAdapter.fragments = tabList.map {
                ExperimentFragment().withArguments(
                        ExperimentFragment.ARG_TYPE_NAME to it
                )
            }
        }
    }

    private fun initViewPager() {
        experimentTabAdapter = ExperimentTabAdapter(supportFragmentManager)
        view_pager.adapter = experimentTabAdapter
    }


    private fun initTabLayout() {
        tab_layout.setupWithViewPager(view_pager)
        tab_layout.tabMode = TabLayout.MODE_SCROLLABLE
    }

    private fun injectDependency() {
        val activityComponent = DaggerActivityComponent.builder()
                .activityModule(ActivityModule(this))
                .build()

        activityComponent.inject(this)
    }

    private fun booleanListToPositionList(booleanArray: BooleanArray?): ArrayList<Int> {
        val resList: ArrayList<Int> = ArrayList()

        for ((position, i) in booleanArray!!.withIndex()) {
            if (i)
                resList.add(position)
        }
        return resList
    }

    private fun positionToSensorList(sensorPosition: ArrayList<Int>): ArrayList<String> {
        val restList: ArrayList<String> = ArrayList()
        for (i in 0 until sensorPosition.size) {
            when (sensorPosition[i]) {
                0 -> restList.add("Accelerometer")
                1 -> restList.add("Ambient Temperature")
                2 -> restList.add("Gravity")
                3 -> restList.add("Gyroscope")
                4 -> restList.add("Light")
                5 -> restList.add("Linear Acceleration")
                6 -> restList.add("Magnetic Field")
                7 -> restList.add("Orientation")
                8 -> restList.add("Pressure")
                9 -> restList.add("Proximity")
                10 -> restList.add("Relative Humidity")
                11 -> restList.add("Rotation Vector")
                12 -> restList.add("Temperature")
                else -> restList.add("Error")
            }
        }
        return restList
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_tabbed_menu, menu)

        return true
    }

//    }
}

