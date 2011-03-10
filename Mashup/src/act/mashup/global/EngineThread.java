package act.mashup.global;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import act.mashup.util.Log;

public class EngineThread extends Thread {

	private Map<Integer, Result> results;
	private EngineNode en;

	public EngineThread(EngineNode en, Map<Integer, Result> results) {
		super();
		this.results = results;
		this.en = en;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		// String jndiName=nameString+"/local";
		// ���ģ����
		String interfaceName = "act.mashup.module." + en.getClassId();

		try {

			// ���乹�����ʵ��
			Class c = Class.forName(interfaceName);
			Object obj = c.newInstance();

			// ����
			Method runMethod = c.getDeclaredMethod("run", EngineNode.class, Map.class);
			runMethod.invoke(obj, en, results);

		} catch (ClassNotFoundException e) {
			Log.logger.fatal(e);
		} catch (SecurityException e) {
			Log.logger.fatal(e);
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			Log.logger.fatal(e);
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			Log.logger.fatal(e);
		} catch (IllegalArgumentException e) {
			Log.logger.fatal(e);
		} catch (InstantiationException e) {
			Log.logger.fatal(e);
		} catch (InvocationTargetException e) {
			Log.logger.fatal(e);
		}

		Log.logger.info("Sigle module " + en.getClassId() + " executed.");
	}

	public void updateStatus(Map<Integer, Integer> satisfyStatus, Map<Integer, Boolean> doneStatus) {
		/*
		 * for(Integer i: en.getOutputs()){ satisfyStatus.put(i, true); }
		 * satisfyStatus.put(this.en.getOutputs(), true);
		 */
		doneStatus.put(this.en.getId(), true);

		//System.out.println("test results:" + this.results.toString());

		Iterator<Integer> iterator = en.getOutputs().iterator();
		while (iterator.hasNext()) {
			Integer curIndex = iterator.next();
			// ��ȫ�ɹ�ʱ��״̬����(Succeed)
			if (this.results.get(curIndex).GetStatus() == 1)
				satisfyStatus.put(curIndex, 1);
			// ���ֳɹ�ʱ�ĸ���(Particial Succeed)
			else if (this.results.get(curIndex).GetStatus() == 2)
				satisfyStatus.put(curIndex, 2);
		}

	}

}
