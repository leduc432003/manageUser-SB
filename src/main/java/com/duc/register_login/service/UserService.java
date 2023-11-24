package com.duc.register_login.service;

import com.duc.register_login.entity.User;
import com.duc.register_login.exception.UserNotFoundException;

public interface UserService {
    public User saveUser(User user, String url);
    public void removeSessionMessage();
    public User get(Integer id) throws UserNotFoundException;
    public void delete(Integer id) throws UserNotFoundException;
    public void save(User user);
    public void sendEmail(User user, String path);
    public boolean verifyAccount(String verificationCode);
}
