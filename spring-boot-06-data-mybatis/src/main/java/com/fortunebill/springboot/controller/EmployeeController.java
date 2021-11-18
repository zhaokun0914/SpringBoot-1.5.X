package com.fortunebill.springboot.controller;

import com.fortunebill.springboot.bean.Employee;
import com.fortunebill.springboot.bean.EmployeeExample;
import com.fortunebill.springboot.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeMapper employeeMapper;

    @GetMapping
    public List<Employee> findAll() {
        EmployeeExample example = new EmployeeExample();
        return employeeMapper.selectByExample(example);
    }

    @GetMapping("/{id}")
    public Employee findById(@PathVariable("id") int id) {
        return employeeMapper.selectByPrimaryKey(id);
    }

    @PostMapping
    public Integer create(Employee employee) {
        return employeeMapper.insert(employee);
    }
}
