package com.example.demo.controller;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.TodoDTO;
import com.example.demo.model.TodoEntity;
import com.example.demo.service.TodoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("todo")

public class TodoController {


//	public ResponseEntity<?> testControllerResponseEntity() {
//		List<String> list = new ArrayList<>();
//		list.add("Hello World! I'm ResponseEntity. And you got 400!");
//		ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
//		
//		//http status를 400으로 설정
//		return ResponseEntity.badRequest().body(response);
//	}
	
	@Autowired
	private TodoService service;
	
	
	@GetMapping("/test") // case sensitive
	
	public  ResponseEntity<?> testTodo() {
		
		// 내가 쓴 코드 
//		List<String> list = new ArrayList<>();
//		list.add("Hello World! I'm TodoEntity");
//		ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
//		
//		return ResponseEntity.ok().body(response);
		
		// 책에 나온 코드
		String str = service.testService(); // using test service
		List<String> list = new ArrayList<>();
		list.add(str);
		
		ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
		
		return ResponseEntity.ok().body(response);
	}
	
	@PostMapping
	public ResponseEntity<?> createTodo(@RequestBody TodoDTO dto) {
		try {
			String temporaryUserId = "temporary-user"; // temporary user id.
			
			// (1) Convert TodoDTO to TodoEntity
			TodoEntity entity = TodoDTO.toEntity(dto);
			
			// (2) Initialize id to null. can't have an id when initialize
			entity.setId(null);
			
			// 임시 user id를 설정해 줌. 이 부분은 4장 인증과 인가에서 수정할 예정. 지금은 인증/인가 기능이 없으므로 한 사용자 (temporary user)만 로그인 없이 사용할 수 있는 애플리케이션인 셈. 
			entity.setUserId(temporaryUserId);
			
			// Create Todo entity using Service.
			List<TodoEntity> entities = service.create(entity);
			
			// Convert the returned entity list to a TodoDTO list using java stream.
			List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
			
			
			// 변환된 TodoDTO 리스트를 이용해 ResponseDTO를 initialize.
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
			
			// return ResponseDTO.
			return ResponseEntity.ok().body(response);
			
		} catch (Exception e) {
			// return an error instead of a DTO in case of an exception.
			String error = e.getMessage();
			
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
			
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@GetMapping
	public ResponseEntity<?> retrieveTodoList() {
		String temporaryUserId = "temporary-user"; // temporary user Id.
		
		// (1) 서비스 메서드의 retrieve() 메서드를 사용해 Todo list를 가져온다. 
		List<TodoEntity> entities = service.retrieve(temporaryUserId);
		
		List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
		
		ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
		
		return ResponseEntity.ok().body(response);
	}
	
	@PutMapping
	public ResponseEntity<?> updateTodo(@RequestBody TodoDTO dto) {
		String temporaryUserId = "temporary-user";
		
		TodoEntity entity = TodoDTO.toEntity(dto);
		
		entity.setUserId(temporaryUserId);
		
		List<TodoEntity> entities = service.update(entity);
		
		List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
		
		ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
		
		return ResponseEntity.ok().body(response);
	}
	
	@DeleteMapping
	public ResponseEntity<?> deleteTodo(@RequestBody TodoDTO dto) {
		try {
			
			String temporaryUserId = "temporary-user";
			
			// (1) Convert to TodoEntity
			TodoEntity entity = TodoDTO.toEntity(dto);
			
			entity.setUserId(temporaryUserId);
			
			List<TodoEntity> entities = service.delete(entity);
			
			List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
			
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
			
			return ResponseEntity.ok().body(response);
					
		} catch(Exception e) {
			
			String error = e.getMessage();
			
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
			
			return ResponseEntity.badRequest().body(response);
		}
	}
}
