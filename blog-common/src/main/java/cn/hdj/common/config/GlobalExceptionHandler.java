package cn.hdj.common.config;


import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.hdj.common.domain.vo.ResultVO;
import cn.hdj.common.enums.ResponseCodeEnum;
import cn.hdj.common.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * @Description: 全局异常处理
 * @Author huangjiajian
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 全局异常类中定义的异常都可以被拦截，只是触发条件不一样，如IO异常这种必须抛出异常到
     * controller中才可以被拦截，或者在类中用try..catch自己处理
     * 绝大部分不需要向上抛出异常即可被拦截，返回前端json数据，如数组下标越界，404 500 400等错误
     * 如果自己想要写，按着以下格式增加异常即可
     */


    /**
     * 启动应用后，被 @ExceptionHandler、@InitBinder、@ModelAttribute 注解的方法，
     * 都会作用在 被 @RequestMapping 注解的方法上。
     *
     * @param binder
     */
    @InitBinder
    public void initWebBinder(WebDataBinder binder) {

    }


    @ExceptionHandler({
            NotPermissionException.class
    })
    @ResponseBody
    public ResultVO exception(NotPermissionException ex) {
        log.error("错误详情：" + ex.getMessage());
        return ResultVO.errorJson(ex.getMessage(), ResponseCodeEnum.NO_AUTH.getCode());
    }

    @ExceptionHandler({
            NotLoginException.class
    })
    @ResponseBody
    public ResultVO exception(NotLoginException ex) {
        log.error("错误详情：" + ex.getMessage());
        return ResultVO.errorJson(ex.getMessage(), ResponseCodeEnum.TOKEN_INVALID.getCode());
    }

    /**
     * 处理自定义异常
     *
     * @param ex 异常信息
     * @return 返回前端异常信息
     */
    @ExceptionHandler(BaseException.class)
    @ResponseBody
    public ResultVO exception(BaseException ex) {
        log.error("错误详情：" + ex.getMessage(), ex);
        return ResultVO.errorJson(ex.getMessage(), ex.getCode());
    }


    /**
     * 处理路径找不到异常
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NoHandlerFoundException.class})
    public ResultVO handlerNoFoundException(NoHandlerFoundException e) {
        log.error("错误详情：" + e.getMessage());
        return ResultVO.errorJson(ResponseCodeEnum.PATH_NOT_FOUND);
    }


    /**
     * 校验异常
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = BindException.class)
    public ResultVO handleValidException(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message = fieldError.getField() + fieldError.getDefaultMessage();
            }
        }
        return ResultVO.errorJson(message);
    }


    /**
     * 系统其它异常(兜底)
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({
            Exception.class,
            RuntimeException.class
    })
    public ResultVO handleException(Throwable e) {
        log.error("错误详情：" + e.getMessage(), e);
        return ResultVO.errorJson(e.getMessage());
    }

}
