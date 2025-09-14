package org.oosd.teamorange_milestoneone.system.submodules;

public abstract class SubModule {
    protected Parent view;

    public abstract Parent createView();
    public abstract void onShow();
    public abstract void onHide();

    public Parent getView() {
        if (view == null) {
            view = createView();
        }
        return view;
    }
}
