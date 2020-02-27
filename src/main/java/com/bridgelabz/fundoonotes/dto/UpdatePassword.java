package com.bridgelabz.fundoonotes.dto;

public class UpdatePassword {
	
	
		private String emailId;
		private String password;
		private String confirmPassword;

		
		public String getEmailId() {
			return emailId;
		}

		
		public void setEmailId(String emailId) {
			this.emailId = emailId;
		}

		/**
		 * Getter method to fetch password
		 * 
		 * @return String
		 */
		public String getPassword() {
			return password;
		}

	
		public void setPassword(String password) {
			this.password = password;
		}

	
		public String getConfirmPassword() {
			return confirmPassword;
		}

	
		public void setConfirmPassword(String confirmPassword) {
			this.confirmPassword = confirmPassword;
		}

		@Override
		public String toString() {
			return "UpdatePassword [emailId=" + emailId + ", password=" + password + ", confirmPassword=" + confirmPassword
					+ "]";
		}


}
