package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

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

	@Autowired
	private Validator validator;
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

	public Request reconstruct(Request request, Integer activityId, BindingResult binding) {
		Request res = new Request();

		res.setId(0);
		res.setStatus(ActivityStatus.PENDING);
		res.setRejectReason("");
		res.setMotivation(request.getMotivation());
		res.setActivity(this.activityService.findOne(activityId));
		res.setPrisoner(this.prisonerService.loggedPrisoner());
		res.setVersion(0);

		this.validator.validate(res, binding);

		return res;

	}

	public List<Request> getLogguedPrisonerRequests() {
		Prisoner prisoner = this.prisonerService.loggedPrisoner();

		return prisoner.getRequests();
	}

	public void assignRequest(Request request, Integer activityId) {
		Prisoner prisoner = this.prisonerService.loggedPrisoner();
		Activity activity = this.activityService.findOne(activityId);

		request = this.save(request);

		this.requestRepository.flush();

		List<Request> prisonerRequests = prisoner.getRequests();
		List<Request> requests = activity.getRequests();
		prisonerRequests.add(request);
		requests.add(request);
		activity.setRequests(requests);
		prisoner.setRequests(prisonerRequests);

		this.prisonerService.save(prisoner);
		this.activityService.save(activity);

	}

	public void deleteRequest(Request request) {
		Prisoner prisoner = this.prisonerService.loggedPrisoner();
		Activity activity = this.activityService.findOne(request.getActivity().getId());

		List<Request> prisonerRequests = prisoner.getRequests();

		Assert.isTrue(request.getPrisoner().equals(prisoner) && prisonerRequests.contains(request));
		Assert.isTrue(request.getStatus().equals(ActivityStatus.PENDING));

		List<Request> activityRequests = activity.getRequests();

		activityRequests.remove(request);
		prisonerRequests.remove(request);

		prisoner.setRequests(prisonerRequests);
		activity.setRequests(activityRequests);

		this.activityService.save(activity);
		this.prisonerService.save(prisoner);

		this.delete(request);
	}

}
