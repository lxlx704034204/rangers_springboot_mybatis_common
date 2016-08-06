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

import com.google.common.collect.Maps;

import cn.rangers.develop.common.ResponseResult;
import cn.rangers.develop.utils.ExcelOperaterUtils;

@RestController
@RequestMapping("/data")
public class DataProcessController {

	

	/**
	 * 单文件上传
	 *
	 * @param file
	 * @param request
	 * @param response
	 * @return
	 * 
	 * @author Administrator
	 * @date 2016年8月6日 下午8:42:12
	 */
//    @RequestMapping(value="/upload",method=RequestMethod.POST)
//    public ResponseResult uploadFile(@RequestParam("filePath")MultipartFile file,HttpServletRequest request,HttpServletResponse response){
//    	ResponseResult result = ResponseResult.getInstance(false);
//    	
//    	try {
//    		if(file.isEmpty() || file == null) {
//    			result.setMsg("没有获取到上传的文件！");
//    			return result;
//    		}
//    		
//    		if(file.getSize() >= 5400000L) {
//    			result.setMsg("上传文件的大小超过了限制，建议5M以内！");
//    			return result;
//    		}
//    		
//    		String fileName = file.getOriginalFilename();//获取原始文件的文件名
//    		if(!fileName.endsWith(".xls") && !fileName.endsWith(".xlsx")) {
//    			result.setMsg("上传文件的格式不符合要求，请上传正规的excel文件！");
//    			return result;
//    		}
//    		InputStream inputStream = file.getInputStream();
//    		ExcelOperaterUtils excelOperaterUtils = new ExcelOperaterUtils();
//    		LinkedHashMap<String, String> params = new LinkedHashMap<>();
//        	params.put("姓名", "name");
//        	params.put("密码", "password");
//    		Map<String, Object> readFromExcel = excelOperaterUtils.readFromExcel(inputStream, fileName, params, null);
//    		if(readFromExcel == null){
//    			result.setMsg("文件中的内容为空！");
//    		}else {
//    			result.setData(readFromExcel);
//    			result.setSuccess(true);
//    			result.setMsg("操作成功！");
//    		}
//			
//    		
//		} catch (Exception e) {
//			result.convertException(e);
//		}
//		return result;
//    }

	/**
	 * 多文件上传
	 * 
	 * @param file
	 * @param request
	 * @param response
	 * @return
	 * 
	 * @author Administrator
	 * @date 2016年8月6日 下午8:42:47
	 */
    @RequestMapping(value="/upload",method=RequestMethod.POST)
    public ResponseResult uploadFile(@RequestParam("filePath")MultipartFile[] files,HttpServletRequest request,HttpServletResponse response){
    	ResponseResult result = ResponseResult.getInstance(false);
    	
    	try {
    		if(files == null || files.length <= 0) {
    			result.setMsg("没有获取到上传的文件！");
    			return result;
    		}
    		Map<String, Object> fileContent = Maps.newHashMap();
    		for(MultipartFile file : files) {
    			if(file.isEmpty() || file == null) {
        			result.setMsg("没有获取到上传的文件！");
        			return result;
        		}
        		
        		if(file.getSize() >= 5400000L) {
        			result.setMsg("上传文件的大小超过了限制，建议5M以内！");
        			return result;
        		}
        		
        		String fileName = file.getOriginalFilename();//获取原始文件的文件名
        		if(!fileName.endsWith(".xls") && !fileName.endsWith(".xlsx")) {
        			result.setMsg("上传文件的格式不符合要求，请上传正规的excel文件！");
        			return result;
        		}
        		InputStream inputStream = file.getInputStream();
        		ExcelOperaterUtils excelOperaterUtils = new ExcelOperaterUtils();
        		LinkedHashMap<String, String> params = new LinkedHashMap<>();
        		params.put("姓名", "name");
        		params.put("密码", "password");
        		Map<String, Object> readFromExcel = excelOperaterUtils.readFromExcel(inputStream, fileName, params, null);
        		if(readFromExcel == null){
        			result.setMsg("文件中的内容为空！");
        		}else {
        			fileContent.put(fileName, readFromExcel);
        		}
    		}
    		result.setData(fileContent);
			result.setSuccess(true);
			result.setMsg("操作成功！");
    		
    	} catch (Exception e) {
    		result.convertException(e);
    	}
    	return result;
    }
}
