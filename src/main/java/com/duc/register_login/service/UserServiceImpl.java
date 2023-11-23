package com.duc.register_login.service;

import com.duc.register_login.entity.User;
import com.duc.register_login.exception.UserNotFoundException;
import com.duc.register_login.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Override
    public User saveUser(User user) {
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        user.setRole("ROLE_USER");
        User newUser = userRepository.save(user);
        return newUser;
    }

    @Override
    public void removeSessionMessage() {
        HttpSession session = ((ServletRequestAttributes)(RequestContextHolder.getRequestAttributes())).
                getRequest().getSession();
        session.removeAttribute("msg");
    }

    @Override
    public User get(Integer id) throws UserNotFoundException {
        Optional<User> result = userRepository.findById(id);
        if(result.isPresent()) {
            return result.get();
        }
        throw new UserNotFoundException("Could not find any users with ID" + id);
    }

    @Override
    public void delete(Integer id) throws UserNotFoundException {
        Long count = userRepository.countById(id);
        if(count == null || count == 0) {
            throw new UserNotFoundException("Could not find any users with ID" + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }
}
