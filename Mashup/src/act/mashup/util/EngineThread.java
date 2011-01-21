package act.mashup.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
		// 获得模块名
		String interfaceName = "act.mashup.module." + en.getClassId();

		// ArrayList<String> paras = en.getParas();
		// Integer paraSize = paras.size();

		try {

			// 反射构造类的实例
			Class c = Class.forName(interfaceName);
			Object obj = c.newInstance();

			// 运行
			Method runMethod = c.getDeclaredMethod("run", EngineNode.class,
					Map.class);
			runMethod.invoke(obj, en, results);

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

	public void updateStatus(Map<Integer, Integer> satisfyStatus,
			Map<Integer, Boolean> doneStatus) {
		/*
		 * for(Integer i: en.getOutputs()){ satisfyStatus.put(i, true); }
		 * satisfyStatus.put(this.en.getOutputs(), true);
		 */
		doneStatus.put(this.en.getId(), true);

		System.out.println("test results:"+this.results.toString());
		
		Iterator<Integer> iterator = en.getOutputs().iterator();
		while (iterator.hasNext()) {
			Integer curIndex = iterator.next();
			// 完全成功时的状态更新(Succeed)
			if (this.results.get(curIndex).GetStatus() == 1)
				satisfyStatus.put(curIndex, 1);
			// 部分成功时的更新(Particial Succeed)
			else if (this.results.get(curIndex).GetStatus() == 2)
				satisfyStatus.put(curIndex, 2);
		}

	}

}
