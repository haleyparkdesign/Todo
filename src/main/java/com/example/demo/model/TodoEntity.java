package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.GenericGenerator;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity // 자바 클래스를 엔티티로 지정하려면 @Entity를 추가. 엔티티에 이름을 부여하고 싶다면 @Entity("TodoEntity")처럼 매개변수(arg)를 넣을 수 있음 
@Table(name="Todo") // table 이름을 지정. 이 엔티티는 Database의 Todo 테이블에 매핑된다는 뜻. 만약 @Table을 추가하지 않거나 추가해도 name을 명시하지 않는다면 @Entity의 이름을 테이블 이름으로 간

public class TodoEntity {
	@Id //기본 키가 될 필드에 지정. 우리의 경우 id가 기본 키이므로 id 필드 위에 @Id를 추가해야 함. 
	@GeneratedValue(generator = "system-uuid") // id를 자동으로 생성하겠다는 뜻. generator: 어떤 방식으로 ID를 생성할지 지정. 
	@GenericGenerator(name="system-uuid", strategy="uuid")
	
	private String id; // this object's id
	private String userId; // the id of the user who created this object
	private String title; // Todo title (e.g. "workout")
	private boolean done;

}
