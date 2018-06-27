package es.uva.alumnos.serorca.kphysics.ui.experiment.activity

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import es.uva.alumnos.serorca.kphysics.ui.experiment.fragment.ExperimentFragment

class ExperimentTabAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    var fragments = listOf<ExperimentFragment>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun getCount() = fragments.size

    override fun getPageTitle(position: Int): String? =
            fragments[position].arguments!!.getString(ExperimentFragment.ARG_TYPE_NAME)

    override fun getItem(position: Int) = fragments[position]

}