package cn.linbin.worklog.shiro;

import cn.linbin.worklog.dao.UserDao;
import cn.linbin.worklog.domain.User;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomerAuthorizingRealm extends AuthorizingRealm {

    @Autowired
    private UserDao userDao;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    /**
     * 身份认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("TEL_NO", usernamePasswordToken.getUsername());
        User user = userDao.selectOne(wrapper);

        //Object principal, Object credentials, String realmName
        AuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), "CUSTOMER_REALM");
        return info;
    }
}
