package com.sdgcrm.application.email;

public class ScheduleEmailResponse {
	 private boolean success;
	    private String jobId;
	    private String jobGroup;
	    private String message;
	    private String template;

	    public ScheduleEmailResponse(boolean success, String message) {
	        this.success = success;
	        this.message = message;
	    }

	    public ScheduleEmailResponse(boolean success, String jobId, String jobGroup, String message) {
	        this.success = success;
	        this.jobId = jobId;
	        this.jobGroup = jobGroup;
	        this.message = message;
	    }

		public boolean isSuccess() {
			return success;
		}

		public void setSuccess(boolean success) {
			this.success = success;
		}

		public String getJobId() {
			return jobId;
		}

		public void setJobId(String jobId) {
			this.jobId = jobId;
		}

		public String getJobGroup() {
			return jobGroup;
		}

		public void setJobGroup(String jobGroup) {
			this.jobGroup = jobGroup;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public String getTemplate() {
			return template;
		}

		public void setTemplate(String template) {
			this.template = template;
		}
	    
		
		
	    

}
