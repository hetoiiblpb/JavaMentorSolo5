package ru.hetoiiblpb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.hetoiiblpb.exception.DBException;
import ru.hetoiiblpb.model.User;
import ru.hetoiiblpb.service.UserService;

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
    public ModelAndView addPage(@ModelAttribute("user") User user) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/editPage");
        return modelAndView;
    }

    @RequestMapping(value = "/admin/addUser", method = RequestMethod.POST)
    public ModelAndView addUser(@ModelAttribute("user") User user) throws DBException {
        ModelAndView modelAndView = new ModelAndView();
        if (user.getRole().equals("")) {
            user.setRole("ROLE_USER");
        }
        if (userService.addUser(user)) {
            modelAndView.setViewName("redirect:/admin");
        }
        return modelAndView;
    }

    @RequestMapping(value="/admin/deleteUser/{id}", method = RequestMethod.GET)
    public ModelAndView deleteUser(@PathVariable("id") Long id) throws DBException {
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


    @GetMapping(value = "/registration")
    public ModelAndView registrationPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @PostMapping(value = "/registration")
    public ModelAndView registration(@ModelAttribute("user") User user, HttpServletRequest request) throws DBException {
        ModelAndView modelAndView = new ModelAndView();
        if (user.getRole().equals("")) {
            user.setRole("ROLE_USER");
        }
        userService.addUser(user);
        HttpSession httpSession = request.getSession(true);
        httpSession.setAttribute("userSession", user);
        modelAndView.addObject("user", user);
        modelAndView.setViewName("redirect:/helloUser");
        return modelAndView;
    }

    @GetMapping(value = "/helloUser")
    public ModelAndView helloUser(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        ModelAndView modelAndView = new ModelAndView();
        User user = (User) session.getAttribute("userSession");
        modelAndView.setViewName("helloUser");
        modelAndView.addObject("user", user);
        return  modelAndView;
    }

    @PostMapping(value = "/helloUser")
    public ModelAndView logout(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        HttpSession session = request.getSession(false);
        session.invalidate();
        modelAndView.setViewName("redirect:/login");
        return modelAndView;
    }

    @GetMapping(value = "/accessDenied")
    public ModelAndView accessDenied(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        HttpSession session = request.getSession(false);
        modelAndView.setViewName("accessDenied");
        return modelAndView;
    }
}