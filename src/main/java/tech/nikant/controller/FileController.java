package tech.nikant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import tech.nikant.service.FileService;

@RestController
public class FileController {
	
	@Autowired
	FileService service;

	//This controller will save Data to DB.
	@PostMapping("/saveData")
	public ResponseEntity<String> saveDataToDB(String fileName){
		service.saveData(fileName);
		return new ResponseEntity<String>("Success", HttpStatus.OK);
	}
	
	
	//This controller will fetch data from DB
	@GetMapping("/fetchData")
	public ResponseEntity<Object> fetchDataFromDB(String fileName){
		Object response = service.fetchData(fileName);
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}
	
}
