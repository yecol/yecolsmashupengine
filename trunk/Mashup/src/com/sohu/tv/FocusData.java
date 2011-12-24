package com.sohu.tv;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sohu.tv.util.SoUtil;

/**
 * �����������ݶ���
 * 
 * @author chengxinsun
 */
public class FocusData {
	/**
	 * ��Ƶid
	 */
	private Long vid;
	
	/**
	 * ר��ID
	 */
	private Long sid;
	/**
	 * �����ͼ
	 */
	private String ver_big_pic;
	
	/**
	 * ����Сͼ
	 */
	private String ver_small_pic;
	
	/**
	 * URL
	 */
	private String tv_url;
	
	/**
	 * ����
	 */
	private String tv_name;
	
	/**
	 * ��Ա
	 */
	private String actor;
	
	/**
	 * ���
	 */
	private String tv_desc;
	
	/**
	 * ����
	 */
	private String mtype;
	
	/**
	 * ����
	 */
	private String director;
	
	/**
	 * ����ʱ��
	 */
	private String tv_update;
	
	/**
	 * ͨ��JSONObject ���� FocusData
	 * @param json
	 * @return
	 * @throws SoException
	 */
	public static List<FocusData> constructFocusDatas(JSONObject json)
			throws SoException {
		try {
			Integer status = json.getInt("status");

			if (status != 200) {
				throw new SoException("Error return result! status :" + status);
			}
			FocusData data = new FocusData();

			JSONArray list = json.getJSONObject("data").getJSONArray("videos");
			Integer size = list.length();
			List<FocusData> datas = new ArrayList<FocusData>(size);
			for (Integer i = 0; i < size; i++) {
				Class<FocusData> ownerClass = (Class<FocusData>) data.getClass();
				FocusData obj = (FocusData) ownerClass.newInstance();// ʵ������

				FocusData result_v = (FocusData) SoUtil.createObjectByJSONObject(
						ownerClass, obj, list.getJSONObject(i));
				datas.add(result_v);
			}
			return datas;
		} catch (Exception jsone) {
			throw new SoException(jsone);
		}
	}

	public Long getVid() {
		return vid;
	}

	public void setVid(Long vid) {
		this.vid = vid;
	}

	public String getVer_big_pic() {
		return ver_big_pic;
	}

	public void setVer_big_pic(String ver_big_pic) {
		this.ver_big_pic = ver_big_pic;
	}

	public String getVer_small_pic() {
		return ver_small_pic;
	}

	public void setVer_small_pic(String ver_small_pic) {
		this.ver_small_pic = ver_small_pic;
	}

	public String getTv_url() {
		return tv_url;
	}

	public void setTv_url(String tv_url) {
		this.tv_url = tv_url;
	}

	public String getTv_name() {
		return tv_name;
	}

	public void setTv_name(String tv_name) {
		this.tv_name = tv_name;
	}

	public String getActor() {
		return actor;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}

	public String getTv_desc() {
		return tv_desc;
	}

	public void setTv_desc(String tv_desc) {
		this.tv_desc = tv_desc;
	}

	public String getMtype() {
		return mtype;
	}

	public void setMtype(String mtype) {
		this.mtype = mtype;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getTv_update() {
		return tv_update;
	}

	public void setTv_update(String tv_update) {
		this.tv_update = tv_update;
	}

	public Long getSid() {
		return sid;
	}

	public void setSid(Long sid) {
		this.sid = sid;
	}
}
