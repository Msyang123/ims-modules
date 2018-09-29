package com.lhiot.ims.rbac.api;

import com.leon.microx.util.auditing.MD5;

/**
 * @Author: HuFan
 * @Description:
 * @Date: Create in 2018/9/29 16:17
 * @Modified By:
 */
public class Test {
    public static void main(String[] args) {
        String md5 = MD5.str("abc123");
        System.out.println(md5);//e99a18c428cb38d5f260853678922e03
    }
}
