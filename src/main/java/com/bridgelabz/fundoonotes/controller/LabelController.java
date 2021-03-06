package com.bridgelabz.fundoonotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.LabelDto;
import com.bridgelabz.fundoonotes.model.Label;
import com.bridgelabz.fundoonotes.model.Note;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.ILabelServices;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin("*")
@RequestMapping("labels")
public class LabelController {

	@Autowired
	ILabelServices labelServices;

	@PostMapping("/create")
	@ApiOperation(value = "Api to create label")
	public ResponseEntity<Response> createLabel(@RequestHeader String token, @RequestBody LabelDto labelDTO) {
		Label labelservice = labelServices.createLabel(token, labelDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("label created ", labelservice));
	}
//	
//	@PostMapping("/create")
//	@ApiOperation(value = "Api to create label", response = Response.class)
//	public ResponseEntity<Response> createLabel(@RequestHeader("token") String token, @RequestBody LabelDto labelDTO) {
//		labelServices.createLabel(token, labelDTO);
//		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("Label created", 201, labelDTO));
//	}
	

	@PostMapping("/create/{noteId}")
	@ApiOperation(value = "Api to create a lebel and add a note")
	public ResponseEntity<Response> createandMapLabel(@RequestHeader("token") String token,
			@RequestBody LabelDto labelDTO, @PathVariable("noteId") long noteId) {
		labelServices.createLabelAndMap(token, noteId, labelDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("label created and mapped", 201, labelDTO));
	}
//	@PostMapping("/create/{noteId}")
//	@ApiOperation(value = "Api to create a lebel and add a note", response = Response.class)
//	public ResponseEntity<Response> createandMapLabel(@RequestHeader("token") String token,
//			@RequestBody LabelDto labelDTO, @PathVariable("noteId") long noteId) {
//		labelServices.createLabelAndMap(token, noteId, labelDTO);
//		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("Label created and mapped", 201, labelDTO));
//	}
	

	@PostMapping("/addLabel")
	@ApiOperation(value = "Api to add note to existing label")
	public ResponseEntity<Response> addLabelsToNote(@RequestHeader("token") String token,
			@RequestParam("noteId") long noteId, @RequestParam("labelId") long labelId) {
		labelServices.addNoteToLabel(token, noteId, labelId);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("note added to the label", 200));
	}
//	@PutMapping("/map")
//	@ApiOperation(value = "Api to add note to existing label", response = Response.class)
//	public ResponseEntity<Response> addLabelsToNote(@RequestHeader("token") String token,
//			@RequestParam("noteId") long noteId, @RequestParam("labelId") long labelId) {
//		labelServices.addNoteToLabel(token, noteId, labelId);
//		return ResponseEntity.status(HttpStatus.OK).body(new Response("Note added to the label", 200));
//	}

	@PostMapping("/remove")
	@ApiOperation(value = "Api to remove note from label")
	public ResponseEntity<Response> removeLabelsNote(@RequestHeader String token,
			@RequestParam long noteId, @RequestParam long labelId) {
		labelServices.removeNoteFromLabel(token, noteId, labelId);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("note removed from the label", 200));
	}
//	@PatchMapping("/remove")
//	@ApiOperation(value = "Api to remove note from label", response = Response.class)
//	public ResponseEntity<Response> removeLabelsToNote(@RequestHeader("token") String token,
//			@RequestParam("noteId") long noteId, @RequestParam("labelId") long labelId) {
//		labelServices.removeNoteFromLabel(token, noteId, labelId);
//		return ResponseEntity.status(HttpStatus.OK).body(new Response("Note removed from the label", 200));
//	}

	@PutMapping("/update")
	@ApiOperation(value = "Api to edit the name of label")
	public ResponseEntity<Response> editLabelName(@RequestHeader String token, @RequestBody LabelDto labelDTO,
			@RequestParam long labelId) {
		if (labelServices.updateLabelName(token, labelDTO, labelId)) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("label name updated", 200));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new Response("Opps...new Label name can't be same!", 400));

	}
	
//	@PutMapping("{labelId}/edit")
//	@ApiOperation(value = "Api to edit the name of label", response = Response.class)
//	public ResponseEntity<Response> editLabelName(@RequestHeader("token") String token, @RequestParam String labelName,
//			@PathVariable("labelId") long labelId) {
//		if (labelService.isLabelEdited(token, labelName, labelId)) {
//			return ResponseEntity.status(HttpStatus.OK).body(new Response("Label name changed", 200));
//		}
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//				.body(new Response("Opps...Error renaming label", Util.BAD_REQUEST_RESPONSE_CODE));
//	}
//	
	

//	 URL -> http://localhost:8080/label/5/delete
//	@DeleteMapping("/delete")
//	@ApiOperation(value = "Api to delete a particular label")
//	public ResponseEntity<Response> deleteLabel(@RequestHeader String token,
//			@PathVariable long labelId) {
//		if (labelServices.deleteLabel(token, labelId)) {
//			return ResponseEntity.status(HttpStatus.OK).body(new Response("label deleted sucessfully", 200));
//		}
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Opps...Error deleting label", 400));
//	}
	
//	@DeleteMapping("{labelId}/delete")
//	@ApiOperation(value = "Api to delete a particular label", response = Response.class)
//	public ResponseEntity<Response> deleteLabel(@RequestHeader String token,@RequestParam long labelId) {
//		if (labelServices.deleteLabel(token, labelId)) {
//			System.out.println("hitting delete label controller");
//			return ResponseEntity.status(HttpStatus.OK).body(new Response("Label deleted sucessfully!", 200));
//		}
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//				.body(new Response("Opps...Error deleting label", 400));
//	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<Response> deleteLabel(@RequestParam long labelId , @RequestHeader String token)
	{

		labelServices.idDeletedLabel(token , labelId);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("Label deleted" ));
	}
	
	
	

	@GetMapping("/getLabels")
	@ApiOperation(value = "Api to get all labels")
	public ResponseEntity<Response> getAllLabels(@RequestHeader String token) {
		List<Label> foundLabelList = labelServices.listOfLabels(token);
		if (!foundLabelList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("found labels", 200, foundLabelList));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Opps...No labels found", 404));
	}
	
	
//	@GetMapping("/fetch")
//	@ApiOperation(value = "Api to get all labels", response = Response.class)
//	public ResponseEntity<Response> getAllLabels(@RequestHeader String token) {
//		List<Label> foundLabelList = labelServices.listOfLabels(token);
//		return ResponseEntity.status(HttpStatus.OK)
//				.body(new Response("found labels", 200, foundLabelList));
//
//	}

	
	

	@GetMapping("/getNotesByLabel/{labelId}")
	@ApiOperation(value = "Api to fetch all notes of a particular label")
	public ResponseEntity<Response> getAllNotesOfLabel(@RequestHeader String token,
			@PathVariable long labelId) {
		List<Note> foundNotesOfLabelList = labelServices.listOfNotesOfLabel(token, labelId);
		if (!foundNotesOfLabelList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("found notes", 200, foundNotesOfLabelList));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Opps...No Notes founds", 404));

	}
	
	
//	@GetMapping("/fetch/{labelId}")
//	@ApiOperation(value = "Api to fetch all notes of a particular label", response = Response.class)
//	public ResponseEntity<Response> getAllNotesOfLabel(@RequestHeader("token") String token,
//			@PathVariable("labelId") long labelId) {
//		List<Note> foundNotesOfLabelList = labelServices.listOfNotesOfLabel(token, labelId);
//		if (!foundNotesOfLabelList.isEmpty()) {
//			return ResponseEntity.status(HttpStatus.OK).body(new Response("found notes", 200, foundNotesOfLabelList));
//		}
//		return ResponseEntity.status(HttpStatus.NOT_FOUND)
//				.body(new Response("Opps...No Notes founds", 404));
//	}
	
	
	
	
	

}
