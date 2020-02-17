package com.example.background.controller;

import com.example.background.dao.UserMapper;
import com.example.background.entity.*;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.json.JSONException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;


@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserMapper userMapper;

//    @RequestMapping(value={"/selectUserById"}, method=RequestMethod.GET)
//    public User selectUserById(String openid){
//        User user = userMapper.selectUserById(openid);
//        return user;
//    }
//    //@RequestMapping(value={"/addUser"}, method=RequestMethod.POST)
//    //public void addUser(User user){
//    //    userMapper.addUser(user);
//    //}
//    @RequestMapping(value={"/addUser"}, method=RequestMethod.POST)
//    public void addUser(String openid){
//        userMapper.addUser(openid);
//    }
//
//    @RequestMapping(value={"/updateUser"}, method=RequestMethod.POST)
//    public void updateUser(User user){
//        userMapper.updateUser(user);
//    }
//
//    @RequestMapping(value={"/deleteUser"}, method=RequestMethod.POST)
//    public void deleteUser(String openid){
//        userMapper.deleteUser(openid);
//    }
    

    //用户打开微信小程序
    @RequestMapping(value={"/first"}, method=RequestMethod.POST)
    public String first(@RequestParam String code){
        String curl = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";
        String appid = "wxe7fdf205417d46a7";
        String appsecret = "d35742db586a7bbc5dd11a6862e506b5";
        String url = String.format(curl,appid, appsecret, code);
        String sb = get(url);
        int passenger = -1;

        Pattern p1=Pattern.compile("\"(.*?)\"");
        Matcher m = p1.matcher(sb);

        ArrayList<String> list = new ArrayList<String>();
        while (m.find()) {
            list.add(m.group().trim().replace("\"","")+" ");
        }
        //System.out.println(list);
        sb = list.get(3);
        User user = userMapper.selectUserById(sb);
        if(user == null) {
            User user1 = new User(sb,1);
            userMapper.addUser(user1);
        }
        else
            passenger = user.getPassenger();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("openid", sb);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            jsonParam.put("passenger",passenger );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(jsonParam);
        String obstr = new Gson().toJson(jsonParam);
        return obstr;
    }

    //乘客
    @RequestMapping(value={"/getd"}, method=RequestMethod.POST)
    public String passenger(@RequestParam String openid,
                                 @RequestParam double longitude,
                                 @RequestParam double latitude,
                                 @RequestParam String aim){
        Data data = new Data(openid,longitude,latitude);
        userMapper.adddata(data);
        //System.out.println(openid);
        Aim aim1 = userMapper.selectAim(aim);
        int road = aim1.getXianlu();
        double jingd = aim1.getJing();
        int dir = 0;
        if(Double.doubleToLongBits(jingd) > Double.doubleToLongBits(longitude))
            dir = 1;
        else dir = -1;
        Map map = new Map(openid, longitude, latitude, dir, road);
        Map map1 = userMapper.selectMap(openid);
        System.out.println(map1);
        if(map1 == null)
            userMapper.addMap(map);
        else
            userMapper.updateMap(map);
        //System.out.println(road);

        List<Mapd> list1;
        double jing = longitude;
        double wei = latitude;
        if(dir == 1) {
            list1 = userMapper.selectMapd1(map1.getWei(), road);
        }
        else {
            list1 = userMapper.selectMapd2(map1.getWei(), road);
        }
        if(list1 == null){
            list1 = userMapper.selectMapd3(-dir,road);
            if(dir == 1){
                jing = 112.9166811;
                wei = 27.9004213;
            }
            else{
                if(road == 1)
                {
                    jing = 112.9065846;
                    wei = 27.9146352;
                }
                else if(road == 2)
                {
                    jing = 112.9127743;
                    wei = 27.9133777;
                }
            }
        }
        String obstr1 = new Gson().toJson(list1);
        JsonParser parser1 = new JsonParser();
        JsonArray jsonArray1 = parser1.parse(obstr1).getAsJsonArray();
        Gson gson2 = new Gson();
        ArrayList<Mapd> userBeanList1 = new ArrayList<>();
        for (JsonElement user : jsonArray1) {
            Mapd userBean = gson2.fromJson(user, Mapd.class);
            userBeanList1.add(userBean);
        }
        double juli = 1000000;
        String dopenid = null;
        for(Mapd l : userBeanList1){
            double s = (jing-l.getJing())*(jing-l.getJing())+(wei-l.getWei())*(wei-l.getWei());
            if(juli>s){
                juli = s;
                dopenid = l.getOpenid();
            }
        }
        Mapd mapd1 = userMapper.selectMapd(dopenid);
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("jing", map1.getJing());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            jsonParam.put("wei",map1.getWei() );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String obstr = new Gson().toJson(jsonParam);
        return obstr;
    }

    //司机
    @RequestMapping(value={"/driver"}, method=RequestMethod.POST)
    public void driver(@RequestParam String openid,
                       @RequestParam double longitude,
                       @RequestParam double latitude){
        Data data = new Data(openid,longitude,latitude);
        userMapper.adddriver(data);
        Mapd mapd = new Mapd(openid,longitude,latitude,0,0,0,0);
        System.out.println(openid);
        Mapd mapd1 = userMapper.selectMapd(openid);
        if(mapd1 == null)
            userMapper.addMapd(mapd);
        else
            userMapper.updateMapd(mapd);
        //List<Driver> list = userMapper.selectDriver(openid);
        //String obstr = new Gson().toJson(list);
        Driver driver = userMapper.selectDriver(openid);
        String obstr = new Gson().toJson(driver);
        Gson gson1 = new Gson(); //新建GSON
        Driver result1 = gson1.fromJson(obstr, Driver.class); //GSON与我的工具类绑定
        double jing = result1.getJing();
        double wei = result1.getWei();
        double juli = 100000;
        int no=-1;
        int road = 0;

        //从路网一中查找最近的点
        List<Luwang> list1 = userMapper.selectLuwang1();
        String obstr1 = new Gson().toJson(list1);
        JsonParser parser1 = new JsonParser();
        JsonArray jsonArray1 = parser1.parse(obstr1).getAsJsonArray();
        Gson gson2 = new Gson();
        ArrayList<Luwang> userBeanList1 = new ArrayList<>();
        for (JsonElement user : jsonArray1) {
            Luwang userBean = gson2.fromJson(user, Luwang.class);
            userBeanList1.add(userBean);
        }
        for(Luwang l : userBeanList1){
            double s = ((jing-l.getJing())*(jing-l.getJing()))+((wei-l.getWei())*(wei-l.getWei()));
            if(juli>s){
                no = l.getNo();
                juli = s;
                road = 1;
            }
        }
        System.out.println(juli);

        //从路网二中查找最近的点
        List<Luwang> list2 = userMapper.selectLuwang2();
        String obstr2 = new Gson().toJson(list2);
        JsonParser parser2 = new JsonParser();
        JsonArray jsonArray2 = parser2.parse(obstr2).getAsJsonArray();
        Gson gson3 = new Gson();
        ArrayList<Luwang> userBeanList2 = new ArrayList<>();
        for (JsonElement user : jsonArray2) {
            Luwang userBean = gson3.fromJson(user, Luwang.class);
            userBeanList2.add(userBean);
        }
        for(Luwang l : userBeanList2){
            double s = ((jing-l.getJing())*(jing-l.getJing()))+((wei-l.getWei())*(wei-l.getWei()));
            if(juli>(s)){
                no = l.getNo();
                juli = s;
                road = 2;
            }
        }

        //更新mapd信息
        if(mapd1.getNow()==0)
            userMapper.updateMapdlast(openid, no, road);
        else if(mapd1.getNow() != no|| mapd1.getRoad() != road){
            int dir = 0;
            if(no > mapd1.getNow())
                dir = 1;
            else
                dir = -1;
            userMapper.updateMapdnow(openid, mapd1.getNow(), no, road, dir);
        }

    }

    //更改用户身份
    @RequestMapping(value={"/change"}, method=RequestMethod.POST)
    public int change(@RequestParam String openid){
        User user = userMapper.selectUserById(openid);
        System.out.println(openid);
        int passenger = user.getPassenger();
        if(passenger == 1) {
            userMapper.changeUser(openid, 0);
            return 0;
        }
        else {
            userMapper.changeUser(openid, 1);
            return 1;
        }
    }

    public static String get(String url) {
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (Exception e) {

        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }

}
