package com.example.user.aina_app.common;

import com.example.user.aina_app.common.QuMediaBaseActivity2;

import java.io.Serializable;

public class BaseReponseVO2 implements Serializable {
    private QuMediaBaseActivity2 activeActivity;
    private int addFlags;
    private String doAction;

    public BaseReponseVO2(QuMediaBaseActivity2 aQuMediaBaseActivity, int addFlags, String doAction) {
        this.setActiveActivity(aQuMediaBaseActivity);
        this.setAddFlags(addFlags);
        this.setDoAction(doAction);
    }

    public QuMediaBaseActivity2 getActiveActivity() {
        return this.activeActivity;
    }

    public void setActiveActivity(QuMediaBaseActivity2 activeActivity) {
        this.activeActivity = activeActivity;
    }

    public int getAddFlags() {
        return this.addFlags;
    }

    public void setAddFlags(int addFlags) {
        this.addFlags = addFlags;
    }

    public String getDoAction() {
        return this.doAction;
    }

    public void setDoAction(String doAction) {
        this.doAction = doAction;
    }
}
