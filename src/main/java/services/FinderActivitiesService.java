
package services;

import java.util.ArrayList;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import domain.Activity;
import domain.FinderActivities;

@Transactional
@Service
public class FinderActivitiesService {

	public FinderActivities create() {
		FinderActivities finder = new FinderActivities();
		finder.setActivities(new ArrayList<Activity>());
		finder.setKeyWord("");
		finder.setMinDate(new Date());

		return finder;
	}
}
