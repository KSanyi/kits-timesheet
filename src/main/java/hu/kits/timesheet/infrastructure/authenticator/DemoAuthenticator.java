package hu.kits.timesheet.infrastructure.authenticator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import hu.kits.timesheet.usermanagement.Authenticator;
import hu.kits.timesheet.usermanagement.UserInfo;
import hu.kits.timesheet.usermanagement.UserRole;

public class DemoAuthenticator implements Authenticator {

    @Override
    public UserInfo authenticateUser(String loginName, String password) throws AuthenticationException {
        
        UserInfo userInfo = users.keySet().stream()
                .filter(u -> u.loginName.equals(loginName))
                .findAny().orElseThrow(() -> new WrongPasswordException());
        
        if(password.equals(users.get(userInfo))) {
            return userInfo;
        }
        
        throw new WrongPasswordException();
    }
    
    public static final Map<UserInfo, String> users;
    
    static {
        Map<UserInfo, String> map = new HashMap<>();
        map.put(new UserInfo("korsi", "Kov�cs Orsolya", UserRole.Manager), "xxx");
        map.put(new UserInfo("kvirag", "Kiss Vir�g", UserRole.Employee), "xxx");
        map.put(new UserInfo("skocso", "K�cs� S�ndor", UserRole.Admin), "xxx");
        users = Collections.unmodifiableMap(map);
    }

}
