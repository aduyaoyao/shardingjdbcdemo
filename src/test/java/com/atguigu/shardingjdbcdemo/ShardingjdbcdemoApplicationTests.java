package com.atguigu.shardingjdbcdemo;

import com.atguigu.shardingjdbcdemo.entity.Course;
import com.atguigu.shardingjdbcdemo.entity.Udict;
import com.atguigu.shardingjdbcdemo.entity.User;
import com.atguigu.shardingjdbcdemo.mapper.CourseMapper;
import com.atguigu.shardingjdbcdemo.mapper.UdictMapper;
import com.atguigu.shardingjdbcdemo.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Wrapper;

@SpringBootTest
class ShardingjdbcdemoApplicationTests {

    //注入Mapper
    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UdictMapper udictMapper;

    //测试水平分表
    @Test
    void addCourse1() {
        for (int i = 0; i < 10; i++) {
            Course course = new Course();
            course.setCname("java"+i);
            course.setUserId(100L);
            course.setCstatus("Normal"+i);
            courseMapper.insert(course);
        }
    }
    @Test
    void select1(){
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cid", 709063885038026752L);
        Course course = courseMapper.selectOne(queryWrapper);
        System.out.println(course.toString());
    }

    //测试水平分库
    @Test
    void addCourse2() {
        Course course = new Course();
        course.setCname("javademo1");
        //分库根据 user_id
        course.setUserId(100L);
        course.setCstatus("Normal1");
        courseMapper.insert(course);
    }
    @Test
    void select2(){
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        //设置 userid 值
        wrapper.eq("user_id",100L);
        //设置 cid 值
        wrapper.eq("cid",709073553256873985L);
        Course course = courseMapper.selectOne(wrapper);
        System.out.println(course);
    }
    //测试垂直分库
    @Test
    public void add3() {
        User user = new User();
        user.setUsername("lucy");
        user.setUstatus("a");
        userMapper.insert(user);
    }
    @Test
    void select3(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",709083354305134593L);
        User user = userMapper.selectOne(wrapper);
        System.out.println(user);
    }

    @Test
    public void addDict() {
        Udict udict = new Udict();
        udict.setUstatus("a");
        udict.setUvalue("已启用");
        udictMapper.insert(udict);
    }

    @Test
    public void deleteDict() {
        QueryWrapper<Udict> wrapper = new QueryWrapper<>();
        //设置 userid 值
        wrapper.eq("dictid",709088530961268737L);
        udictMapper.delete(wrapper);
    }
}
