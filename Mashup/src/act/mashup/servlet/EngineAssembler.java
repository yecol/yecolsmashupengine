package act.mashup.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jdom.output.XMLOutputter;

import act.mashup.global.EngineManager;

/**
 * Servlet implementation class EngineAssembler
 */

public class EngineAssembler extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public EngineAssembler() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("yes.posted.");
		
			
			//在session中查找或者设置一个新的桩
			EngineManager emgr=new EngineManager();
			emgr.BuildEngine(request.getParameter("xml"));
			
			
			
			response.setContentType("text/xml");
			//指定响应类型
			response.setCharacterEncoding("UTF-8");
	        PrintWriter out = response.getWriter();
			//获得书写器
	        
	        /*
	        String rlt=null;
	        rlt=request.getParameter("rlt");
	        try{
	        	out.println(emgr.GetResult().toString());
	        }
	        */
	        
	        //out.println(emgr.GetResult(5).toString());
	        XMLOutputter outputter = new XMLOutputter();
	        
	        String outputIdString = request.getParameter("outputId");
	        int outputIdInt;
	        if(outputIdString != null)
	        {
	        	outputIdInt = Integer.parseInt(outputIdString);
	        	outputter.output(emgr.GetResult(outputIdInt), out);
	        }
	        else
	        {
	        	outputter.output(emgr.GetResult(), out);
	        }     
	         
	        out.flush();   
	        out.close();   
			

		
	}

}
