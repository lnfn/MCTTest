package www.mytest.ru.mcttest.presenter;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.stubbing.Answer;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import www.mytest.ru.mcttest.Constants;
import www.mytest.ru.mcttest.interactor.MainInteractor;
import www.mytest.ru.mcttest.network.APIWebService;
import www.mytest.ru.mcttest.view.MainView;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;

/**
 * Created by Eugene on 22.04.2016.
 */
public class MainPresenterTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    MainView mainView;
    MainInteractor mainInteractor;
    MainPresenter mainPresenter;

    @Before
    public void setUp() {
        mainInteractor = new MainInteractor(
                new Retrofit.Builder()
                        .baseUrl(Constants.URL_WEB_SERVICE)
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .build().create(APIWebService.class)
        );
        mainPresenter = new MainPresenter(mainInteractor);
    }

    @Test
    public void testHideProgressBarWhenError() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return Observable.error(new Throwable("err"));
            }
        }).when(mainInteractor).getResult("п");
        mainPresenter.loadResult("");
        verify(mainView).hideProgressBar();
    }
}