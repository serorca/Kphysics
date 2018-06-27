package es.uva.alumnos.serorca.kphysics.ui.experiment.fragment

class ExperimentFragmentPresenter: ExperimentFragmentContract.Presenter{
    private lateinit var view: ExperimentFragmentContract.View

    override fun subscribe() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun unsubscribe() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun attach(view: ExperimentFragmentContract.View) {
        this.view = view
        view.showLineGraph()
    }

}