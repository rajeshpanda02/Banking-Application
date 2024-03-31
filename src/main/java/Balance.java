

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Balance
 */
@WebServlet("/Balance")
public class Balance extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Balance() {
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
		
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","raj","welcome");
			PreparedStatement ps = con.prepareStatement("select amount from bank where accountno=? and name=? and password=?");
		
			ps.setString(1, accountno);
			ps.setString(2,name);
			ps.setString(3,password);
		
			
			ResultSet rs =ps.executeQuery();
			if (rs.next()) {
				for (int i = 1; i < 3 ; i++) {
					out.print("your account balance is : "+ rs.getString(i) );
				}
			}
			con.close();
		} catch (Exception e) {
			//out.print(e);
		
		}   
	}

}
