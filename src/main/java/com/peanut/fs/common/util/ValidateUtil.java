package com.peanut.fs.common.util;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @description:
 * @author:zfs
 * @date:created in 10:01 2019/7/18
 */
public class ValidateUtil {

    private static Validator validator = Validation.buildDefaultValidatorFactory()
            .getValidator();

    public static <T> Map<String, StringBuilder> validate(T obj){
        Map<String,StringBuilder> errorMap = null;
        Set<ConstraintViolation<T>> set = validator.validate(obj,Default.class);
        if(set != null && set.size() >0 ){
            errorMap = new HashMap<>();
            String property = null;
            for(ConstraintViolation<T> cv : set){
                //这里循环获取错误信息，可以自定义格式
                property = cv.getPropertyPath().toString();
                if(errorMap.get(property) != null){
                    errorMap.get(property).append(",").append(cv.getMessage());
                }else{
                    StringBuilder sb = new StringBuilder();
                    sb.append(cv.getMessage());
                    errorMap.put(property, sb);
                }
            }
        }
        return errorMap;
    }

    public static boolean isOperationSuccess(int intValue){
        return intValue > 0;
    }


}
