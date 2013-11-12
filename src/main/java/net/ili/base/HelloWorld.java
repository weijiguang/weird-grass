/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ili.base;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Weir
 */
//@Named
//@ViewScoped
public class HelloWorld {

    private String sayHelloWorld = "hello world,hello spring ,hello CDI ...!!!";
    public static final String PREMISSION_STRING = "perms[\"{0}\"]";

    public HelloWorld() {
//        org.apache.shiro.realm.jdbc.JdbcRealm jdbcRealm = new org.apache.shiro.realm.jdbc.JdbcRealm();
//        jdbcRealm.
//        org.springframework.orm.hibernate4.support.OpenSessionInViewFilter
//        org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter
//        net.ili.base.services.authority.IliJpaRealm
//        org.apache.shiro.web.filter.mgt.DefaultFilter
//        org.springframework.cache.ehcache.EhCacheManagerFactoryBean
//        org.springframework.cache.ehcache.EhCacheManagerFactoryBean
//       org.springframework.web.filter.DelegatingFilterProxy
//        org.springframwork.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator
//        org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator
//        org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor
//        org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor
//        org.apache.shiro.web.filter.authc.PassThruAuthenticationFilter
//        net.ili.base.util.RedirectNavigationHandler
    }

//  org.springframework.web.jsf.el.SpringBeanFacesELResolver
    /**
     * Get the value of sayHelloWorld
     *
     * @return the value of sayHelloWorld
     */
    public String getSayHelloWorld() {
        return sayHelloWorld;
    }

    /**
     * Set the value of sayHelloWorld
     *
     * @param sayHelloWorld new value of sayHelloWorld
     */
    public void setSayHelloWorld(String sayHelloWorld) {
        this.sayHelloWorld = sayHelloWorld;
    }

    public void test() {
        System.out.println(MessageFormat.format(PREMISSION_STRING, "user:list"));

    }

    public static void main(String[] args) {
//        HelloWorld h = new HelloWorld();
//        h.test();

        Pattern pattern = Pattern.compile("perms\\[(.*?)\\]");

        List<String> sl = new ArrayList<>();
        List<String> sl2 = new ArrayList<>();
        sl.add("wa");
        sl.add("ag");
        sl.add("ar");
        sl.add("ae");
        sl.add("9a");
        sl.add("ca");

        for (String str : sl) {
            sl2.add(MessageFormat.format(PREMISSION_STRING, str));
        }

        System.out.println(StringUtils.join(sl2, ","));

        Matcher matcher = pattern.matcher(StringUtils.join(sl2, ","));

        System.out.println(matcher.groupCount());
        
        while (matcher.find()) {
            System.out.println(matcher.group(1));
        }
    }
}
