package org.thor.infrastructure;

import org.thor.base.base.RxLifeActivity;

public class MainActivity extends RxLifeActivity {


    private MainViewModule mainViewModule;

    @Override
    protected int getLayoutResId() {

        return R.layout.activity_main;
    }

    @Override
    protected void configView() {
    }

    @Override
    protected void initData() {
        mainViewModule = new MainViewModule(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainViewModule.a();
    }

    @Override
    public void showProgress() {
        super.showProgress();
    }

    @Override
    public void onError(String message) {
        super.onError(message);
    }
}
