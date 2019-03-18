/*
 *  ===========================================================================
 *  * QuMedia Confidential
 *  *
 *  * (C) Copyright QuMedia Corp. 2014.
 *  * ===========================================================================
 */
package com.example.user.aina_app.action;

import android.util.Log;

import com.example.user.aina_app.common.BaseReponseVO2;
import com.example.user.aina_app.common.ViartrilMobileResponseVO;
import com.qumedia.android.core.common.BaseReponseVO;
import com.qumedia.android.core.common.HttpDataHandler;
import com.qumedia.android.core.util.XmlBeanUtils;


import org.w3c.dom.Document;

import java.io.DataInputStream;

/**
 * <p>HttpCallBackProcessAction
 * <p/>
 * </p>
 *
 * @author Brian Liao
 * @version 上午 10:52:32 2010/1/22
 * @see
 */
public class HttpCallBackProcessAction implements HttpDataHandler2 {

    public HttpCallBackProcessAction() {

    }

    public void setData(Object input, BaseReponseVO2 responseHeader) throws Exception {
        Document xmlDoc=XmlBeanUtils.parseXmlData((DataInputStream)input);
        ViartrilMobileResponseVO reponseVO = new ViartrilMobileResponseVO(xmlDoc);
        responseHeader.getActiveActivity().doHttpCallBack(reponseVO);
    }
}
