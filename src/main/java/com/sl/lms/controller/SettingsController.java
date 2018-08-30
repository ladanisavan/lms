
package com.sl.lms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.lms.service.MasterDataService;

@Controller
@RequestMapping("/admin")
public class SettingsController {

	Logger logger = LoggerFactory.getLogger(SettingsController.class);
	
	private final MasterDataService mdService;
	public SettingsController(MasterDataService mdService) {
		this.mdService = mdService;
	}
	
	@GetMapping("/settings")
	public String viewSettingsHome() {
		return "/admin/settings";
	}
}
