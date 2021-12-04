package com.example.demo.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data


public class TodoEntity {
	private String id; // this object's id
	private String userId; // the id of the user who created this object
	private String title; // Todo title (e.g. "workout")
	private boolean done;

}
