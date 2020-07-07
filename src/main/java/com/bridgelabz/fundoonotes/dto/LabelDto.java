package com.bridgelabz.fundoonotes.dto;

import com.bridgelabz.fundoonotes.model.Label;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LabelDto {
	
	@ApiModelProperty(notes = "Name of the label", name = "label name")
	private String labelName;
}
