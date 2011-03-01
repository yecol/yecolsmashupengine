package act.mashup.util.Similarity;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import act.mashup.global.KV;

public class LoadDict {

	Map<String, String> sogou = new HashMap<String, String>();
	//��Ƶ��Ϣ
	Map<String, String> sogou1 = new HashMap<String, String>();
	//������Ϣ
	private static boolean instanceFlag = false;

	private LoadDict() {
		try {
			//�ֵ��Լ���Ƶ��Ϣ�������ѹ��������ʿ�
			File dict = new File(KV.sogouDictPath);
			FileInputStream fis = new FileInputStream(dict);
			BufferedInputStream bis = new BufferedInputStream(fis);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));

			//int time=0;
			boolean flag=true;
			while (flag) {
				String line = br.readLine();
				if(line==null){
					flag=false;
					break;
				}
				/*
				if (time < 10) {
					System.out.println(line);
					time++;
				}
				*/
				String[] textArray = line.split("\t");
				//ȡ��Ƶ��Ϣ
				this.sogou.put(textArray[0], textArray[1]);
				//ȡ������Ϣ
				//this.sogou1.put(textArray[0], textArray[2]);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static LoadDict getDict() {
		if (!instanceFlag)
			return new LoadDict();
		else
			return null;
	}

	public Map<String, String> getSogou() {
		return sogou;
	}

	public void setSogou(Map<String, String> sogou) {
		this.sogou = sogou;
	}

	public Map<String, String> getSogou1() {
		return sogou1;
	}

	public void setSogou1(Map<String, String> sogou1) {
		this.sogou1 = sogou1;
	}

	//�����������ڲ��ԡ�
	public static void main(String[] args) {
		LoadDict ld = LoadDict.getDict();
		Map<String, String> map = ld.getSogou();
		System.out.println("END");

	}

}
