package com.showyourtrace.controller.admin;

import com.showyourtrace.exception.ErrorHandler;
import com.showyourtrace.exception.ErrorResponse;
import com.showyourtrace.repository.UserRepository;
import com.showyourtrace.service.mailer.Mailer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/admin/api/mailing")
public class MailingController {

    private static final Logger log = LoggerFactory.getLogger(MailingController.class);

    @Autowired
    private ErrorHandler errorHandler;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Mailer mailer;

    @Autowired
    private ServletContext context;

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponse handleExceptions(Exception ex, HttpServletResponse response) {
        return errorHandler.handleExceptions(ex, response);
    }

    //=================================== MAILING =============================================================================
//    @RequestMapping(value = "/sendPromo", method = RequestMethod.POST)
//    @ResponseBody
//    @Transactional
//    public boolean sendPromo(@RequestBody PromoMailingRequest request) {
//        File realPathFolder = new File(context.getRealPath(""));
//        final String attachmentsFolder = realPathFolder.getParentFile().getAbsolutePath() + "/filestore";
//
//        final List<User> subscribers = userRepository.getAllSubscribers();
//
//        ExecutorService executor = Executors.newCachedThreadPool();
//        executor.execute(() -> {
//
//            for(User user : subscribers) {
//                try {
//                    Mailer.CustomMessage customMessage = mailer.new CustomMessage(user.getEmail(), request.getSubject(), request.getBody());
//                    for(String fileName : request.getFiles()) {
//                        customMessage.addAttachment(attachmentsFolder, fileName);
//                    }
//                    customMessage.send();
//                } catch (MessagingException e) {
//                    log.error("Error sending promo emails: ", e);
//                }
//            }
//
//        });
//
//        return true;
//    }
}
