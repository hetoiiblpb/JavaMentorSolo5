package ru.hetoiiblpb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.hetoiiblpb.exception.DBException;
import ru.hetoiiblpb.model.User;
import ru.hetoiiblpb.service.UserService;
import ru.hetoiiblpb.service.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
        modelAndView.setViewName("admin/allUsers");
        modelAndView.addObject("users",users);
        return modelAndView;
    }

    @RequestMapping(value = "/admin/updateUser/{id}", method = RequestMethod.GET)
    public ModelAndView editPage(@PathVariable("id") Long id) throws DBException {
        User user = userService.getUserById(id);
        System.out.println(user.toString());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/editPage");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @RequestMapping(value = "/admin/updateUser", method = RequestMethod.POST)
    public ModelAndView updateUser(@ModelAttribute ("user") User user) throws DBException {
        ModelAndView modelAndView = new ModelAndView();
        System.out.println(user.toString());
        userService.updateUser(user);
        modelAndView.setViewName("redirect:/admin");
        return modelAndView;
    }

    @RequestMapping(value = "/admin/addUser", method = RequestMethod.GET)
    public ModelAndView addPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/editPage");
        return modelAndView;
    }

    @RequestMapping(value = "/admin/addUser", method = RequestMethod.POST)
    public ModelAndView addUser(@ModelAttribute("user") User user) throws DBException {
        ModelAndView modelAndView = new ModelAndView();
        if (userService.addUser(user)) {
            modelAndView.setViewName("redirect:/admin");
        }
        return modelAndView;
    }

    @RequestMapping(value="/admin/deleteUser/{id}", method = RequestMethod.GET)
    public ModelAndView deleteFilm(@PathVariable("id") Long id) throws DBException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin");
        userService.deleteUser(id);
        return modelAndView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView loginPage () {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("authorization");
        return modelAndView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login(@RequestParam(value = "name") String name, @RequestParam(value = "password") String password, HttpServletRequest request) throws DBException {
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.verifyUserPassword(name, password);
        if (user != null) {
            HttpSession httpSession = request.getSession(true);
            httpSession.setAttribute("userSession",user);
            if (user.getRole().equals("admin")) {
                modelAndView.setViewName("redirect:/admin");
            } else {
                modelAndView.setViewName("redirect:/helloUser");
            }
        } else {
            modelAndView.setViewName("redirect:/login");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/helloUser", method = RequestMethod.GET)
    public ModelAndView helloUser(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        ModelAndView modelAndView = new ModelAndView();
        User user = (User) session.getAttribute("userSession");
        modelAndView.setViewName("helloUser");
        modelAndView.addObject("user", user);
        return  modelAndView;
    }
}