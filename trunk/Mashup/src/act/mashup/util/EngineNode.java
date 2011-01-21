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

	// 构造方法
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

	// 返回满足与否，并在每次查询满足与否前重新检测
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

	// 验证输入是否满足特定格式，不符合时抛出异常待处理
	private boolean CheckLogicalAttribute() {
		// TODO
		return true;
	}

	// 重新检测是否满足运行条件
	private void CheckSatisfy(Map<Integer, Integer> stateArray) {
		// 如果已经满足则返回
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
			}// 每一个输入条件都满足才置满足条件的值为真
			if (singleSatisfy == true)
				satisfy = true;
		}
	}

	// 测试
	public String toString() {
		return "id=" + id + " classId=" + classId + " paras="
				+ paras.toString() + " inputs=" + inputs.toString()
				+ " outputs=" + outputs.toString();
	}

}
