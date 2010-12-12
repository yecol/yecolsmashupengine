package act.mashup.global;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import act.mashup.util.EngineNode;
import act.mashup.util.EngineThread;
import act.mashup.util.Item;
import act.mashup.util.Result;

/**
 * Session Bean implementation class EngineManager
 */

public class EngineManager {

	/**
	 *  
	 */
	private static final long serialVersionUID = 1L;
	public Map<Integer, Boolean> satisfyStatus;
	public Map<Integer, Boolean> doneStatus;
	private ArrayList<EngineNode> engineNodes;
	public Map<Integer, Result> results;

	// 私有临时变量
	private ArrayList<EngineNode> satisfyingNodes;
	private Method prepareMethod;
	private Method runMethod;

	/**
	 * Default constructor.
	 */
	public EngineManager() {
		// TODO Auto-generated constructor stub
		satisfyStatus = new HashMap<Integer, Boolean>();
		doneStatus = new HashMap<Integer, Boolean>();
		satisfyStatus.put(0, true);// 初始条件
		engineNodes = new ArrayList<EngineNode>();
		satisfyingNodes = new ArrayList<EngineNode>();
		results = new HashMap<Integer, Result>();

	}

	// 从XML格式参数构造执行数组
	public void BuildEngine(String xmlString) {
		// TODO Auto-generated method stub

		// 临时变量
		Element figure;
		Element ioput;
		List ioputs;
		String classId;
		Integer id;
		Map<String, String> paras;
		ArrayList inputs;
		ArrayList outputs;

		// 从字符串开始解析XML文档
		StringReader read = new StringReader(xmlString);
		InputSource source = new InputSource(read);
		SAXBuilder sb = new SAXBuilder();

		// 命名空间
		Namespace em = Namespace.getNamespace("em",
				"http://www.example.org/EngineModel");
		Namespace gf = Namespace.getNamespace("gf",
				"http://www.example.org/GeneralFigure");

		try {
			Document doc = sb.build(source);
			Element rootElement = doc.getRootElement();
			System.out.println("parse begin");
			System.out.println(rootElement.toString());
			List figures = rootElement.getChildren("figure", em);
			// 对每一个figure进行对象化操作
			for (Iterator iter = figures.iterator(); iter.hasNext();) {
				figure = (Element) iter.next();
				System.out.println(figure.toString());

				// 获得属性
				classId = figure.getAttributeValue("classid", gf);
				id = Integer.parseInt(figure.getAttributeValue("id", gf));
				satisfyStatus.put(id, false);
				doneStatus.put(id, false);

				// 获得参数
				ioputs = figure.getChild("LogicalAttribute", gf).getChildren(
						"Para", gf);
				paras = new HashMap<String, String>();
				for (Iterator it = ioputs.iterator(); it.hasNext();) {
					ioput = (Element) it.next();
					paras.put(ioput.getAttributeValue("name"),
							ioput.getTextTrim());
					System.out.println(ioput.getTextTrim());
				}

				// 获得输入
				ioputs = figure.getChild("interfaces", gf)
						.getChild("inputs", gf).getChildren("input", gf);
				inputs = new ArrayList<Integer>();
				for (Iterator it = ioputs.iterator(); it.hasNext();) {
					ioput = (Element) it.next();
					inputs.add(Integer.parseInt(ioput.getText()));
				}

				// 获得输出
				ioputs = figure.getChild("interfaces", gf)
						.getChild("outputs", gf).getChildren("output", gf);
				outputs = new ArrayList<Integer>();
				for (Iterator it = ioputs.iterator(); it.hasNext();) {
					ioput = (Element) it.next();
					outputs.add(Integer.parseInt(ioput.getText()));
				}

				// 对象化
				engineNodes.add(new EngineNode(id, classId, paras, inputs,
						outputs));

			}

		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			System.out.println("==========YecolsError0003==========");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("==========YecolsError0004==========");
			e.printStackTrace();
		}
		for (EngineNode en : engineNodes) {
			System.out.println("enTest:" + en.toString());
		}

		RunSequence();

	}

	// 运行可执行序列
	public void RunSequence() {
		while (doneStatus.containsValue(false) == true) {
			// 更新可执行数组
			QueryRunnable();

			// 线程级地运行组件
			ArrayList<EngineThread> threads = new ArrayList<EngineThread>();
			for (EngineNode en : satisfyingNodes) {

				EngineThread t = new EngineThread(en, results);
				threads.add(t);
				t.start();
			}

			// 将线程运行后的状态更新
			for (EngineThread t : threads) {
				try {
					t.join();
					t.updateStatus(satisfyStatus, doneStatus);

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		System.out.println(results.toString());
		System.out.println("ALL MODULES EXECUTE OVER");
	}

	// 从result中生成XML
	public Document GetResult(Integer i) {
		Document outDoc = new Document();
		Element rootElement = new Element("root");
		if (this.results.get(i).GetStatus() == false) {
			rootElement.setAttribute("status", "false");
			Element errormsg=new Element("errormsg");
			errormsg.setText(this.results.get(i).GetErrorMsg());
			rootElement.addContent(errormsg);
		} else {
			rootElement.setAttribute("status", "true");
			List<Item> _itemList = this.results.get(i).GetResultList();
			Element _el = null;
			for (Item _item : _itemList) {
				_el = new Element("item");
				for (Iterator<String> it = _item.getKeys().iterator(); it
						.hasNext();) {
					String _name = it.next();
					Element _ele = new Element(_name);
					_ele.setText(_item.getValue(_name));
					_el.addContent(_ele);
				}
				rootElement.addContent(_el);
			}
		}
		outDoc.setRootElement(rootElement);
		return outDoc;
	}

	// 查询可运行的节点
	private ArrayList QueryRunnable() {
		this.satisfyingNodes.clear();
		for (EngineNode node : engineNodes) {
			if (node.isSatisfy(satisfyStatus, doneStatus))
				this.satisfyingNodes.add(node);
		}
		return this.satisfyingNodes;
	}

}
