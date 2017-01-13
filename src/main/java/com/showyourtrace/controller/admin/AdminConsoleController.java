package com.showyourtrace.controller.admin;

import com.showyourtrace.service.TicketAuthenticationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminConsoleController {

    private static final Logger log = LoggerFactory.getLogger(AdminConsoleController.class);

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView showAdminConsole(
    ) {
        ModelAndView mv = new ModelAndView("admin");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication instanceof TicketAuthenticationToken) {
            mv.addObject("auth", false);
        } else {
            mv.addObject("auth", true);
        }
        return mv;
    }
}
