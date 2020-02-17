package com.example.background.dao;


import java.util.List;

import com.example.background.entity.*;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    //用户首次登录小程序
    @Select("select * from user where openid=#{openid}")
    public User selectUserById(String openid);

    //@Insert("insert into user(openid,jing,wei) values (#{openid},#{jing},#{wei})")
    //public void addUser(User user);
    @Insert("insert into user(openid,passenger) values (#{openid},1)")
    public void addUser(User user);

    @Update("update user set jing=#{jing},wei=#{wei} where openid=#{openid}")
    public void updateUser(User user);

    @Delete("delete from user where openid=#{openid}")
    public void deleteUser(String openid);

    //乘客
    @Insert("insert into data(openid,jing,wei) values (#{openid},#{jing},#{wei})")
    public void adddata(Data data);

    @Select("select * from aim where go=#{aim}")
    public Aim selectAim(String aim);

    @Update("update map set dir=#{dir},road=#{road},jing=#{jing},wei=#{wei} where openid=#{openid}")
    public void updateMap(Map map);

    @Insert("insert into map(openid,jing,wei,dir,road) values (#{openid},#{jing},#{wei},#{dir},#{road})")
    public void addMap(Map map);

    @Select("select * from map where openid=#{openid}")
    public Map selectMap(String openid);

    @Select("select * from mapd where dir=1 and wei<#{wei} and road=#{road}")
    public List<Mapd> selectMapd1(double wei, int road);

    @Select("select * from mapd where dir=-1 and wei>#{wei} and road=#{road}")
    public List<Mapd> selectMapd2(double wei, int road);

    @Select("select * from mapd where dir=#{dir} and road=#{road}")
    public List<Mapd> selectMapd3(int dir, int road);

    //司机
    @Insert("insert into driver(openid,jing,wei) values (#{openid},#{jing},#{wei})")
    public void adddriver(Data data);

    @Select("select * from mapd where openid=#{openid}")
    public Mapd selectMapd(String openid);

    @Update("update mapd set jing=#{jing},wei=#{wei} where openid=#{openid}")
    public void updateMapd(Mapd mapd);

    @Update("update mapd set last=#{now},now=#{now},road=#{road} where openid=#{openid}")
    public void updateMapdlast(String openid, int now, int road);

    @Update("update mapd set last=#{last},now=#{now},road=#{road},dir=#{dir} where openid=#{openid}")
    public void updateMapdnow(String openid,int last, int now, int road, int dir);

    @Insert("insert into mapd(openid,jing,wei,dir,road) values (#{openid},#{jing},#{wei},#{dir},#{road})")
    public void addMapd(Mapd mapd);

//    @Select("select * from driver where openid=#{openid} order by uptime desc limit 2")
//    public List<Driver> selectDriver(String openid);

    @Select("select * from driver where openid=#{openid} order by uptime desc limit 1")
    public Driver selectDriver(String openid);

    @Select("select * from luwang1")
    public List<Luwang> selectLuwang1();

    @Select("select * from luwang2")
    public List<Luwang> selectLuwang2();

    //变更用户信息
    @Update("update user set passenger=#{passenger} where openid=#{openid}")
    public void changeUser(String openid, int passenger);
}