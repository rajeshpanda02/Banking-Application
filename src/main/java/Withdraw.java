

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
 * Servlet implementation class Withdraw
 */
@WebServlet("/Withdraw")
public class Withdraw extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Withdraw() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		int accountno = Integer.parseInt(request.getParameter("accno"));
		String name = request.getParameter("name");
		String password = request.getParameter("psw");
		int withdraw = Integer.parseInt(request.getParameter("amount"));
		
		int balance,newbalance;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","raj","welcome");
			PreparedStatement ps = con.prepareStatement("select * from bank where accountno=? and name=? and password=?");
		
			ps.setInt(1,accountno);
			ps.setString(2, name);
			ps.setString(3, password);
			
			ResultSet rs  = ps.executeQuery();
			
			if (rs.next()) {
				balance = rs.getInt(4);
				if (withdraw>balance) {
					out.print("Your Have Insufficeant Balance");
				}
				newbalance = balance - withdraw;
				
				PreparedStatement ps2 = con.prepareStatement("update bank set amount=? where accountno=?");
				ps2.setInt(1, newbalance);
				ps2.setInt(2, accountno);
				
				ps2.executeUpdate();
				
				out.print("<b> My Balance is  : </b>"+ balance +"<br>");
				out.print("<b> My Withdraw Amount is  : </b>"+ withdraw +"<br>");
				out.print("<b> After Withdraw My Balance is  : </b>"+ newbalance +"<br>");
			}
			else {
				out.print("<b> Enter The Correct Details : </b>"+"<br>");
				}
			con.close();
			}
			catch (Exception e) {
			out.print(e);
		}
	}

}
