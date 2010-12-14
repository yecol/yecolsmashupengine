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
		
			
			//��session�в��һ�������һ���µ�׮
			EngineManager emgr=new EngineManager();
			emgr.BuildEngine(request.getParameter("xml"));
			
			
			
			response.setContentType("text/xml");
			//ָ����Ӧ����
			response.setCharacterEncoding("UTF-8");
	        PrintWriter out = response.getWriter();
			//�����д��
	        
	        /*
	        String rlt=null;
	        rlt=request.getParameter("rlt");
	        try{
	        	out.println(emgr.GetResult().toString());
	        }
	        */
	        
	        //out.println(emgr.GetResult(5).toString());
	        XMLOutputter outputter = new XMLOutputter();   
	        outputter.output(emgr.GetResult(6), out);   
	        out.flush();   
	        out.close();   
			
		
		/*try {
			InitialContext ctx=new InitialContext();
			
			//��session�в��һ�������һ���µ�׮
			HttpSession session=request.getSession(true);
			EngineManagerLocal emgr=(EngineManagerLocal)session.getAttribute("engineManager");
			if(emgr==null){
				emgr=(EngineManagerLocal)ctx.lookup("EngineManager/local");
				session.setAttribute("engineManager", emgr);
			}
			
			emgr.BuildEngine(request.getParameter("xml"));
			
			
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
		
		
	}

}
