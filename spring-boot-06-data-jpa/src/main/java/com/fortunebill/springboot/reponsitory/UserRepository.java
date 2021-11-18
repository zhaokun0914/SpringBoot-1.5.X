package com.fortunebill.springboot.reponsitory;

import com.fortunebill.springboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 继承JpaRepository来完成对数据库的操作
 * @author Kavin
 * @date 2021年11月18日14:14:15
 */
public interface UserRepository extends JpaRepository<User, Integer> {
}
