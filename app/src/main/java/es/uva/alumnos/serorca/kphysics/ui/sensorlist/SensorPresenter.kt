package es.uva.alumnos.serorca.kphysics.ui.sensorlist

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class SensorPresenter : SensorContract.Presenter {

    private val subscriptions = CompositeDisposable()
    private lateinit var view: SensorContract.View

    override fun subscribe() {
    }

    override fun unsubscribe() {
    subscriptions.clear()
    }

    override fun attach(view: SensorContract.View) {
        this.view = view
    }

    override fun loadList() {
        
    }


}