package habraspring.controllers;

import habraspring.entities.User;
import habraspring.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.thymeleaf.spring4.view.ThymeleafView;

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

    @RequestMapping(method = RequestMethod.POST)
    public User addUser(String username, String password, String password_confirm)
    {
        //no empty fields allowed
        if (username.isEmpty() || password.isEmpty() || password_confirm.isEmpty())
            return null;
        //passwords should match
        if (!password.equals(password_confirm))
            return null;
        return users.save(new User(username, password));
    }

    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public ModelAndView getUserForm()
    {
        return new ModelAndView("add");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id)
    {
        users.delete(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable("id") Long id)
    {
        return users.findOne(id);
    }
}
