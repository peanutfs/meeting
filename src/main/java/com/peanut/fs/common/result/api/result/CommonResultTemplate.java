package com.peanut.fs.common.result.api.result;


import com.peanut.fs.common.enums.ResponseEnum;
import com.peanut.fs.common.exceptions.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;


/**
 * @description:
 * @author:peanutfs
 * @date:created in 15:03 2019/9/07
 */
@Slf4j
public class CommonResultTemplate {

    public static ApiCommonResult execute(Callback callback) {
        ApiCommonResult apiCommonResult;
        try {
            apiCommonResult = new ApiCommonResult();
            apiCommonResult.setBody(callback.execute());
        } catch (CommonException ce){
            if(StringUtils.isNotEmpty(ce.getErrCode())){
                apiCommonResult = new ApiCommonResult(ce.getErrCode(), ce.getErrMsg());
            }else {
                apiCommonResult = new ApiCommonResult(ce.getErrMsg());
            }
        } catch (Exception e) {
            log.error("[CommonResultTemplate.execute]执行异常e:{}", e);
            apiCommonResult = new ApiCommonResult(ResponseEnum.SERVER_ERROR);
        }
        return apiCommonResult;
    }

    public interface Callback {
        /**
         * 通用执行方法
         * @return
         */
        Object execute();
    }

}
