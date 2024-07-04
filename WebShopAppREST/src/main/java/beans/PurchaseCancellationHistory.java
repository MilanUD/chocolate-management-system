package beans;

import java.time.LocalDate;

public class PurchaseCancellationHistory {
	
	private String id;
	private String userId;
	private Integer cancellationCounter;
	private LocalDate firstCancellation;
	private LocalDate endDateForRecording;
	
	public PurchaseCancellationHistory(String userId) {
		super();
		this.userId = userId;
		this.cancellationCounter = 1;
		this.firstCancellation = LocalDate.now();
		this.endDateForRecording = LocalDate.now().plusMonths(1);
	}

	public PurchaseCancellationHistory(String id, String userId, Integer cancellationCounter,
			LocalDate firstCancellation, LocalDate endDateForRecording) {
		super();
		this.id = id;
		this.userId = userId;
		this.cancellationCounter = cancellationCounter;
		this.firstCancellation = firstCancellation;
		this.endDateForRecording = endDateForRecording;
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getUserId() {
		return userId;
	}



	public void setUserId(String userId) {
		this.userId = userId;
	}



	public Integer getCancellationCounter() {
		return cancellationCounter;
	}



	public void setCancellationCounter(Integer cancellationCounter) {
		this.cancellationCounter = cancellationCounter;
	}



	public LocalDate getFirstCancellation() {
		return firstCancellation;
	}



	public void setFirstCancellation(LocalDate firstCancellation) {
		this.firstCancellation = firstCancellation;
	}



	public LocalDate getEndDateForRecording() {
		return endDateForRecording;
	}



	public void setEndDateForRecording(LocalDate endDateForRecording) {
		this.endDateForRecording = endDateForRecording;
	}



	public PurchaseCancellationHistory() {
		// TODO Auto-generated constructor stub
	}

}
