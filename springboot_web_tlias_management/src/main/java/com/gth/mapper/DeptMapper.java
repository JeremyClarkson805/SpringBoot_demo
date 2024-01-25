package com.gth.mapper;

import com.gth.pojo.Dept;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DeptMapper {
    //查询全部部门
    @Select("select * from dept")
    List<Dept> list();

    //根据Id删除部门
    @Delete("delete from dept where id = #{id}")
    void deleteById(Integer id);

    //插入部门
    @Insert("insert into dept (name, create_time, update_time) values (#{name},#{createTime},#{updateTime})")
    void insert(Dept dept);
}
