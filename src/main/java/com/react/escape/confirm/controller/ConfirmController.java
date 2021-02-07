package com.react.escape.confirm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.react.escape.confirm.service.ConfirmService;

@Controller
public class ConfirmController {

	@Autowired
	private ConfirmService confirmService;
	
	
}
