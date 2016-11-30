package controllers;

import beans.LoanDataVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import services.Services;

import java.util.List;

/**
 * Created by admin on 2016/11/21.
 */
@RestController
@RequestMapping(value = "/jsonCtrl")
public class JsonCtrl {

	@Autowired
	private Services services;

	@RequestMapping(value = "/json")
	public List<LoanDataVo> toJson() {
		return services.queryLoan();
	}
}
