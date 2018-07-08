package es.uva.alumnos.serorca.kphysics.ui.proyectlist

class ProyectListPresenter: ProyectListContract.Presenter{

    private lateinit var view: ProyectListContract.View

    override fun subscribe() {
    }

    override fun unsubscribe() {
    }

    override fun attach(view: ProyectListContract.View) {
        this.view = view
    }
}