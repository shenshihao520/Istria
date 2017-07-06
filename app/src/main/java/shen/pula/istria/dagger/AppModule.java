//package shen.pula.istria.dagger;
//
//import android.app.Application;
//
////import dagger.Module;
////import dagger.Provides;
//import dagger.Module;
//import dagger.Provides;
//import shen.pula.istria.MPresenter.LocationPresenter;
//import shen.pula.istria.MainApplication;
//
///**
// * Created by shenshihao520 on 2017/4/13.
// */
//
//@Module
//public class AppModule {
//    private final Application application;
//
//    public AppModule(Application application) {
//        this.application = application;
//    }
//
//    @Provides
//    public String provideLocation() {
//        return "Tianjin";
//    }
//
//    @Provides
//    public LocationPresenter provideDagger() {
//        return new LocationPresenter(application.getBaseContext());
//    }
//}
