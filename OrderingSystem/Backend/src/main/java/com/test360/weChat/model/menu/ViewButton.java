package com.test360.weChat.model.menu;

public class ViewButton extends Button {
	private String url;

	public ViewButton(String name, String type, Button[] sub_button, String url) {
		super(name, type, sub_button);
		this.url = url;
	}

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
