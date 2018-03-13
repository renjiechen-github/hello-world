package pccom.web.action.onstruction;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import pccom.web.action.BaseController;
import pccom.web.server.onstruction.OnstructionService;


@Controller
public class OnstructionController extends BaseController
{
	@Autowired
	public OnstructionService Service;

/*	*//**
	 * 工程新增
	 * @author 刘飞
	 * @param response
	 *//*
	@RequestMapping (value = "onstruction/create.do")
	public void create(HttpServletRequest request, HttpServletResponse response) {
		this.writeJsonData(Service.Save(request,response), response);
	} */
	
	
}
