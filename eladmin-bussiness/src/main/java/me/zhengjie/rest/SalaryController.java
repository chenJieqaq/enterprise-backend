/*
*  Copyright 2019-2020 Zheng Jie
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*  http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/
package me.zhengjie.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.domain.Salary;
import me.zhengjie.service.SalaryService;
import me.zhengjie.service.dto.SalaryQueryCriteria;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://eladmin.vip
* @author chenjie
* @date 2023-04-10
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "薪资管理接口管理")
@RequestMapping("/api/salary")
public class SalaryController {

    private final SalaryService salaryService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('salary:list')")
    public void exportSalary(HttpServletResponse response, SalaryQueryCriteria criteria) throws IOException {
        salaryService.download(salaryService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询薪资管理接口")
    @ApiOperation("查询薪资管理接口")
    @PreAuthorize("@el.check('salary:list')")
    public ResponseEntity<Object> querySalary(SalaryQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(salaryService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增薪资管理接口")
    @ApiOperation("新增薪资管理接口")
    @PreAuthorize("@el.check('salary:add')")
    public ResponseEntity<Object> createSalary(@Validated @RequestBody Salary resources){
        return new ResponseEntity<>(salaryService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改薪资管理接口")
    @ApiOperation("修改薪资管理接口")
    @PreAuthorize("@el.check('salary:edit')")
    public ResponseEntity<Object> updateSalary(@Validated @RequestBody Salary resources){
        salaryService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除薪资管理接口")
    @ApiOperation("删除薪资管理接口")
    @PreAuthorize("@el.check('salary:del')")
    public ResponseEntity<Object> deleteSalary(@RequestBody Integer[] ids) {
        salaryService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}