package net.javajuan.springboot.entities;

public class Frequency {
	
	    private String category;
	    private int frequency;
	    private Invitation invitation;

	    // Constructor for category and frequency
	    public Frequency(String category, int frequency) {
	        this.setCategory(category);
	        this.setFrequency(frequency);
	    }

	    // Constructor for invitation and frequency
	    public Frequency(Invitation invitation, int frequency) {
	        this.setInvitation(invitation);
	        this.setFrequency(frequency);
	    }

		public String getCategory() {
			return category;
		}

		public void setCategory(String category) {
			this.category = category;
		}

		public int getFrequency() {
			return frequency;
		}

		public void setFrequency(int frequency) {
			this.frequency = frequency;
		}

		public Invitation getInvitation() {
			return invitation;
		}

		public void setInvitation(Invitation invitation) {
			this.invitation = invitation;
		}
}


