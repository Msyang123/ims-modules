package com.lhiot.ims.rbac.common;

import com.leon.microx.util.StringUtils;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

public class ConverStrToRequestMethod {

    @Nullable
    public static RequestMethod[] getListFromStr(String methodListStr){
        if(StringUtils.isBlank(methodListStr))
            return null;
        List<RequestMethod> result = new ArrayList<>();
        if(methodListStr.contains("*")) {
            result.add(RequestMethod.GET);
            result.add(RequestMethod.PATCH);
            result.add(RequestMethod.POST);
            result.add(RequestMethod.PUT);
            result.add(RequestMethod.DELETE);
            result.add(RequestMethod.HEAD);
            result.add(RequestMethod.TRACE);
            result.add(RequestMethod.OPTIONS);
            return result.toArray(new RequestMethod[8]);
        }
        if(methodListStr.contains("GET")){
            result.add(RequestMethod.GET);
        }
        if(methodListStr.contains("PATCH")){
            result.add(RequestMethod.PATCH);
        }
        if(methodListStr.contains("POST")){
            result.add(RequestMethod.POST);
        }
        if(methodListStr.contains("PUT")){
            result.add(RequestMethod.PUT);
        }
        if(methodListStr.contains("DELETE")){
            result.add(RequestMethod.DELETE);
        }
        if(methodListStr.contains("HEAD")){
            result.add(RequestMethod.HEAD);
        }
        if(methodListStr.contains("TRACE")){
            result.add(RequestMethod.TRACE);
        }
        if(methodListStr.contains("OPTIONS")){
            result.add(RequestMethod.OPTIONS);
        }
        return result.toArray(new RequestMethod[result.size()]);
    }
}
