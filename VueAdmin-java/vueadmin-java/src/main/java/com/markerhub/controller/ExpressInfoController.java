package com.markerhub.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author pc
 * @since 2023-04-11
 */
@RestController
@RequestMapping("/sys/express")
public class ExpressInfoController extends BaseController {

    @PostMapping("/upload")
    public Map<String,Object> fileupload(MultipartFile file, HttpServletRequest req) {
        Map<String, Object> result = new HashMap<>();

        // 放在本地项目 目录
        String filePath="F:\\本科毕业设计\\SpringBoot\\VueAdmin-java\\vueadmin-java\\src\\main\\resources\\static\\img";
        File folder = new File(filePath);

        if (!folder.exists()) {
            folder.mkdirs();
        }
        // 生成新的文件名
        String oldName = file.getOriginalFilename();
        String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."));
        // System.out.println(oldName);
        //System.out.println(newName);
        try {
            // 保存文件到指定路径
            file.transferTo(new File(folder, newName));
            String url = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort()+"/img/"  + newName;

            System.out.println(url);
            result.put("status", "OK");
            result.put("name", oldName);
            result.put("url", url);

        } catch (IOException e) {
            result.put("status", "ERROR");
            result.put("msg", e.getMessage());
        }
        return result;
    }

}
