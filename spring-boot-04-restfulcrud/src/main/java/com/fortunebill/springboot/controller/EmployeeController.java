package com.fortunebill.springboot.controller;

import com.fortunebill.springboot.dao.DepartmentDao;
import com.fortunebill.springboot.dao.EmployeeDao;
import com.fortunebill.springboot.entities.Department;
import com.fortunebill.springboot.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private DepartmentDao departmentDao;

    @GetMapping("/emps")
    public String list(Map<String, Object> map) {
        Collection<Employee> all = employeeDao.getAll();
        map.put("emps", all);
        // thymeleaf默认就会拼串，从classpath:/templates/emp/list.html
        return "emp/list";
    }

    @GetMapping("/emp")
    public String toAddPage(Map<String, Object> map) {
        // 来到添加页面之前，先查出所有部门
        Collection<Department> departments = departmentDao.getDepartments();
        map.put("depts", departments);
        return "emp/add";
    }

    /**
     * SpringMVC自动将请求参数和入参对象的属性一一绑定
     * 要求请求参数的名字和javabean对象的属性名一样
     * @param employee
     * @return
     */
    @PostMapping("/emp")
    public String addEmp(Employee employee) {
        employeeDao.saveOrUpdate(employee);
        // 来到员工列表页面，redirect:重定向到一个地址，forward:转发到一个地址
        return "redirect:/emps";
    }

    /**
     * 来到修改页面，查出当前员工，在页面回显
     * @param id
     * @param map
     * @return
     */
    @GetMapping("/emp/{id}")
    public String toEditEmp(@PathVariable("id") Integer id, Map<String, Object> map) {
        Employee employee = employeeDao.get(id);
        map.put("emp", employee);

        Collection<Department> departments = departmentDao.getDepartments();
        map.put("depts", departments);
        return "emp/add";
    }

    /**
     * 更新员工
     * @param employee
     * @return
     */
    @PutMapping("/emp")
    public String updateEmployee(Employee employee) {
        employeeDao.saveOrUpdate(employee);
        return "redirect:/emps";
    }

    /**
     * 员工删除
     * @param id
     * @return
     */
    @DeleteMapping("/emp/{id}")
    public String deleteEmployee(@PathVariable("id") Integer id) {
        employeeDao.delete(id);
        return "redirect:/emps";
    }

}
