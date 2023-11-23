package com.duc.register_login.service;

import com.duc.register_login.entity.User;
import com.duc.register_login.exception.UserNotFoundException;

public interface UserService {
    public User saveUser(User user);
    public void removeSessionMessage();
    public User get(Integer id) throws UserNotFoundException;
    public void delete(Integer id) throws UserNotFoundException;
    public void save(User user);
}
