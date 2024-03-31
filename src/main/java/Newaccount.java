

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Newaccount
 */
@WebServlet("/Newaccount")
public class Newaccount extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Newaccount() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String accountno = request.getParameter("accno");
		String name = request.getParameter("name");
		String password = request.getParameter("psw");
		String amount = request.getParameter("amount");
		String address = request.getParameter("add");
		String mobile = request.getParameter("mobile");
		
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","raj","welcome");
			PreparedStatement ps = con.prepareStatement("insert into bank values(?,?,?,?,?,?)");
			ps.setString(1, accountno);
			ps.setString(2,name);
			ps.setString(3,password);
			ps.setString(4,amount);
			ps.setString(5,address);
			ps.setString(6,mobile);
			
			int i = ps.executeUpdate();
			out.print(i+" record inserted successfully...........");
			
		} catch (Exception e) {
			out.print(e);
		}
	}

}
