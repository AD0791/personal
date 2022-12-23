package com.alexandrodisla.personal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
@RestController
@RequestMapping("/api/v2")
public class PersonalApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonalApplication.class, args);
	}



	private final StudentRepository studentRepository;

	public PersonalApplication(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}


	@GetMapping("/greetings")
	public GreetResponse greet(){return new GreetResponse("Hello Rest spring");}

	record GreetResponse(String greet){}

	@GetMapping("/students")
	public List<Student> getStudents(){
		return studentRepository.findAll();
	}

	record NewStudentRequest(
			String name,
			String email,
			Integer age,
			String major
	){}

	@PostMapping("/student")
	public void addStudent(@RequestBody NewStudentRequest request){
		Student student = new Student();
		student.setName(request.name());
		student.setEmail(request.email());
		student.setAge(request.age());
		student.setMajor(request.major());
		studentRepository.save(student);
	}

	@DeleteMapping("/student/{studentId}")
	public void deleteStudent(
			@PathVariable("studentId")
			Integer id){
		studentRepository.deleteById(id);
	}


	@PutMapping("/student/{studentId}")
	public void updateStudent(@PathVariable("studentId") Integer id, @RequestBody NewStudentRequest request){
		Optional<Student>  studentData = studentRepository.findById(id);
		// bad practice
		Student currentStudent =  studentData.get();
		currentStudent.setName(request.name());
		currentStudent.setEmail(request.email());
		currentStudent.setAge(request.age());
		currentStudent.setMajor(request.major());
		studentRepository.save(currentStudent);

	}
}


