package com.promotions.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.promotions.services.PromotionsService;

@RestController
@RequestMapping("/promotions")
public class PromotionsApiController {

	private static final Logger logger = LoggerFactory.getLogger(PromotionsApiController.class);

	@Autowired
	private PromotionsService promotionsService;

	@GetMapping("/test")
	public String test() {
		logger.info("PromotionsApiController test endpoint hit success!");
		return "PromotionsApiController test endpoint hit success!";
	}
}
