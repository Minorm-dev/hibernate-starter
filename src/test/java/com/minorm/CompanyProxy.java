package com.minorm;

import com.minorm.entity.Company;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;
import org.hibernate.proxy.ProxyConfiguration;
import org.hibernate.proxy.pojo.bytebuddy.ByteBuddyInterceptor;

public class CompanyProxy extends Company
        implements HibernateProxy, ProxyConfiguration { // uses in fetch.LAZY

    private ByteBuddyInterceptor byteBuddyInterceptor;

    @Override
    public Object writeReplace() {
        return null;
    }

    @Override
    public LazyInitializer getHibernateLazyInitializer() {
        return byteBuddyInterceptor;
    }

    @Override
    public void $$_hibernate_set_interceptor(Interceptor interceptor) {

    }
}
