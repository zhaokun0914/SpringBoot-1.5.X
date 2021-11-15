package com.fortunebill.springboot.controller;

import com.fortunebill.springboot.dao.EmployeeDao;
import com.fortunebill.springboot.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;
import java.util.Map;

/**
 * @author Kavin
 * @date 2021年11月15日13:01:58
 */
@Controller
public class EmployeeController {

    @Autowired
    private EmployeeDao employeeDao;

    @GetMapping("/emps")
    public String list(Map<String, Object> map) {
        Collection<Employee> all = employeeDao.getAll();

        map.put("emps", all);
        // thymeleaf默认就会拼串，从classpath:/templates/emp/list.html
        return "emp/list";
    }

}
