/*
 * 	<em:figure gf:classid="RssFeed" gf:id="1">
		<gf:LogicalAttribute>
		</gf:LogicalAttribute>
		<gf:interfaces>
			<gf:inputs>
				<gf:input>0</gf:input>
			</gf:inputs>
			<gf:outputs>
				<gf:output>3</gf:output>
			</gf:outputs>
		</gf:interfaces>
	</em:figure>
 */

package act.mashup.util;

import java.util.ArrayList;
import java.util.Map;

public class EngineNode {
	private Integer id;
	private String classId;
	private Map<String,String> paras;
	private final ArrayList<Integer> inputs;
	private final ArrayList<Integer> outputs;
	private boolean satisfy;

	// ���췽��
	public EngineNode(Integer id, String classId, Map<String,String> paras,
			ArrayList<Integer> inputs, ArrayList<Integer> outputs) {
		super();
		this.id = id;
		this.classId = classId;
		this.paras = paras;
		this.inputs = inputs;
		this.outputs = outputs;
		this.satisfy = false;
	}

	// ����������񣬲���ÿ�β�ѯ�������ǰ���¼��
	public boolean isSatisfy(Map<Integer, Integer> status, Map<Integer, Boolean> doneStatus) {
		if(doneStatus.get(this.id)==true)
			return false;
		else{
			CheckSatisfy(status);
			return satisfy;
		}		
	}
	
	public Integer getId(){
		return this.id;
	}

	public String getClassId() {
		return this.classId;
	}

	public Map<String,String> getParas() {
		System.out.println("this is para in en:" + this.paras.toString());
		return this.paras;
	}
	
	public ArrayList<Integer> getInputs(){
		return this.inputs;
	}
	
	public ArrayList<Integer> getOutputs(){
		return this.outputs;
	}

	// ��֤�����Ƿ������ض���ʽ��������ʱ�׳��쳣������
	private boolean CheckLogicalAttribute() {
		// TODO
		return true;
	}

	// ���¼���Ƿ�������������
	private void CheckSatisfy(Map<Integer, Integer> stateArray) {
		// ����Ѿ������򷵻�
		System.out.println(stateArray.toString());
		if (satisfy == true)
			return;
		else {
			boolean singleSatisfy = true;
			for (Integer i : this.inputs) {
				if (stateArray.get(i) == 0) {
					singleSatisfy = false;
					break;
				}
			}// ÿһ�����������������������������ֵΪ��
			if (singleSatisfy == true)
				satisfy = true;
		}
	}

	// ����
	public String toString() {
		return "id=" + id + " classId=" + classId + " paras="
				+ paras.toString() + " inputs=" + inputs.toString()
				+ " outputs=" + outputs.toString();
	}

}
