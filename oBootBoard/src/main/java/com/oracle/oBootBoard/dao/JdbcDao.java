package com.oracle.oBootBoard.dao;

import static org.hamcrest.CoreMatchers.nullValue;

import java.lang.reflect.Executable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import com.oracle.oBootBoard.dto.BDto;

//@Repository
public class JdbcDao implements Bdao {
	// jdbc사용
	private final DataSource dataSource;

	@Autowired
	public JdbcDao(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	private Connection getcConnection() {
		return DataSourceUtils.getConnection(dataSource);
	}

	@Override
	public ArrayList<BDto> boardList() {
		ArrayList<BDto> bList = new ArrayList<>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "select * from mvc_board order by bGroup desc, bStep asc";
		System.out.println("BDao boardList start...");

		try {
			connection = getcConnection();
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				BDto bdto = new BDto();
				bdto.setbId(resultSet.getInt("bid"));
				bdto.setbName(resultSet.getString("bname"));
				bdto.setbTitle(resultSet.getString("btitle"));
				bdto.setbContent(resultSet.getString("bcontent"));
				bdto.setbDate(resultSet.getTimestamp("bdate"));
				bdto.setbHit(resultSet.getInt("bhit"));
				bdto.setbGroup(resultSet.getInt("bgroup"));
				bdto.setbStep(resultSet.getInt("bstep"));
				bdto.setbIndent(resultSet.getInt("bindent"));
				// 게터 세터 방식이 아닌 생성자로 get
				// int bIndent = resultSet.getInt("bIndent");
				// Bdto dto = new BDto(bID,
				// bName,bTitle,bContect,bDate,bHit,bGroup,bStep,bIndent);
				bList.add(bdto);

			}

		} catch (SQLException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
				if (preparedStatement != null)
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return bList;
	}

	@Override
	public BDto contentView(String strId) {
		BDto bdto = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "select * from mvc_board where bid=?";
		upHit(strId);

		try {
			connection = getcConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, Integer.parseInt(strId));
			resultSet = preparedStatement.executeQuery();
			// primary key이므로 if사용
			if (resultSet.next()) {
				int bId = resultSet.getInt("bid");
				String bName = resultSet.getString("bname");
				String bTitle = resultSet.getString("btitle");
				String bContent = resultSet.getString("bcontent");
				Timestamp bDate = resultSet.getTimestamp("bdate");
				int bHit = resultSet.getInt("bhit");
				int bGroup = resultSet.getInt("bGroup");
				int bStep = resultSet.getInt("bStep");
				int bIndent = resultSet.getInt("bIndent");
				// 생성자로 넣어준다. = 모든 항목을 set한 것
				bdto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
				if (preparedStatement != null)
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return bdto;
	}

	public void upHit(String bId) {
		BDto bdto = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sql = "update mvc_board set bHit=bHit+1 where bid=?";

		try {
			connection = getcConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, bId);
			preparedStatement.executeUpdate();

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Override
	public BDto modify(String bId, String bName, String bTitle, String bContent) {
		BDto board = new BDto();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sql = "update mvc_board set bName=?, bTitle=?,bContent=? where bId=?";

		try {
			connection = getcConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, bName);
			preparedStatement.setString(2, bTitle);
			preparedStatement.setString(3, bContent);
			preparedStatement.setInt(4, Integer.parseInt(bId));
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return board;
	}

	@Override
	public void modify3(BDto bDto) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sql = "update mvc_board set bName=?, bTitle=?,bContent=? where bId=?";

		try {
			connection = getcConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, bDto.getbName());
			preparedStatement.setString(2, bDto.getbTitle());
			preparedStatement.setString(3, bDto.getbContent());
			preparedStatement.setInt(4, bDto.getbId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Override
	public void delete(String bId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sql = "delete from mvc_board where bid=?";
		System.out.println("DAO delete bId->" + bId);
		try {
			connection = getcConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, Integer.parseInt(bId));
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Override
	public BDto write(String bName, String bTitle, String bContent) {
		BDto bDto = new BDto();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sql = "insert into mvc_board(bid,bName, bTitle, bcontent, bGroup, bhit, bstep, bindent, bdate) values(mvc_board_seq.nextval,?,?,?,mvc_board_seq.currval,0,0,0,sysdate)";
		System.out.println("JdbcDao write " + bName);
		try {
			connection = getcConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, bName);
			preparedStatement.setString(2, bTitle);
			preparedStatement.setString(3, bContent);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return bDto;
	}

	@Override
	public BDto reply_view(String strbId) {
		BDto bdto = new BDto();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "select * from mvc_board where bid=?";

		try {
			connection = getcConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, Integer.parseInt(strbId));
			resultSet = preparedStatement.executeQuery();
			// primary key이므로 if사용
			// bdto = contentView(strbId);
			// bdto = write(bdto.getbName(), bdto.getbTitle(), bdto.getbContent());
			// String sql2 = "update mvc_board set bid=?,bstep=bstep+1,bindent=bindent+1";
			if (resultSet.next()) {
				int bId = resultSet.getInt("bid");
				String bName = resultSet.getString("bname");
				String bTitle = resultSet.getString("btitle");
				String bContent = resultSet.getString("bcontent");
				Timestamp bDate = resultSet.getTimestamp("bdate");
				int bHit = resultSet.getInt("bhit");
				int bGroup = resultSet.getInt("bGroup");
				int bStep = resultSet.getInt("bStep");
				int bIndent = resultSet.getInt("bIndent");
				// 생성자로 넣어준다. = 모든 항목을 set한 것
				bdto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
				if (preparedStatement != null)
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return bdto;
	}

	@Override
	public BDto reply(String bId, String bName, String bTitle, String bContent, String bGroup, String bStep,
			String bIndent) {
		BDto bDto = new BDto();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		String sql1 = "select nvl(max(bid),0) from mvc_board";
		String sql2 = "insert into mvc_board(bid,bName, bTitle, bcontent, bGroup, bhit, bstep, bindent, bdate) values(mvc_board_seq.nextval,?,?,?,mvc_board_seq.currval,0,0,0,sysdate)";

		try {
			connection = getcConnection();
			preparedStatement = connection.prepareStatement(sql1);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				bDto.setbId(resultSet.getInt(1));
			}
			connection.close();
			preparedStatement.close();
			resultSet.close();

			// 답변값 로직
			replyShape(bGroup, bStep);

			// 답변 입력
			connection = getcConnection();
			preparedStatement = connection.prepareStatement(sql2);
			preparedStatement.executeUpdate();
			preparedStatement.setString(1, bName);
			preparedStatement.setString(2, bTitle);
			preparedStatement.setString(3, bContent);
			preparedStatement.executeUpdate();
			
			
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return bDto;
	}

	private void replyShape(String bGroup, String bStep) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sql = "update mvc_borad set bStep = bStep+1, where bGroup=? and bStep > ?";

		try {
			BDto bDto = new BDto();
			connection = getcConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, bGroup);
			preparedStatement.setString(2, bStep);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
