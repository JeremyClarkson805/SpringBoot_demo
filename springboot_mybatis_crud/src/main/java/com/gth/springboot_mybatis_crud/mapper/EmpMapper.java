package com.gth.springboot_mybatis_crud.mapper;

import com.gth.springboot_mybatis_crud.pojo.Emp;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;

@Mapper//在运行时，会自动生成该接口的实现对象（代理对象），并且将该对象交给IOC容器管理
public interface EmpMapper {
    //删除
    @Delete("delete from emp where id=#{id}")
    public void delete(Integer id);

    //插入
    @Options(keyProperty = "id",useGeneratedKeys = true) //加了useGeneratedKeys以后会自动的将生成的主键值赋值,keyProperty表名赋值给emp对象的哪个属性
    @Insert("insert into emp (username,name, gender, image, job, entrydate, dept_id, create_time, update_time) " +
            "VALUES (#{username},#{name},#{gender},#{image},#{job},#{entryDate},#{deptId},#{createTime},#{updateTime})")
    public void insert(Emp emp);

    //更新
    @Update("update emp set username=#{username},name=#{name},image=#{image},job=#{job},entrydate=#{entryDate},dept_id=#{deptId},update_time=#{updateTime} where id= #{id}")
    public void update(Emp emp);

    //查询
    //解决数据库中名称和pojo类中名字不一样的解决方案：
    //1.给字段起别名，让别名与实体类属性一致
    //2.通过Results，@Result注解
    //    @Results({//通过Results，@Result注解手动映射数据库中名称和pojo类中名字不一样的值
//            @Result(column = "dept_id",property = "deptId"),
//            @Result(column = "creat_time",property = "createTime"),
//            @Result(column = "update_time",property = "updateTime")
//    })

    //3.开启mybatis的驼峰命名自动映射开关
    @Select("select * from emp where id = #{id}")
    public Emp getById(Integer id);


    //条件查询，通过注解的方式
//    @Select("select * from emp where name regexp concat('.*?',#{name},'.*?') and gender = #{gender} and entrydate between #{begin} and #{end} order by update_time desc")
//    public List<Emp>list (String name, Short gender, LocalDate begin,LocalDate end);

    //通过XML配置文件的方式,实现了动态条件查询
    public List<Emp> list (String name, Short gender, LocalDate begin,LocalDate end);

    //动态更新员工
    public void update2(Emp emp);

    //批量删除员工
    public void deleteByIds(List<Integer> ids);

}
