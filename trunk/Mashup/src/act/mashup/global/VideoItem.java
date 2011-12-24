package act.mashup.global;

public class VideoItem {
	private String VideoTitle;
	private String VideoDesc;
	private String VideoDuration;
	private String VideoThumbURL;
	private String VideoLinkURL;
	private String VideoURL;

	public VideoItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getVideoTitle() {
		return VideoTitle;
	}

	public void setVideoTitle(String videoTitle) {
		VideoTitle = videoTitle;
	}

	public String getVideoDesc() {
		return VideoDesc;
	}

	public void setVideoDesc(String videoDesc) {
		VideoDesc = videoDesc;
	}

	public String getVideoDuration() {
		return VideoDuration;
	}

	public void setVideoDuration(String videoDuration) {
		VideoDuration = videoDuration;
	}

	public String getVideoThumbURL() {
		return VideoThumbURL;
	}

	public void setVideoThumbURL(String videoThumbURL) {
		VideoThumbURL = videoThumbURL;
	}

	public String getVideoLinkURL() {
		return VideoLinkURL;
	}

	public void setVideoLinkURL(String videoLinkURL) {
		VideoLinkURL = videoLinkURL;
	}

	public String getVideoURL() {
		return VideoURL;
	}

	public void setVideoURL(String videoURL) {
		VideoURL = videoURL;
	}

	@Override
	public String toString() {
		return "VideoItem [VideoTitle=" + VideoTitle + ", VideoDesc=" + VideoDesc + ", VideoDuration=" + VideoDuration + ", VideoThumbURL=" + VideoThumbURL + ", VideoLinkURL="
				+ VideoLinkURL + ", VideoURL=" + VideoURL + "]";
	}

}
