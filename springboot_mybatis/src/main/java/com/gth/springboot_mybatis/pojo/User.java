package com.gth.springboot_mybatis.pojo;

import lombok.*;

//@Getter
//@Setter
//@ToString
//@EqualsAndHashCode
@Data//这个注解相当于上面四个注解相加
@NoArgsConstructor//无参构造
@AllArgsConstructor//全参构造
public class User {

    private Integer id;
    private String name;
    private Short age;
    private short gender;
    private String phone;

//    public User() {
//
//    }
//
//    public User(Integer id, String name, Short age, short gender, String phone) {
//        this.id = id;
//        this.name = name;
//        this.age = age;
//        this.gender = gender;
//        this.phone = phone;
//    }
//
//    public Integer getId() {
//        return id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public Short getAge() {
//        return age;
//    }
//
//    public short getGender() {
//        return gender;
//    }
//
//    public String getPhone() {
//        return phone;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setAge(Short age) {
//        this.age = age;
//    }
//
//    public void setGender(short gender) {
//        this.gender = gender;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }


}
