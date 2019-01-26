package com.lhiot.ims.contentcenter.api;

import com.leon.microx.web.result.Pages;
import com.leon.microx.web.session.Sessions;
import com.leon.microx.web.swagger.ApiParamType;
import com.lhiot.ims.contentcenter.feign.FeedbackFeign;
import com.lhiot.ims.contentcenter.feign.entity.Feedback;
import com.lhiot.ims.contentcenter.feign.type.FeedbackStatusEnum;
import com.lhiot.util.FeginResponseTools;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;

/**
 * @author hufan created in 2019/1/25 17:11
 **/
@Api(description = "用户反馈接口")
@RestController
@Slf4j
public class FeedbackApi {
    private final FeedbackFeign feedbackFeign;

    @Autowired
    public FeedbackApi(FeedbackFeign feedbackFeign) {
        this.feedbackFeign = feedbackFeign;
    }

    @PostMapping("/feedbacks")
    @ApiImplicitParam(paramType = ApiParamType.BODY, name = "feedback", value = "新增用户反馈内容", dataType = "Feedback")
    @ApiOperation(value = "用户反馈创建")
    public ResponseEntity create(@RequestBody Feedback feedback) {
        log.debug("用户反馈创建\t param:{}", feedback);

        ResponseEntity entity = feedbackFeign.create(feedback);
        return FeginResponseTools.convertNoramlResponse(entity);
    }

    @PutMapping("/feedbacks/{id}")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "反馈id", dataType = "Long", required = true),
            @ApiImplicitParam(paramType = ApiParamType.BODY, name = "feedback", value = "要修改的用户反馈内容", dataType = "Feedback", required = true)
    })
    @ApiOperation(value = "依据id更新用户反馈")
    public ResponseEntity updateById(@PathVariable("id") Long id, @RequestBody Feedback feedback, Sessions.User user) {
        log.debug("依据id更新用户反馈\t id:{} param:{}", id, feedback);

        feedback.setFeedbackAt(Date.from(Instant.now()));
        feedback.setBackEditor((String) user.getUser().get("name"));
        feedback.setStatus(FeedbackStatusEnum.REPLY);
        ResponseEntity entity = feedbackFeign.updateById(id, feedback);
        return FeginResponseTools.convertUpdateResponse(entity);
    }

    @ApiOperation(value = "根据id查询用户反馈", notes = "根据id查询用户反馈")
    @ApiImplicitParam(paramType = ApiParamType.PATH, name = "id", value = "主键id", required = true, dataType = "Long")
    @GetMapping("/feedbacks/{id}")
    public ResponseEntity<Feedback> findFeedback(@PathVariable("id") Long id) {
        log.debug("根据id查询用户反馈\t param:{}", id);

        ResponseEntity entity = feedbackFeign.findFeedback(id);
        return FeginResponseTools.convertNoramlResponse(entity);
    }

    @PostMapping("/feedbacks/pages")
    @ApiImplicitParam(paramType = ApiParamType.BODY, name = "feedback", value = "反馈参数", dataType = "Feedback")
    @ApiOperation(value = "查询用户反馈分页列表", response = Feedback.class, responseContainer = "Set")
    public ResponseEntity<Pages<Feedback>> pageQuery(@RequestBody Feedback feedback) {
        log.debug("查询用户反馈分页列表\t param:{}", feedback);

        ResponseEntity entity = feedbackFeign.pageQuery(feedback);
        return FeginResponseTools.convertNoramlResponse(entity);
    }
}