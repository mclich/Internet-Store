package com.mclich.epamproject.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mclich.epamproject.Constants;
import com.mclich.epamproject.dao.mysql.OrderDAO;
import com.mclich.epamproject.dao.mysql.UserDAO;
import com.mclich.epamproject.entity.User;
import com.mclich.epamproject.entity.Order.Status;
import com.mclich.epamproject.entity.User.Role;
import com.mclich.epamproject.exception.NoRightsException;

@WebServlet("/user-orders")
@SuppressWarnings("serial")
public class UserOrdersServlet extends HttpServlet
{
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
		User currUser=(User)req.getSession().getAttribute("user");
		if(currUser!=null&&(currUser.getLogin().equals(req.getParameter("login"))||currUser.getRoles().contains(Role.ADMIN)))
		{
			if(req.getParameter("order")!=null)
			{
				int orderId=Integer.parseInt(req.getParameter("order"));
				OrderDAO oDAO=OrderDAO.getInstance();
				Status status=oDAO.getStatus(orderId);
				switch(status)
				{
					case PROCESSED:
					{
						oDAO.updateStatus(orderId, Status.PAID);
						break;
					}
					case PAID:
					{
						oDAO.updateStatus(orderId, Status.CANCELLED);
						break;
					}
					case CANCELLED:
					{
						oDAO.updateStatus(orderId, Status.PROCESSED);
						break;
					}
				}
				res.sendRedirect("user-orders?login="+req.getParameter("login")+"&back="+req.getParameter("back"));
			}
			else
			{
				User linkedUser=UserDAO.getInstance().get(req.getParameter("login"));
				req.setAttribute("orders", UserDAO.getInstance().get(req.getParameter("login")).getOrders());
				req.setAttribute("fullName", linkedUser.getFirstName()+" "+linkedUser.getLastName());
				req.getRequestDispatcher("content/pages/user-orders.jsp").forward(req, res);
			}
		}
		else Constants.errorRedirect(req, res, new NoRightsException(), false);
	}
}
