

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
 * Servlet implementation class Transfer
 */
@WebServlet("/Transfer")
public class Transfer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Transfer() {
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
		int otheracc = Integer.parseInt(request.getParameter("racc"));
		int tamount = Integer.parseInt(request.getParameter("amount"));
		int balance , balancee , tbal, attbal;	
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","raj","welcome");
			
			PreparedStatement ps = con.prepareStatement("select * from bank where accountno=? and name=? and password=?");
			ps.setInt(1,accountno);
			ps.setString(2, name);
			ps.setString(3, password);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				balance=rs.getInt(4);
				balancee = balance - tamount;
				
				out.print("<b> Your Balance Is : </b>"+ balance +"<br>");
				out.print("<b> Your Transfer Amount Is :</b>"+ tamount + "<br>");
				
				PreparedStatement ps2 = con.prepareStatement("update bank set amount=? where accountno=?");
				ps2.setInt(1,balancee);
				ps2.setInt(2,accountno);
				ps2.executeUpdate();
				out.print("<b> After  Transfer Your Balance Is :</b>"+ balancee + "<br>");
			
			
			
				PreparedStatement ps3 = con.prepareStatement("select * from bank where accountno=?");
				ps3.setInt(1,otheracc);
				ResultSet rs3= ps3.executeQuery();
					if (rs3.next()) {
						tbal = rs3.getInt(2);
						attbal = tbal + tamount;
						out.print("<b> Your Target A/c Balance Is :</b>"+ tbal + "<br>");
						PreparedStatement ps4 = con.prepareStatement("update bank set amount=? where accountno=?");
						ps4.setInt(1,attbal);
						ps4.setInt(2,otheracc);
						ps4.executeUpdate();
						out.print("<b> After Transfer Target A/c Amount Is :</b>"+ attbal + "<br>");
				}
			}
			
		} catch (Exception e) {
			out.print(e);
		}
	}

}
