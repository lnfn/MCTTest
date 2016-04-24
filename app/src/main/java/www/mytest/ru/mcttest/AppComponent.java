package www.mytest.ru.mcttest;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Eugene on 24.03.2016.
 */
@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent {
    void inject(MainActivity mainActivity);
}