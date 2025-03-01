package com.minorm;

import com.minorm.entity.Company;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyTest {

    @Test
    void testDynamic(){ // dynamic uses interfaces
        Company company = new Company();
        Proxy.newProxyInstance(company.getClass().getClassLoader(), company.getClass().getInterfaces(),
                (proxy, method, args) -> method.invoke(company, args));
    }
}
