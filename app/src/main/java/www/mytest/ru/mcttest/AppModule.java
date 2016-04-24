package www.mytest.ru.mcttest;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import www.mytest.ru.mcttest.interactor.IMainInteractor;
import www.mytest.ru.mcttest.interactor.MainInteractor;
import www.mytest.ru.mcttest.network.APIWebService;

/**
 * Created by Eugene on 24.03.2016.
 */
@Module
public class AppModule {
    private Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    IMainInteractor provideMainInteractor(APIWebService apiWebService) {
        return new MainInteractor(apiWebService);
    }

    @Provides
    Context provideContext () {
        return application.getApplicationContext();
    }
}