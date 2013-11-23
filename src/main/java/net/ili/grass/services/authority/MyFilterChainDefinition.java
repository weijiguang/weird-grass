/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ili.grass.services.authority;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map.Entry;
import javax.inject.Inject;
import javax.inject.Named;
import net.ili.grass.dao.authority.ResourceDao;
import net.ili.grass.entities.authority.Resource;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.config.Ini;
import org.springframework.beans.factory.FactoryBean;

/**
 * 动态创建filterchaindefinitions
 * 通过读取数据库来定义org.apache.shiro.spring.web.ShiroFilterFactoryBean的filterChainDefinitions
 *
 * @author Weir
 */
//@Named
public class MyFilterChainDefinition implements FactoryBean<Ini.Section> {

    @Inject
    private ResourceDao dao;
    //shiro默认的链接定义
    private String filterChainDefinitions;
    /**
     * 默认premission表达式
     */
    public static final String PREMISSIONEXP = "perms[\"{0}\"]";
//    public static final String PREMISSIONEXP = "authc,perms[{0}]";
//    public static final String PREMISSIONEXP = "authc,perms[\"{0}\"]";

    /**
     * 通过filterChainDefinitions对默认的url过滤定义
     *
     * @param filterChainDefinitions 默认的url过滤定义
     */
    public void setFilterChainDefinitions(String filterChainDefinitions) {
        this.filterChainDefinitions = filterChainDefinitions;
    }

    /**
     *
     * @return @throws Exception
     */
    @Override
    public Ini.Section getObject() throws Exception {
        Ini defaultIni = new Ini();
        Ini ini = new Ini();

        //加载默认的url
        if (StringUtils.isNotEmpty(filterChainDefinitions)) {
            defaultIni.load(filterChainDefinitions);
        }
        ini.addSection(Ini.DEFAULT_SECTION_NAME);
        Ini.Section section = ini.getSection(Ini.DEFAULT_SECTION_NAME);

        //获取所有Resource
        List<Resource> list = dao.findAll();
        for (Resource resource : list) {
            //如果不为空值添加到section中
            if (StringUtils.isNotEmpty(resource.getUrl()) && StringUtils.isNotEmpty(resource.getPermission())) {
                section.put(resource.getUrl(), MessageFormat.format(PREMISSIONEXP, resource.getPermission()));
            }
        }
        return section;
    }

    /**
     *
     * @return
     */
    @Override
    public Class<?> getObjectType() {
        return getClass();
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isSingleton() {
        return true;
    }
}
