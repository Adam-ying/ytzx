package com.yingtao.ytzx.user.controller;

import com.yingtao.ytzx.model.entity.base.Region;
import com.yingtao.ytzx.model.vo.common.Result;
import com.yingtao.ytzx.model.vo.common.ResultCodeEnum;
import com.yingtao.ytzx.user.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Adam
 * @create 2024-05-15 21:18
 */
@RestController
@RequestMapping("/api/user/region")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @GetMapping("findByParentCode/{parentCode}")
    public Result findByParentCode(@PathVariable String parentCode){
        List<Region> regionList = regionService.findByParentCode(parentCode);
        return Result.build(regionList, ResultCodeEnum.SUCCESS);
    }
}
