package com.peanut.fs.controller;

import com.github.pagehelper.PageInfo;
import com.peanut.fs.common.result.CommonResult;
import com.peanut.fs.common.util.FileUploadUtil;
import com.peanut.fs.dao.command.MeetingInfoCommand;
import com.peanut.fs.service.meeting.MeetingInfoService;
import com.peanut.fs.service.meeting.dto.MeetingInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/meeting")
public class MeetingInfoController {

    @Autowired
    private MeetingInfoService meetingInfoService;

    @RequestMapping("/addMeeting")
    @ResponseBody
    public CommonResult addMeeting(MeetingInfoDto meetingInfoDto){
        log.info("[MeetingInfoController.addMeeting]新增会议开始meetingInfoDto:{}", meetingInfoDto);
        CommonResult commonResult = meetingInfoService.insert(meetingInfoDto);
        log.info("[MeetingInfoController.addMeeting]新增会议结束commonResult:{}", commonResult);
        return commonResult;
    }

    @RequestMapping("/addMeetingForm")
    public String helloUser(Model model) {
        return "meeting/meeting-add.ftl";
    }

    @RequestMapping("/meetingInfoForm")
    public String meetingInfoForm(Model model) {
        return "meeting/meeting-list.ftl";
    }

    @RequestMapping("/meetingInfo")
    @ResponseBody
    public Map<String, Object> meetingInfo(int page, int limit) {
        log.info("[MeetingInfoController.meetingInfo]查询app版本信息开始page:{}, limit:{}", page, limit);
        PageInfo<MeetingInfoDto> meetingInfoModelPageInfo = meetingInfoService.selectByPageWithCheckIn(buildMeetingInfoCommand(page, limit));
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("code",0);
        resultMap.put("msg","");
        resultMap.put("count",String.valueOf(meetingInfoModelPageInfo.getTotal()));
        resultMap.put("data",meetingInfoModelPageInfo.getList());
        log.info("[MeetingInfoController.meetingInfo]查询app版本信息结束resultMap:{}", resultMap);
        return resultMap;
    }

    private MeetingInfoCommand buildMeetingInfoCommand(int page, int limit) {
        MeetingInfoCommand meetingInfoCommand = new MeetingInfoCommand();
        meetingInfoCommand.setLimit(limit);
        meetingInfoCommand.setPage(page);
        return meetingInfoCommand;
    }

    @RequestMapping("/uploadImage")
    @ResponseBody
    public CommonResult uploadAndroidPackage(@RequestParam("file")MultipartFile multipartFile, HttpServletRequest request) {
        log.info("[MeetingInfoController.uploadAndroidPackage]上传文件开始");
        CommonResult<String> commonResult = new CommonResult<>(CommonResult.failureCode);
        String path = "E:/logs";
        String visitPath = FileUploadUtil.uploadFile(multipartFile, path, path);
        if(StringUtils.isNotEmpty(visitPath)){
            commonResult.setSuccess(CommonResult.successCode);
        }
        commonResult.setData(visitPath);
        return commonResult;
    }

    @RequestMapping("/editMeetingInfoForm")
    public String editMeetingInfoForm(Model model, long id) {
        log.info("[MeetingInfoController.editMeetingInfoForm]编辑会议信息页面查询开始id:{}", id);
        model.addAttribute("meetingInfo", meetingInfoService.selectById(id));
        return "meeting/meeting-edit.ftl";
    }

    @RequestMapping("/editMeetingInfo")
    @ResponseBody
    public CommonResult editMeetingInfo(MeetingInfoDto meetingInfoDto) {
        log.info("[MeetingInfoController.editMeetingInfo]更新会议信息开始meetingInfoDto:{}", meetingInfoDto);
        CommonResult commonResult = meetingInfoService.update(meetingInfoDto);
        log.info("[MeetingInfoController.editMeetingInfo]更新会议信息结束commonResult:{}", commonResult);
        return commonResult;
    }

    @RequestMapping("/delMeetingInfo")
    @ResponseBody
    public CommonResult delMeetingInfo(long id) {
        log.info("[MeetingInfoController.editMeetingInfo]删除会议信息开始id:{}", id);
        CommonResult commonResult = meetingInfoService.delete(id);
        log.info("[MeetingInfoController.editMeetingInfo]删除会议信息结束commonResult:{}", commonResult);
        return commonResult;
    }

    @RequestMapping("/changeEffectiveStatus")
    @ResponseBody
    public CommonResult changeEffectiveStatus(long id, String isEffective) {
        log.info("[MeetingInfoController.changeEffectiveStatus]修改会议生效状态开始id:{}, isEffective", id, isEffective);
        CommonResult commonResult = meetingInfoService.updateEffectiveStatus(id, isEffective);
        log.info("[MeetingInfoController.editMeetingInfo]修改会议生效状态结束commonResult:{}", commonResult);
        return commonResult;
    }
}
