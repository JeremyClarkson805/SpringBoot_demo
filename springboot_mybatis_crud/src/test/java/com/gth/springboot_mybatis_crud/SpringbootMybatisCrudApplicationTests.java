package com.gth.springboot_mybatis_crud;

import com.gth.springboot_mybatis_crud.mapper.EmpMapper;
import com.gth.springboot_mybatis_crud.pojo.Emp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class SpringbootMybatisCrudApplicationTests {
    @Autowired
    private EmpMapper empMapper;

    @Test
    void contextLoads() {
    }

    @Test
    public void testDelete(){
        empMapper.delete(1);
    }

    @Test
    public void testInsert()
    {
        Emp e = new Emp();
        e.setUsername("Tom");
        e.setName("汤姆");
        e.setPassword("123456");
        e.setImage("3.jpg");
        e.setGender((short)1);
        e.setJob((short)1);
        e.setDeptId(6);
        e.setEntryDate(LocalDate.of(2005,12,31));
        e.setUpdateTime(LocalDateTime.now());
        e.setCreateTime(LocalDateTime.now());

        empMapper.insert(e);
        System.out.println(e.getId());
    }

    @Test
    public void testUpdate()
    {
        Emp e = new Emp();
        e.setId(4);
        e.setUsername("Tom");
        e.setName("汤姆");
        e.setPassword("123456");
        e.setImage("3.jpg");
        e.setGender((short)1);
        e.setJob((short)1);
        e.setDeptId(4);
        e.setEntryDate(LocalDate.of(2005,12,31));
        e.setUpdateTime(LocalDateTime.now());
        e.setCreateTime(LocalDateTime.now());

        empMapper.update(e);
        System.out.println(e.getId());
    }

    @Test
    public void testGetById(){
        Emp e = empMapper.getById(1);
        System.out.println(e);
    }

    @Test
    public void testList(){
//        empMapper.list("金",(short)1,LocalDate.of(1999,1,1),LocalDate.of(2010,12,31));

        List<Emp> list = empMapper.list(null,null,null,null);
        System.out.println(list);
    }

    @Test
    public void testUpdate_2(){
        Emp e = new Emp();
        e.setId(4);
        e.setUsername("Fucking Tom");
        e.setName("汤姆");
        e.setPassword("316868");
        e.setGender((short)2);
        e.setUpdateTime(LocalDateTime.now());
        empMapper.update2(e);
    }

    @Test
    public void testDeleteByIds(){
        List<Integer> ids = Arrays.asList(8,9,10,11,12);
        empMapper.deleteByIds(ids);
    }
}
