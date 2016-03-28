package com.test360.controller;

import com.test360.business.model.*;
import com.test360.business.service.MenuService;
import com.test360.business.service.OrderingService;
import com.test360.business.service.ReceivingService;
import com.test360.business.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 用户登录验证
 */
@Controller
@RequestMapping(value="/Manager")
public class ManagerController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MenuService menuService;

    @Autowired
    OrderingService orderingService;

    @Autowired
    ReceivingService receivingService;

    @Autowired
    UserService userService;

    @ResponseBody
    @RequestMapping(value = "/Managerlogin")
    public Object Managerlogin(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> parames) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        String account = (String)parames.get("account");
        String password = (String)parames.get("password");

        if(account.equals("zgj")&&password.equals("123")){
            map.put("account", account);
            map.put("success", true);
            map.put("msg", "登录成功");
            System.out.println(request.getSession().getAttribute("account"));
            System.out.println(request.getSession().getMaxInactiveInterval());
        }else{
            map.put("error", true);
            map.put("msg", "登录失败");
        }
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/islogin")
    public Object islogin(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> parames) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        String account= (String) request.getSession().getAttribute("account");
        if(account!=null && account.equals(""))   {
            map.put("account",account);
            map.put("success", true);
            map.put("msg", "登录成功");
        }else{
            map.put("error", true);
            map.put("msg", "登录失败");
        }
        return map;
    }

    /**
     * 获取全部菜谱
     * @param request
     * @param response
     * @param parames
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/menu")
    public Object menu(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> parames) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        Criteria criteria = new Criteria();

        Date date = new Date();
        date.setDate(date.getDate() - 1);
        criteria.getCondition().put("menuDate1",date);

        List<Menu> list= menuService.selectByExample(criteria);
        String menuDate="";

        List<MenuList> menuLists=new ArrayList<MenuList>();

        for(Menu menu:list){
            String str=new SimpleDateFormat("yyyy-MM-dd").format(menu.getMenuDate()).toString();
            if(menuLists.size()==0){
                MenuList menuList=new MenuList();
                menuList.setMenuDate(str);
                if ("A".equals(menu.getMenuType())) {
                    menuList.setMenuA(menu.getMenu());
                } else if ("B".equals(menu.getMenuType())) {
                    menuList.setMenuB(menu.getMenu());
                } else {
                    menuList.setMenuC(menu.getMenu());
                }
                menuLists.add(menuList);
            }else{
                int i=0;
                boolean flag=false;
                for(MenuList ml:menuLists){
                    if(str.equals(ml.getMenuDate())){
                        flag=true;
                        break;
                    }
                    i++;
                }
                    if(flag){
                        if ("A".equals(menu.getMenuType())) {
                            menuLists.get(i).setMenuA(menu.getMenu());
                        } else if ("B".equals(menu.getMenuType())) {
                            menuLists.get(i).setMenuB(menu.getMenu());
                        } else {
                            menuLists.get(i).setMenuC(menu.getMenu());
                        }
                    }else{
                        MenuList menuList=new MenuList();
                        menuList.setMenuDate(str);
                        if ("A".equals(menu.getMenuType())) {
                           menuList.setMenuA(menu.getMenu());
                        } else if ("B".equals(menu.getMenuType())) {
                            menuList.setMenuB(menu.getMenu());
                        } else {
                           menuList.setMenuC(menu.getMenu());
                        }
                        menuLists.add(menuList);
                    }
                }
            }
        map.put("menu",menuLists);
        map.put("success", true);

        return map;
    }

    /**
     * 录入菜谱
     * @param request
     * @param response
     * @param parames
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/menuLoad")
    public Object menuLoad(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> parames) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        String menuDateStr = (String)parames.get("menuDate");
        menuDateStr = menuDateStr.substring(0, menuDateStr.indexOf("T"));
        String[] ymd = menuDateStr.split("-");

        Date date = new Date(Integer.parseInt(ymd[0])-1900, Integer.parseInt(ymd[1])-1, Integer.parseInt(ymd[2])+1);

        String menuA = (String)parames.get("menuA");
        String menuB = (String)parames.get("menuB");
        String menuC = (String)parames.get("menuC");
        String aprice = (String)parames.get("Aprice");
        String bprice = (String)parames.get("Bprice");
        String cprice = (String)parames.get("Cprice");

        Menu menu=new Menu();
        menu.setMenuDate(date);
        menu.setMenuType("A");
        menu.setMenu(menuA);
        menu.setPrice(new BigDecimal(aprice));
        menuService.insert(menu);

        menu.setMenuType("B");
        menu.setMenu(menuB);
        menu.setPrice(new BigDecimal(bprice));
        menuService.insert(menu);

        menu.setMenuType("C");
        menu.setMenu(menuC);

        menu.setPrice(new BigDecimal(cprice));
        menuService.insert(menu);

        map.put("success", true);
        return map;
    }

    /**
     * 查询用户订单
     * @param request
     * @param response
     * @param parames
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/checkAll")
    public Object checkAll(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> parames) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        Criteria criteria = new Criteria();
        String menuDateStr = (String) parames.get("querytime");

        if (menuDateStr != null && !menuDateStr.equals("")) {
            menuDateStr = menuDateStr.substring(0, menuDateStr.indexOf("T"));
            String[] ymd = menuDateStr.split("-");
            Date date1 = new Date(Integer.parseInt(ymd[0]) - 1900, Integer.parseInt(ymd[1]) - 1, Integer.parseInt(ymd[2]) + 1);
            Date date2 = new Date(Integer.parseInt(ymd[0]) - 1900, Integer.parseInt(ymd[1]) - 1, Integer.parseInt(ymd[2]) + 2);

            criteria.getCondition().put("orderTime1", date1);
            criteria.getCondition().put("orderTime2", date2);
            criteria.getCondition().put("getTime1", date1);
            criteria.getCondition().put("getTime2", date2);
        }

        String userId = (String) parames.get("userid");
        if (userId != null && !userId.equals("")) {
            criteria.getCondition().put("userId", userId);
        }
        List<Ordering> orderlist = orderingService.selectByExample(criteria);
        List<Receiving> receivingList = receivingService.selectByExample(criteria);
        List<Receiving> receivingList1=new ArrayList<Receiving>();

        String datetime1;
        String datetime2;
        int userid = 0;
        boolean flag=false;
        for (int i=0;i<receivingList.size();i++) {
            userid = receivingList.get(i).getUserId();
            datetime1 = new SimpleDateFormat("yyyy-MM-dd").format(receivingList.get(i).getGetTime());

            for (int j=0;j<orderlist.size();j++) {
                datetime2 = new SimpleDateFormat("yyyy-MM-dd").format(orderlist.get(j).getOrderTime());
                if (orderlist.get(j).getUserId() == userid && datetime1.equals(datetime2)) {
                    orderlist.get(j).setIsTaken((byte)2);
                    orderingService.updateByPrimaryKey(orderlist.get(j));
                    flag=true;
                    break;
                }
            }
            if(flag){
                receivingList.get(i).setIsError((byte)0);
                receivingService.updateByPrimaryKey(receivingList.get(i));
                flag=false;
            }
        }
        for(Ordering ordering:orderlist){
            if(ordering.getIsTaken().equals((byte)1)){

                Receiving receiving=new Receiving();
                receiving.setUserId(ordering.getUserId());
                receiving.setIsError((byte) 2);
                receiving.setGetTime(ordering.getOrderTime());
                receiving.setLocation(ordering.getLocation());
                receivingList1.add(receiving);
            }
        }
        for(Receiving receiving:receivingList){
            if(receiving.getIsError().equals((byte)1)){
                receivingList1.add(receiving);
            }
        }

        List<User> userList=new ArrayList<User>();
        userList=userService.selectByExample(criteria);
        map.put("rece",receivingList1);
        map.put("receiving",receivingList);
        map.put("ordering",orderlist);
        map.put("user",userList);
        map.put("success", true);
        return map;
    }

    /**
     * 统计订单总数
     * @param request
     * @param response
     * @param parames
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/AllNumber")
    public Object AllNumber(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> parames) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        Criteria criteria = new Criteria();
        criteria.put("isTaken_greater","1");
        String today = (String)parames.get("today");
        if(today!=null&&!today.equals("")){
            String[] ymd = today.split("-");
            Date date1 = new Date(Integer.parseInt(ymd[0])-1900, Integer.parseInt(ymd[1])-1, Integer.parseInt(ymd[2]));
            Date date2 = new Date(Integer.parseInt(ymd[0])-1900, Integer.parseInt(ymd[1])-1, Integer.parseInt(ymd[2])+1);
            criteria.getCondition().put("orderTime1", date1);
            criteria.getCondition().put("orderTime2", date2);
        }

        criteria.put("location","17楼");
        criteria.put("menuType","A");
        int a17=orderingService.countByExample(criteria);
        criteria.put("menuType","B");
        int b17=orderingService.countByExample(criteria);
        criteria.put("menuType","C");
        int c17=orderingService.countByExample(criteria);
        map.put("a17",a17);
        map.put("b17",b17);
        map.put("c17",c17);

        criteria.put("location","18楼");
        criteria.put("menuType","A");
        int a18=orderingService.countByExample(criteria);
        criteria.put("menuType","B");
        int b18=orderingService.countByExample(criteria);
        criteria.put("menuType","C");
        int c18=orderingService.countByExample(criteria);
        map.put("a18",a18);
        map.put("b18",b18);
        map.put("c18",c18);

        criteria.put("location","创客空间");
        criteria.put("menuType","A");
        int a=orderingService.countByExample(criteria);
        criteria.put("menuType","B");
        int b=orderingService.countByExample(criteria);
        criteria.put("menuType","C");
        int c=orderingService.countByExample(criteria);
        map.put("a",a);
        map.put("b",b);
        map.put("c",c);

        map.put("success", true);
        return map;
    }

    /**
     * 删除菜谱
     * @param request
     * @param response
     * @param parames
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/remove")
    public Object remove(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> parames) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        Criteria criteria = new Criteria();
        String menuDate = (String)parames.get("menuDate");
        Date date=StringtoDate(menuDate);
        date.setDate(date.getDate() - 2);
        criteria.getCondition().put("menuDate1",date);
        date.setDate(date.getDate() +1);
        criteria.getCondition().put("menuDate2",date);
        menuService.deleteByExample(criteria);
        map.put("success", true);
        return map;
    }

    //字符串转换成日期
    public Date StringtoDate(String str){
        String[] ymd = str.split("-");
        Date date = new Date(Integer.parseInt(ymd[0])-1900, Integer.parseInt(ymd[1])-1, Integer.parseInt(ymd[2])+1);
        return date;
    }
}
