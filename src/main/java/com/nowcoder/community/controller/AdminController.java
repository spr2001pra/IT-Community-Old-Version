package com.nowcoder.community.controller;

import com.nowcoder.community.entity.Page;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.UserService;
import com.nowcoder.community.util.CommunityUtil;
import com.nowcoder.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AdminController {

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/admin/usermanage", method = RequestMethod.GET)
    public String getAdminUserPage(Model model, Page page){

        User user = hostHolder.getUser();

        page.setPath("/admin/usermanage");
        page.setRows(userService.getFindUserRows());

        List<User> userListPage = userService.getUserByAdmin(user.getId(), page.getOffset(), page.getLimit());
        List<Map<String, Object>> everyUserDetail = new ArrayList<>();
        for (User everyUser : userListPage){
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("user", everyUser);
            everyUserDetail.add(userMap);
        }
        model.addAttribute("userList", everyUserDetail);
        return "/site/admin/user-controller";
    }

    @RequestMapping(path = "admin/usermanage/type", method = RequestMethod.POST)
    @ResponseBody
    public String updateUserType(int userid, int type){
        if(type == 0){
            userService.updateUserType(userid, 1);
            return CommunityUtil.getJSONString(0, "授权成功！");
        }else {
            userService.updateUserType(userid, 0);
            return CommunityUtil.getJSONString(0, "取消授权成功！");
        }
    }

    @RequestMapping(path = "admin/usermanage/status", method = RequestMethod.POST)
    @ResponseBody
    public String updateUserStatus(int userid, int status){
        if(status == 0){
            userService.updateUserStatus(userid, 1);
            return CommunityUtil.getJSONString(0, "解除拉黑成功！");
        }else {
            userService.updateUserStatus(userid, 0);
            return CommunityUtil.getJSONString(0, "拉黑成功！");
        }
    }

}
