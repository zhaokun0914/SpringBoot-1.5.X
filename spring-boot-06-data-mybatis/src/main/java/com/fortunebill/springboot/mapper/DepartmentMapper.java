package com.fortunebill.springboot.mapper;

import com.fortunebill.springboot.bean.Department;
import org.apache.ibatis.annotations.*;

/**
 * 指定这是一个操作数据库的Mapper
 *
 * @author kevin
 * @date 2021年11月17日21:58:00
 */
//@Mapper
public interface DepartmentMapper {

    @Select("select * from department where id = #{id}")
    Department getDeptById(Integer id);

    @Delete("delete from department where id = #{id}")
    int deleteDeptById(Integer id);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into department(department_name) value (#{departmentName})")
    int insertDept(Department department);

    @Update("update department set department_name = #{departmentName} where id = #{id}")
    int updateDept(Department department);

}
