package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.TestRequestBodyDTO;

import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("test") //resource

public class TestController {

//	@GetMapping
//	public String testController() {
//		return "hello world!";
//	}
	
//	@GetMapping("/{id}")
//	public String testControllerWithPathVariables(@PathVariable(required=false) int id) {
//		return "Hello World! ID " + id;
//	}
	
	@GetMapping("/testRequestBody")// case sensitive
	public String testControllerRequestBody(@RequestBody TestRequestBodyDTO testRequestBodyDTO) {
		return "Hello World! ID" + testRequestBodyDTO.getId() + " Your message is: " + testRequestBodyDTO.getMessage();
	}
	
	@GetMapping("/testResponseBody")// case sensitive
	public ResponseDTO<String> testControllerResponseBody() {
		List<String> list = new ArrayList<>();
		list.add("Hello World! I'm ResponseDTO");
		ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
		
		return response;
	}
	
	@GetMapping("/testResponseEntity") // case sensitive
	public ResponseEntity<?> testControllerResponseEntity() {
		List<String> list = new ArrayList<>();
		list.add("Hello World! I'm ResponseEntity. And you got 400!");
		ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
		
		//http status를 400으로 설정
		return ResponseEntity.badRequest().body(response);
	}
	
}
