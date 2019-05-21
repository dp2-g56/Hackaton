package services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Activity;
import domain.Prisoner;
import domain.SocialWorker;
import repositories.ActivityRepository;

@Service
@Transactional
public class ActivityService {

	@Autowired
	private ActivityRepository activityRepository;

	@Autowired
	private SocialWorkerService socialWorkerService;

	// CRUDS
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

	public Activity save(Activity activity) {
		return this.activityRepository.save(activity);
	}

	// Activities

	public Map<Activity, Integer> getNumberOfApprobedRequestPerActivity(List<Activity> activities) {
		Map<Activity, Integer> res = new HashMap<>();
		for (Activity activity : activities)
			res.put(activity, this.activityRepository.getNumberOfApprobedRequest(activity));
		return res;
	}

	public void securityActivityForRequests(Activity activity) {
		Assert.isTrue(activity.getMaxAssistant() != this.activityRepository.getNumberOfApprobedRequest(activity));
	}

	public List<Activity> getFutureActivites() {
		Date date = new Date();

		return this.activityRepository.getPostActivities(date);

	}

	public List<Activity> getFinalActivitiesSocialWorker(SocialWorker sw) {
		return this.activityRepository.getFinalActivitiesSocialWorker(sw);
	}
}
