package es.uva.alumnos.serorca.kphysics.ui.experiment.activity

class ExperimentPresenter: ExperimentContract.Presenter{

    private lateinit var view: ExperimentContract.View

    override fun subscribe() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun unsubscribe() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun attach(view: ExperimentContract.View) {
        this.view = view
        view.showExperimentFragment()
    }


}