package services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Activity;
import domain.Prisoner;
import repositories.ActivityRepository;

@Transactional
@Service
public class ActivityService {

	@Autowired
	private ActivityRepository activityRepository;

	@Autowired
	private SocialWorkerService socialWorkerService;

	public List<Activity> findAll() {
		return this.activityRepository.findAll();
	}

	public Activity findOne(int id) {
		return this.activityRepository.findOne(id);
	}

	public List<Prisoner> getPrisonersPerActivity(Activity a) {
		this.socialWorkerService.loggedAsSocialWorker();
		List<Prisoner> res = new ArrayList<Prisoner>();
		res = this.activityRepository.getPrisonersPerActivity(a);
		return res;
	}

}
