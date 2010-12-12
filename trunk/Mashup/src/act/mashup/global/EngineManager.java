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

	// ˽����ʱ����
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
		satisfyStatus.put(0, true);// ��ʼ����
		engineNodes = new ArrayList<EngineNode>();
		satisfyingNodes = new ArrayList<EngineNode>();
		results = new HashMap<Integer, Result>();

	}

	// ��XML��ʽ��������ִ������
	public void BuildEngine(String xmlString) {
		// TODO Auto-generated method stub

		// ��ʱ����
		Element figure;
		Element ioput;
		List ioputs;
		String classId;
		Integer id;
		Map<String, String> paras;
		ArrayList inputs;
		ArrayList outputs;

		// ���ַ�����ʼ����XML�ĵ�
		StringReader read = new StringReader(xmlString);
		InputSource source = new InputSource(read);
		SAXBuilder sb = new SAXBuilder();

		// �����ռ�
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
			// ��ÿһ��figure���ж��󻯲���
			for (Iterator iter = figures.iterator(); iter.hasNext();) {
				figure = (Element) iter.next();
				System.out.println(figure.toString());

				// �������
				classId = figure.getAttributeValue("classid", gf);
				id = Integer.parseInt(figure.getAttributeValue("id", gf));
				satisfyStatus.put(id, false);
				doneStatus.put(id, false);

				// ��ò���
				ioputs = figure.getChild("LogicalAttribute", gf).getChildren(
						"Para", gf);
				paras = new HashMap<String, String>();
				for (Iterator it = ioputs.iterator(); it.hasNext();) {
					ioput = (Element) it.next();
					paras.put(ioput.getAttributeValue("name"),
							ioput.getTextTrim());
					System.out.println(ioput.getTextTrim());
				}

				// �������
				ioputs = figure.getChild("interfaces", gf)
						.getChild("inputs", gf).getChildren("input", gf);
				inputs = new ArrayList<Integer>();
				for (Iterator it = ioputs.iterator(); it.hasNext();) {
					ioput = (Element) it.next();
					inputs.add(Integer.parseInt(ioput.getText()));
				}

				// ������
				ioputs = figure.getChild("interfaces", gf)
						.getChild("outputs", gf).getChildren("output", gf);
				outputs = new ArrayList<Integer>();
				for (Iterator it = ioputs.iterator(); it.hasNext();) {
					ioput = (Element) it.next();
					outputs.add(Integer.parseInt(ioput.getText()));
				}

				// ����
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

	// ���п�ִ������
	public void RunSequence() {
		while (doneStatus.containsValue(false) == true) {
			// ���¿�ִ������
			QueryRunnable();

			// �̼߳����������
			ArrayList<EngineThread> threads = new ArrayList<EngineThread>();
			for (EngineNode en : satisfyingNodes) {

				EngineThread t = new EngineThread(en, results);
				threads.add(t);
				t.start();
			}

			// ���߳����к��״̬����
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

	// ��result������XML
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

	// ��ѯ�����еĽڵ�
	private ArrayList QueryRunnable() {
		this.satisfyingNodes.clear();
		for (EngineNode node : engineNodes) {
			if (node.isSatisfy(satisfyStatus, doneStatus))
				this.satisfyingNodes.add(node);
		}
		return this.satisfyingNodes;
	}

}
