package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Activity;
import domain.ActivityStatus;
import domain.Prisoner;
import domain.Request;
import repositories.RequestRepository;

@Service
@Transactional
public class RequestService {

	@Autowired
	private RequestRepository requestRepository;

	@Autowired
	private PrisonerService prisonerService;

	@Autowired
	private ActivityService activityService;
	// CRUS

	public Request create() {
		Request res = new Request();

		res.setActivity(null);
		res.setMotivation("");
		res.setRejectReason("");
		res.setStatus(ActivityStatus.PENDING);

		return res;
	}

	public Request findeOne(Integer id) {
		return this.requestRepository.findOne(id);
	}

	public List<Request> findAll() {
		return this.requestRepository.findAll();
	}

	public void delete(Request request) {
		this.requestRepository.delete(request);
	}

	public Request save(Request request) {
		return this.requestRepository.save(request);
	}

	// Request

	public List<Request> getLogguedPrisonerRequests() {
		Prisoner prisoner = this.prisonerService.loggedPrisoner();

		return prisoner.getRequests();
	}

	public void assignRequest(Request request, Integer activityId) {
		Activity activity = this.activityService.findOne(activityId);

		request.setActivity(activity);

		request = this.save(request);
		List<Request> requests = activity.getRequests();
		requests.add(request);
		activity.setRequests(requests);

		this.activityService.save(activity);

	}

}
