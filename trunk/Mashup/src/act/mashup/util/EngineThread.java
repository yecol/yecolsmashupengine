package act.mashup.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EngineThread extends Thread {

	private Map<Integer, Result> results;
	private EngineNode en;

	public EngineThread(EngineNode en,Map<Integer, Result> results) {
		super();
		this.results = results;
		this.en = en;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		// String jndiName=nameString+"/local";
		//���ģ����
		String interfaceName = "act.mashup.module." + en.getClassId();
		
		//ArrayList<String> paras = en.getParas();
		//Integer paraSize = paras.size();
		
		try {
			
			//���乹�����ʵ��
			Class c = Class.forName(interfaceName);
			Object obj = c.newInstance();
		
			/*
			//���ݴ�������ĸ������д��Σ����е�׼���׶�
			if(paraSize==0){
				Method prepareMethod = c.getDeclaredMethod("Prepare");
				prepareMethod.invoke(obj);
			}else if(paraSize==1){
				Method prepareMethod = c.getDeclaredMethod("Prepare",String.class);
				prepareMethod.invoke(obj,paras.get(0));
			}
			*/
			
			//����
			Method runMethod = c.getDeclaredMethod("run",EngineNode.class,Map.class);
			runMethod.invoke(obj, en,results);
			//List resultList=(List)runMethod.invoke(obj);
			//results.put(en.getId(),resultList);
			//System.out.println(resultList.toString());

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("==========YecolsError0001==========");
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			System.out.println("==========YecolsError0006==========");
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			System.out.println("==========YecolsError0002==========");
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			System.out.println("==========YecolsError0007==========");
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			System.out.println("==========YecolsError0009==========");
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			System.out.println("==========YecolsError0010==========");
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("SINGLE MODULE EXECUTE OVER");
	}
	
	public void updateStatus(Map<Integer,Boolean> satisfyStatus, Map<Integer, Boolean> doneStatus){
		satisfyStatus.put(this.en.getId(), true);
		doneStatus.put(this.en.getId(), true);
	}

}
