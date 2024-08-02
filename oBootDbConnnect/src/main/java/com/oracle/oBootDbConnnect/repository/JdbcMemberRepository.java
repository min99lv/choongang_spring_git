package com.oracle.oBootDbConnnect.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import com.oracle.oBootDbConnnect.domain.Member7;

//@Repository
public class JdbcMemberRepository implements MemberRepository {
	// jdbc사용
	private final DataSource dataSource;

	@Autowired
	public JdbcMemberRepository(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	private Connection getConnection() {
		// jdbc : mapping
		return DataSourceUtils.getConnection(dataSource);
	}

	@Override
	public Member7 save(Member7 member7) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "insert into member7(id,name) values(member_seq.nextval,?)";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member7.getName());
			pstmt.executeUpdate();
			return member7;
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		} finally {
			close(conn,pstmt,rs);
		}

	}

	@Override
	public List<Member7> findAll() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select id,name from member7";
		List<Member7> member7 = new ArrayList<>();

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Member7 md = new Member7();
				md.setId(rs.getLong("id"));
				md.setName(rs.getString("name"));
				member7.add(md);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			close(conn,pstmt,rs);
		}

		return member7;
	}

	private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			// TODO: handle exception
		}
		try {
			if (pstmt != null)
				pstmt.close();
		} catch (SQLException e) {
			// TODO: handle exception
		}
		try {
			if (conn != null)
				close(conn);
		} catch (SQLException e) {
			// TODO: handle exception
		}
	}

	private void close(Connection conn) throws SQLException {
		DataSourceUtils.releaseConnection(conn, dataSource);
	}

}
