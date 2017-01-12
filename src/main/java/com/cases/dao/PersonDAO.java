package com.cases.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.cases.vo.Person;

@Repository
public class PersonDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate = null;
	public Person queryPerson(int id){
		String sql = "select id, name, age from persons where id=?";
		final List<Person> list = new ArrayList<Person>();
		jdbcTemplate.query(sql,new Integer[]{id}, new RowCallbackHandler(){
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				Person per = new Person();
				per.id = rs.getInt("id");
				per.name = rs.getString("name");
				per.age = rs.getInt("age");
				list.add(per);
			}
		});
		return list.size() > 0 ? list.get(0) : null;
	}
	public List<Person> queryAllPerson(){
		String sql = "select id, name, age from persons";
		final List<Person> list = new ArrayList<Person>();
		jdbcTemplate.query(sql, new RowCallbackHandler(){
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				Person per = new Person();
				per.id = rs.getInt("id");
				per.name = rs.getString("name");
				per.age = rs.getInt("age");
				list.add(per);
			}
		});
		return list;
	}
	public int insertPerson(final Person person){
		final String sql = "insert into persons (name , age) values(?, ?)";
		KeyHolder kh = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
				ps.setString(1, person.name);
				ps.setInt(2, person.age);
				return ps;
			}
		}, kh);
		int key = kh.getKey().intValue();
		person.id = key;
		return key;
	}
	public void updatePerson(final Person person){
		String sql = "update persons (name, age) values(?, ?) where id = ?";
		jdbcTemplate.update(sql, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, person.name);
				ps.setInt(2, person.age);
				ps.setInt(3, person.id);
			}
		});
	}
	public void deletePersonByID(final int id){
		String sql = "delete from persons where id = ?";
		jdbcTemplate.update(sql, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, id);
			}
		});
	}
}
