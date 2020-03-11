package com.bridgelabz.fundoonotes.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.model.Label;

@Repository
public interface ILabelRepository extends JpaRepository<Label, Long> {

	public Label findOneBylabelName(String name);

	@Query(value = "select * from nativeQuery = truenativeQuery = true where user_id = ?", nativeQuery = true)
	List<Label> findAll(Long id);

	@Modifying
	@Transactional
	@Query(value = "update label_details set label_name=? where label_id=?", nativeQuery = true)
	public void updateLabelName(String name, Long id);

	@Query(value = "select *  label_details where label_name=?", nativeQuery = true)
	public List<Label> checkLabelInDatabase(String labelName);

	@Query(value = "select * from label_details", nativeQuery = true)
	public List<Label> getAllLabels();

}
