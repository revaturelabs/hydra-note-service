package com.revature.caliber.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.caliber.model.NoteType;
import com.revature.caliber.model.SimpleNote;

@Repository
public interface NoteRepository extends JpaRepository<SimpleNote, Integer> {
	void deleteByTraineeId(Integer traineeId);
	List<SimpleNote> findAllByTraineeId(Integer traineeId);
	List<SimpleNote> findAllByBatchId(Integer batchId);
	
	List<SimpleNote> findAllByBatchIdAndType(Integer batchId, NoteType type);
	List<SimpleNote> findAllByTraineeIdAndType(Integer traineeId, NoteType type);
	
	List<SimpleNote> findAllByBatchIdAndWeekAndType(Integer batchId, Short week, NoteType type);
	List<SimpleNote> findAllByTraineeIdAndWeekAndType(Integer traineeId, Short week, NoteType type);
	
	List<SimpleNote> findAllByBatchIdAndWeekAndQcFeedbackAndType(Integer batchId, Short week, boolean qcFeedback, NoteType type);
	SimpleNote findOneByBatchIdAndWeekAndQcFeedbackAndType(Integer batchId, Short week, boolean qcFeedback, NoteType type);
	List<SimpleNote> findAllByTraineeIdAndWeekAndQcFeedbackAndType(Integer traineeId, Short week, boolean qcFeedback, NoteType type);
	SimpleNote findOneByTraineeIdAndWeekAndQcFeedbackAndType(Integer traineeId, Short week, boolean qcFeedback, NoteType type);
	
	List<SimpleNote> findAllByTraineeIdAndTypeOrderByWeekAsc(Integer traineeId, NoteType type);
	List<SimpleNote> findAllByTraineeIdAndQcFeedbackAndTypeOrderByWeekAsc(Integer traineeId, boolean qcFeedback, NoteType type);
	List<SimpleNote> findAllByBatchIdAndQcFeedbackAndTypeOrderByWeekAsc(Integer batchId, boolean qcFeedback, NoteType type);
	List<SimpleNote> findAllByBatchIdAndWeekAndQcFeedbackAndTypeOrderByWeekAsc(Integer batchId, Short week, boolean qcFeedback, NoteType type);
}
