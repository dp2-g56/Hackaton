package services;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Activity;
import repositories.ActivityRepository;

@Service
@Transactional
public class ActivityService {

	@Autowired
	private ActivityRepository activityRepository;

	// CRUDS
	public List<Activity> findAll() {
		return this.activityRepository.findAll();
	}

	public Activity findOne(Integer id) {
		return this.activityRepository.findOne(id);
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

}
