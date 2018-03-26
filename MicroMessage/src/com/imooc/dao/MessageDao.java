package com.imooc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.imooc.bean.Message;

/**
 *��message����ص����ݿ����
 */

public class MessageDao {
	
	/**
	 *���ݲ�ѯ������ѯ��Ϣ�б�
	 */
	public List<Message> queryMessageList(String command,String description){
		
		List<Message> messageList=new ArrayList<Message>();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/micro_message","root","123456");
			StringBuilder sql=new StringBuilder("select ID,COMMAND,DESCRIPTION,CONTENT from message where 1=1");
			List<String> paramlist=new ArrayList<String>();
			if(command!=null&&!"".equals(command.trim())){
				sql.append(" and COMMAND=?");
				paramlist.add(command);
			} 
			if(description!=null&&!"".equals(description.trim())){
				sql.append(" and DESCRIPTION like '%' ? '%'");
				paramlist.add(description);
			}
			java.sql.PreparedStatement statement =conn.prepareStatement(sql.toString());
			for(int i=0;i<paramlist.size();i++){
				statement.setNString(i+1,paramlist.get(i));
			}
			//����������1������sql�����󣬲�һ����sql������Ҳ������ִ��sql������﷨����
			//�磺ResultSet rs=statement.executeQuery(sql);����sql
			ResultSet rs=statement.executeQuery();
			
			while(rs.next()){
				Message message=new Message();
				messageList.add(message);
				message.setId(rs.getString("ID"));
				message.setCommand(rs.getString("COMMAND"));
				message.setDescription(rs.getString("DESCRIPTION"));
				message.setContent(rs.getString("CONTENT"));
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return messageList;
	}

}
