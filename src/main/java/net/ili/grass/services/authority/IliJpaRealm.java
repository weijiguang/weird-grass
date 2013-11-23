/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ili.grass.services.authority;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import net.ili.grass.dao.authority.UserDao;
import net.ili.grass.entities.authority.Resource;
import net.ili.grass.entities.authority.Role;
import net.ili.grass.entities.authority.User;
import net.ili.grass.webbeans.authority.MySession;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 授权及认证类
 *
 * @author Weir
 */
//@Named
public class IliJpaRealm extends AuthorizingRealm {

    @Inject
    private UserDao userDao;
    @Inject
    private MySession mySession;
    //默认role表达式
    //多个时必须加上引号，并且参数之间用逗号分割，当有多个参数时，例如/admins/user/**=roles["admin,guest"]
    static final String ROLEEXP = "roles[\"{0}\"]";
    //默认premission表达式
    //多个时必须加上引号，并且参数之间用逗号分割，例如/admins/user/**=perms["user:add:*,user:modify:*"]
    static final String PREMISSIONEXP = "perms[\"{0}\"]";

    /**
     * 授权查询回调函数，进行鉴权但缓存中无用户的授权信息时调用
     * 
     * @param pc 当前参与者集合
     * @return 返回当前参与者的授权信息
     */
//    授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
//    doGetAuthorizationInfo(PrincipalCollection principals)是负责在应用程序中决定用户的访问控制的方法。
//    它是一种最终判定用户是否被允许做某件事的机制。与doGetAuthenticationInfo(AuthenticationToken token)相似，
//    doGetAuthorizationInfo(PrincipalCollection principals) 
//    也知道如何协调多个后台数据源来访问角色恶化权限信息和准确地决定用户是否被允许执行给定的动作。
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {
        //获取当前应用参与者
        Object principal = pc.getPrimaryPrincipal();

        if (principal instanceof MySession && mySession.getLoginUser() != null) {

            //当前应用参与者拥有的授权信息
            SimpleAuthorizationInfo auzInfo = new SimpleAuthorizationInfo();
            //查询当前参与者的授权信息
            User loginUser = mySession.getLoginUser();

            List<Role> roles = userDao.findOne(loginUser.getId()).getRoles();
            List<Resource> resources = userDao.searchResources(loginUser.getId());
            //添加当前参与者拥有的role
            addRoles(auzInfo, roles);
            //添加当前参与者拥有的permission
            addPermissions(auzInfo, resources);
//            System.out.println("auzInfo.getRoles():" + auzInfo.getRoles());
//            System.out.println("auzInfo.getStringPermissions():" + auzInfo.getStringPermissions());

            SecurityUtils.getSubject().getSession().setAttribute("mySession", mySession);
            return auzInfo;
        }
        return null;
    }

    /**
     * 认证回调函数，登陆时调用
     *
     * @param at 当前应用参与者的令牌
     * @return 返回当前应用参与者的信息
     * @throws AuthenticationException
     */
//    认证回调函数, 登录时调用.
//    doGetAuthenticationInfo(AuthenticationToken token)（认证/登录方法）会返回一个AuthenticationInfo,
//            就是认证信息。是一个对执行及对用户的身份验证（登录）尝试负责的方法。当一个用户尝试登录时，
//            该逻辑被认证器执行。认证器知道如何与一个或多个Realm协调来存储相关的用户/帐户信息。
//            从这些Realm中获得的数据被用来验证用户的身份来保证用户确实是他们所说的他们是谁。
    //当前应用参与者（可以是一个用户，也可以是第三方服务等其他抽象参与者）
    //当前应用参与者是在系统的入口（通常为用户登录的方法）通过Subject currentUser = SecurityUtils.getSubject()创建。
    //参与者的令牌（假设为username/password对）通过UsernamePasswordToken token = new UsernamePasswordToken(username,password)创建。
    //参与者通过currentUser.login(token)的方法登录shiro系统。
    //参与者通过currentUser.logout()的方法退出shiro系统。
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken at) throws AuthenticationException {
        //令牌 指的是一个键，可用它登录到一个系统。最基本和常用的令牌是 UsernamePasswordToken，用以指定用户的用户名和密码。
        //UsernamePasswordToken 类实现了 AuthenticationToken 接口，它提供了一种获得凭证和用户的主体（帐户身份）的方式。
        UsernamePasswordToken token = (UsernamePasswordToken) at;
        //通过表单接收的用户名
        String userName = token.getUsername();
        if (userName == null) {
            throw new AuthenticationException("用户名不能为空");
        }
        //根据登录账户的令牌，获取系统账户的信息
        User loginUser = userDao.findByName(userName);

        if (loginUser == null) {
            throw new AuthenticationException("用户不存在");
        }
        if (!loginUser.getEnable()) {
            throw new AuthenticationException("你的账户已被禁用,请联系管理员开通.");
        }
        mySession.setLoginUser(loginUser);
        return new SimpleAuthenticationInfo(mySession, loginUser.getPassword(), getName());
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    private void addRoles(SimpleAuthorizationInfo auzInfo, List<Role> roles) {
        List<String> rs = new ArrayList<>();
        for (Role role : roles) {
            if (StringUtils.isNotEmpty(role.getPermissionDefinition())) {
                rs.add(role.getPermissionDefinition());
            }
        }
        auzInfo.addRoles(rs);
    }

    private void addPermissions(SimpleAuthorizationInfo auzInfo, List<Resource> resources) {
        List<String> ps = new ArrayList<>();
        for (Resource resource : resources) {
            ps.add(resource.getPermission());
        }
        auzInfo.addStringPermissions(ps);
    }
}
