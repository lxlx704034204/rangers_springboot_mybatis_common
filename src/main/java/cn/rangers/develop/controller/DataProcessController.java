package cn.rangers.develop.controller;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.rangers.develop.common.ResponseResult;
import cn.rangers.develop.utils.ExcelOperaterUtils;

@RestController
@RequestMapping("/data")
public class DataProcessController {

	

    @RequestMapping(value="/upload",method=RequestMethod.POST)
    public ResponseResult uploadFile(@RequestParam("filePath")MultipartFile file,HttpServletRequest request,HttpServletResponse response){
    	ResponseResult result = ResponseResult.getInstance(false);
    	
    	try {
    		String fileName = file.getName();
    		InputStream inputStream = file.getInputStream();
    		ExcelOperaterUtils excelOperaterUtils = new ExcelOperaterUtils();
    		LinkedHashMap<String, String> params = new LinkedHashMap<>();
        	params.put("姓名", "name");
        	params.put("密码", "password");
    		Map<String, Object> readFromExcel = excelOperaterUtils.readFromExcel(inputStream, fileName, params, null);
    		if(readFromExcel == null){
    			result.setMsg("文件中的内容为空！");
    		}else {
    			result.setData(readFromExcel);
    			result.setSuccess(true);
    			result.setMsg("操作成功！");
    		}
			
    		
		} catch (Exception e) {
			result.convertException(e);
		}
		return result;
    }
}
