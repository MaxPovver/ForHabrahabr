package habraspring.controllers;

import habraspring.entities.User;
import habraspring.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maxim Chistov on 12.07.15.
 */
@RestController
@RequestMapping("/users")
@PreAuthorize("hasRole('ADMIN')")
public class UsersController {
    @Autowired
    UsersRepository users;

    @RequestMapping(method = RequestMethod.GET)
    public List<User> getUsers()
    {
        List<User> result = new ArrayList<>();
        users.findAll().forEach(result::add);
        return result;
    }
}
