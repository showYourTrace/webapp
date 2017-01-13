package com.showyourtrace.controller.admin;

import com.showyourtrace.domain.ReferenceDomain;
import com.showyourtrace.domain.UserDomain;
import com.showyourtrace.exception.ErrorHandler;
import com.showyourtrace.exception.ErrorResponse;
import com.showyourtrace.object.request.UserCreateRequest;
import com.showyourtrace.object.request.UserSearchRequest;
import com.showyourtrace.object.request.UserUpdateRequest;
import com.showyourtrace.object.response.UserResponse;
import com.showyourtrace.object.response.UserSearchResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/admin/api")
public class AdminController {

    private static final Logger log = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private UserDomain userDomain;

    @Autowired
    private ReferenceDomain referenceDomain;

    @Autowired
    private ErrorHandler errorHandler;

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponse handleExceptions(Exception ex, HttpServletResponse response) {
        return errorHandler.handleExceptions(ex, response);
    }

// ================================== USERS ==========================================================================
    @RequestMapping(value = "/user/getdetails/{id}", method = RequestMethod.GET)
    @ResponseBody
    public UserResponse showUserDetails(@PathVariable Long id) {
        return userDomain.get(id);
    }

    @RequestMapping(value = "/user/search", method = RequestMethod.POST)
    @ResponseBody
    public Page<UserSearchResponse> search(@RequestBody UserSearchRequest request) {
        return userDomain.search(request);
    }

    @RequestMapping(value = "/user/create", method = RequestMethod.POST)
    @ResponseBody
    public void createUser(@RequestBody UserCreateRequest request) {
        userDomain.create(request);
    }

    @RequestMapping(value = "/user/update", method = RequestMethod.POST)
    @ResponseBody
    public void updateUser(@RequestBody UserUpdateRequest request) {
        userDomain.update(request);
    }


    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public boolean deleteUser(@RequestParam Long id) {
        return userDomain.delete(id);
    }



    //=================================== REFERENCES =======================================================================
//    @RequestMapping(value = "/deal/allTypes", method = RequestMethod.GET)
//    @ResponseBody
//    public List<ReferenceResponse> getAllTypes() {
//        return referenceDomain.getAllTypes();
//    }
//
//    @RequestMapping(value = "/deal/allCategories", method = RequestMethod.GET)
//    @ResponseBody
//    public List<DealCategoryResponse> getAllCategories() {
//        return referenceDomain.getAllCategories();
//    }

}
