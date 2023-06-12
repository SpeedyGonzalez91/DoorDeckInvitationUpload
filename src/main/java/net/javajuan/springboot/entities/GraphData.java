package net.javajuan.springboot.entities;

public class GraphData {

	    private String category;
	    private int value;

	    public GraphData(String category, int value) {
	        this.category = category;
	        this.value = value;
	    }

	    // Getters and setters

	    public String getCategory() {
	        return category;
	    }

	    public void setCategory(String category) {
	        this.category = category;
	    }

	    public int getValue() {
	        return value;
	    }

	    public void setValue(int value) {
	        this.value = value;
	    }
	}


