package cn.linbin.worklog.common;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.UndeclaredThrowableException;

/**
 * 统一异常拦截, add by linbin
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ModelAndView processException(Exception e){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/system/error");

        if (e instanceof UndeclaredThrowableException){
            mv.addObject("errorMsg", e.getCause().getMessage());
        }else if (e instanceof LoginException){
            mv.setViewName("/system/login");
        }else {
            mv.addObject("errorMsg", e.getMessage());
        }

        return mv;
    }
}
