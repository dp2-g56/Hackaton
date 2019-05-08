
package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.CurriculumRepository;
import utilities.RandomString;
import domain.Curriculum;
import domain.EducationRecord;
import domain.MiscellaneousRecord;
import domain.PersonalRecord;
import domain.ProfessionalRecord;
import domain.SocialWorker;

@Service
@Transactional
public class CurriculumService {

	// Manged Repository

	@Autowired
	private CurriculumRepository	curriculumRepository;

	// Supporting service

	@Autowired
	private SocialWorkerService		socialWorkerService;

	@Autowired
	private PersonalRecordService	personalRecordService;


	// Simple CRUD methods

	public Curriculum create() {

		List<MiscellaneousRecord> miscellaneousRecords = new ArrayList<MiscellaneousRecord>();
		List<EducationRecord> educationRecords = new ArrayList<EducationRecord>();
		List<ProfessionalRecord> professionalRecords = new ArrayList<ProfessionalRecord>();
		PersonalRecord personalRecord = new PersonalRecord();
		personalRecord = this.personalRecordService.create();

		Curriculum curriculum = new Curriculum();
		curriculum.setTicker(this.generateTicker());
		curriculum.setMiscellaneousRecords(miscellaneousRecords);
		curriculum.setEducationRecords(educationRecords);
		curriculum.setPersonalRecord(personalRecord);
		curriculum.setProfessionalRecords(professionalRecords);

		return curriculum;

	}

	//Método auxiliar para generar el ticker-------------------------------
	public String generateTicker() {
		String res = "";
		Date date = null;
		String date1;
		String date2 = LocalDate.now().toString();
		String gen = new RandomString(6).nextString();
		List<Curriculum> lc = this.curriculumRepository.findAll();
		SimpleDateFormat df_in = new SimpleDateFormat("yyMMdd");
		SimpleDateFormat df_output = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = df_output.parse(date2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		date1 = df_in.format(date);
		res = res + date1 + "-" + gen;
		for (Curriculum c : lc)
			if (c.getTicker() == res)
				return this.generateTicker();
		return res;
	}

	public Collection<Curriculum> findAll() {
		Collection<Curriculum> result;

		result = this.curriculumRepository.findAll();

		return result;
	}

	public Curriculum findOne(Integer id) {

		Curriculum result = this.curriculumRepository.findOne(id);
		return result;

	}

	public Curriculum save(Curriculum curriculum) {
		return this.curriculumRepository.save(curriculum);

	}

	public void delete(Curriculum curriculum) {
		SocialWorker socialCurriculum = this.getSocialWorkerByCurriculum(curriculum.getId());
		socialCurriculum.setCurriculum(null);
		this.socialWorkerService.save(socialCurriculum);
		this.curriculumRepository.delete(curriculum);

	}

	public SocialWorker getSocialWorkerByCurriculum(int id) {
		return this.curriculumRepository.getSocialWorkerByCurriculum(id);
	}

}
