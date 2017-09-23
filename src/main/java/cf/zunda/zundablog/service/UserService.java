package cf.zunda.zundablog.service;

import cf.zunda.zundablog.Entity.User;
import cf.zunda.zundablog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User findByUserId(String userId) {return userRepository.findByUserId(userId);}


}
