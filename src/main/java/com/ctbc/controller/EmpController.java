package com.ctbc.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ctbc.model.dao.EmpDAO;
import com.ctbc.model.vo.DeptVO;
import com.ctbc.model.vo.EmpVO;

@Controller
@RequestMapping(value = "/EmpController")
public class EmpController {
	
	@Autowired
	private EmpDAO empDAO;
	
	@ResponseBody
	@RequestMapping(value = "/getAllEmps", method = RequestMethod.POST, 
		consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE },  // consumes的例子（方法僅處理request Content-Type 為 "application/json" 類型的請求）
		produces = { MediaType.APPLICATION_JSON_UTF8_VALUE }   // produces: 指定返回的内容类型，僅當 request-header 中的(Accept)類型中包含該指定類型才返回；
	)
	private List<EmpVO> getAllEmps(@RequestBody Map<String, Object> inputJson) {
		System.out.println("=========== testAjaxPost ===========");
		System.out.println(">>> inputJson >>> " + inputJson);
		
		List<EmpVO> empList = empDAO.getAll();
		for (EmpVO empVO : empList) {
			System.out.println(empVO);
			
			DeptVO deptVO = empVO.getDeptVOGG();
			deptVO.setEmps(null); // ※※※避免Gson.toJson發生StackOverFlowError(Gson輸出json時一方多方無限交互參照)
			
			System.out.println(">>> deptVO >>> " + deptVO);
		}
		
		return empList;
	}

}
