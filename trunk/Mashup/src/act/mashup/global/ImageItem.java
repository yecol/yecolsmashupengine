package act.mashup.global;

public class ImageItem {
	private String ImageTitle;
	private String ImageThumbURL;
	private String ImageURL;
	private String ImageRefererURL;

	public ImageItem() {
		super();
	}

	public String getImageTitle() {
		return ImageTitle;
	}

	public void setImageTitle(String imageTitle) {
		ImageTitle = imageTitle;
	}

	public String getImageThumbURL() {
		return ImageThumbURL;
	}

	public void setImageThumbURL(String imageThumbURL) {
		ImageThumbURL = imageThumbURL;
	}

	public String getImageURL() {
		return ImageURL;
	}

	public void setImageURL(String imageURL) {
		ImageURL = imageURL;
	}

	public String getImageRefererURL() {
		return ImageRefererURL;
	}

	public void setImageRefererURL(String imageRefererURL) {
		ImageRefererURL = imageRefererURL;
	}

	@Override
	public String toString() {
		return "ImageItem [ImageTitle=" + ImageTitle + ", ImageThumbURL=" + ImageThumbURL + ", ImageURL=" + ImageURL + ", ImageRefererURL=" + ImageRefererURL + "]";
	}

}
