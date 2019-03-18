package com.example.user.aina_app.action;

import com.example.user.aina_app.common.BaseReponseVO2;
import com.qumedia.android.core.common.BaseReponseVO;

import java.io.Serializable;

public interface HttpDataHandler2 extends Serializable {
    void setData(Object var1, BaseReponseVO2 var2) throws Exception;
}
