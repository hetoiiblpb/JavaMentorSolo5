package ru.hetoiiblpb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.hetoiiblpb.exception.DBException;
import ru.hetoiiblpb.model.User;
import ru.hetoiiblpb.service.UserService;
import ru.hetoiiblpb.service.UserServiceImpl;

import java.sql.SQLException;
import java.util.List;

@Controller
public class UserController {
    private UserService userService ;
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView allUsers() throws SQLException, DBException {
        List<User> users = userService.getAllUsers();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("allUsers");
        modelAndView.addObject("users",users);
        return modelAndView;
    }

    @RequestMapping(value = "/admin/updateUser/{id}", method = RequestMethod.GET)
    public ModelAndView editPage(@PathVariable("id") Long id) throws DBException {
        User user = userService.getUserById(id);
        System.out.println(user.toString());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("editPage");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @RequestMapping(value = "/admin/updateUser/", method = RequestMethod.POST)
    public ModelAndView updateUser(@ModelAttribute ("user") User user) throws DBException {
        ModelAndView modelAndView = new ModelAndView();
        System.out.println(user.toString());
        modelAndView.setViewName("redirect:/admin/");
        userService.updateUser(user);
        return modelAndView;
    }

    @RequestMapping(value = "/admin/addUser", method = RequestMethod.GET)
    public ModelAndView addPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("editPage");
        return modelAndView;
    }

    @RequestMapping(value = "/admin/addUser", method = RequestMethod.POST)
    public ModelAndView addUser(@ModelAttribute("user") User user) throws DBException {
        ModelAndView modelAndView = new ModelAndView();
        if (userService.addUser(user)) {
            modelAndView.setViewName("redirect:/admin/");
        }
        return modelAndView;
    }

    @RequestMapping(value="/admin/deleteUser/{id}", method = RequestMethod.GET)
    public ModelAndView deleteFilm(@PathVariable("id") Long id) throws DBException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/");
        userService.deleteUser(id);
        return modelAndView;
    }
}