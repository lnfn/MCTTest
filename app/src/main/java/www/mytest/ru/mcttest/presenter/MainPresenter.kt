package www.mytest.ru.mcttest.presenter

import rx.Subscription
import www.mytest.ru.mcttest.R
import www.mytest.ru.mcttest.interactor.IMainInteractor
import www.mytest.ru.mcttest.view.MainView
import java.lang.ref.WeakReference
import javax.inject.Inject

/**
 * Created by Eugene on 21.04.2016.
 */
class MainPresenter
@Inject constructor(val mainInteractor: IMainInteractor): IMainPresenter {
    private var mainView: WeakReference<MainView>? = null
    private var sub: Subscription? = null

    private var lastQuery = ""

    override fun setView(mainView: MainView) {
        this.mainView = WeakReference(mainView)
    }

    override fun loadResult(query: String) {
        if(query.length > 0 && query != lastQuery) {
            lastQuery = query
            if(sub?.isUnsubscribed == false) sub?.unsubscribe()
            mainView?.get()?.showProgressBar()
            sub = mainInteractor.getResult(query)
                    .subscribe(
                            {
                                it ->
                                mainView?.get()?.hideProgressBar()
                                if (it.results.size > 0)
                                    mainView?.get()?.loadedList(it.results)
                                else
                                    mainView?.get()?.showError(R.string.no_search)
                            },
                            {
                                t ->
                                mainView?.get()?.hideProgressBar()
                                mainView?.get()?.showError(R.string.no_internet)
                            },
                            {}
                    )
        }
    }

    override fun onDestroy() {
        if(sub?.isUnsubscribed == false) sub?.unsubscribe()
        mainView?.clear()
    }
}