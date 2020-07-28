package cn.linbin.worklog.shiro;

import cn.linbin.worklog.dao.UserDao;
import cn.linbin.worklog.domain.po.User;
import cn.linbin.worklog.utils.MD5Util;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomerCredentialsMatcher extends SimpleCredentialsMatcher {

    @Autowired
    private UserDao userDao;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;

        String telNo = usernamePasswordToken.getUsername();
        String userPassword = String.valueOf(usernamePasswordToken.getPassword());
        String md5Password = MD5Util.md5(userPassword, telNo);

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("TEL_NO", telNo);
        User user = userDao.selectOne(wrapper);

        if (user==null){
            return false;
        }

        return md5Password.equals(user.getPassword());
    }
}
