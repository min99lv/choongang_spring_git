package com.oracle.oBootBoardWarPom.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.sql.DataSource;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import com.oracle.oBootBoardWarPom.dto.BDto;



//@Repository
public class JdbcDao implements BDao {

	//JDBC사용하기 위해 코딩
	private final DataSource dataSource;
	public JdbcDao(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	private Connection getConnection() {
		return DataSourceUtils.getConnection(dataSource);
	}
	
	
	
	@Override
	public ArrayList<BDto> boardList() {
		ArrayList<BDto> bList = new ArrayList<BDto>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		String sql = "select * from MVC_BOARD order by bGroup desc, bStep asc";
		
		System.out.println("BDao boardList start...");
		
		try {
			
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			
				while (resultSet.next()) {
					//BDto bdto = new BDto();
					int bId = resultSet.getInt("bId");
					String bName = resultSet.getString("bName");
					String bTitle = resultSet.getString("bTitle");
					//System.out.println(bTitle);
					String bContent = resultSet.getString("bContent");
					Timestamp bDate = resultSet.getTimestamp("bDate");
					int bHit = resultSet.getInt("bHit");
					int bGroup = resultSet.getInt("bGroup");
					int bStep = resultSet.getInt("bStep");
					int bIndent = resultSet.getInt("bIndent");
					
					BDto dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
					
					bList.add(dto);
				}
			
		} catch (Exception e) {
			System.out.println("JdbcDao ArrayList e.getMessage() >> "+e.getMessage());
		}
		finally {
			try {
				if(resultSet != null) {
					resultSet.close();
				}
				if(preparedStatement != null) {
					preparedStatement.close();
				}
				if(connection != null) {
					connection.close();
				}
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
			}
		}
		return bList;
	}

	@Override
	public BDto contentView(String strId) {
		
		BDto bDto = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		String sql = "select * from MVC_BOARD where bId = ?";
		
		//조회할때마다 조회수 상승
		upHit(strId);
		
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, Integer.parseInt(strId));
			resultSet = preparedStatement.executeQuery();
			
			System.out.println("JdbcDao contentView sql >> "+sql);
			
			//프라이머리키를 조건으로 sql문에 걸어주었으므로 while문을 사용하지 않는다
			if(resultSet.next()) {
				int bId = resultSet.getInt("bId");
				System.out.println("JdbcDao contentView bId >> "+bId);
				String bName = resultSet.getString("bName");
				System.out.println("JdbcDao contentView bName >> "+bName);
				String bTitle = resultSet.getString("bTitle");
				String bContent = resultSet.getString("bContent");
				Timestamp bDate = resultSet.getTimestamp("bDate");
				int bHit = resultSet.getInt("bHit");
				int bGroup = resultSet.getInt("bGroup");
				int bStep = resultSet.getInt("bStep");
				int bIndent = resultSet.getInt("bIndent");
				
				//생성자로 세팅하는것이다 set....과 같은 의미임
				bDto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
			}
			
		} catch (Exception e) {
			System.out.println("JdbcDao contentView e.getMessage() >> "+e.getMessage());
		}
		finally {
			try {
				try {
					if(resultSet != null) {
						resultSet.close();
					}
					if(preparedStatement != null) {
						preparedStatement.close();
					}
					if(connection != null) {
						connection.close();
					}
				} catch (Exception e2) {
					System.out.println(e2.getMessage());
				}
			} catch (Exception e2) {
				e2.getMessage();
			}
		}
		
		return bDto;
	}
	
	public void upHit(String bId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String sql = "update mvc_board set bHit = bHit+1 where bid= ?";
		
		//System.out.println("BDao upHit start...");
		
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, Integer.parseInt(bId));
			
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			System.out.println("JdbcDao upHit e.getMessage() >> "+e.getMessage());
		}
		finally {
			try {
				try {
					if(preparedStatement != null) {
						preparedStatement.close();
					}
					if(connection != null) {
						connection.close();
					}
				} catch (Exception e2) {
					System.out.println(e2.getMessage());
				}
			} catch (Exception e2) {
				e2.getMessage();
			}
		}
	}
	
	/*
	@Override
	public void modify(String bId, String bName, String bTitle, String bContent) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sql = "update mvc_board set bName=?, bTitle=?, bContent=? where bId = ?";
		
		BDto bdto = new BDto();
		
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, bName);
			preparedStatement.setString(2, bTitle);
			preparedStatement.setString(3, bContent);
			preparedStatement.setInt(4, Integer.parseInt(bId));
			
			
			
		} catch (Exception e) {
			System.out.println("JdbcDao bModifyCmd e.getMessage()"+e.getMessage());
		}
		finally {
			try {
				try {
					if(preparedStatement != null) {
						preparedStatement.close();
					}
					if(connection != null) {
						connection.close();
					}
				} catch (Exception e2) {
					System.out.println(e2.getMessage());
				}
			} catch (Exception e2) {
				e2.getMessage();
			}
		}
	}
	*/

	@Override
	public void modify(BDto bdto) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sql = "update mvc_board set bName = ?, bTitle = ?, bContent = ? where bId = ?";
		
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, bdto.getbName());
			preparedStatement.setString(2, bdto.getbTitle());
			preparedStatement.setString(3, bdto.getbContent());
			preparedStatement.setInt(4, bdto.getbId());
			
			preparedStatement.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("JdbcDao bModifyCmd e.getMessage()"+e.getMessage());
		}
		finally {
			try {
				try {
					if(preparedStatement != null) {
						preparedStatement.close();
					}
					if(connection != null) {
						connection.close();
					}
				} catch (Exception e2) {
					System.out.println(e2.getMessage());
				}
			} catch (Exception e2) {
				e2.getMessage();
			}
		}
	}
	
	public void delete(String bId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sql = "delete from mvc_board where bId = ?";
		
		try {
			
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, Integer.parseInt(bId));
			
			preparedStatement.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("JdbcDao delete e.getMessage()"+e.getMessage());
		}
		finally {
			try {
				try {
					if(preparedStatement != null) {
						preparedStatement.close();
					}
					if(connection != null) {
						connection.close();
					}
				} catch (Exception e2) {
					System.out.println(e2.getMessage());
				}
			} catch (Exception e2) {
				e2.getMessage();
			}
		}
	}

	@Override
	public void write(String bName, String bTitle, String bContent) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sql = "insert into mvc_board (bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent)"
				+ "values (mvc_board_seq.nextval,?,?,?,sysdate,0,mvc_board_seq.currval,0,0)";
		
		try {
			//44	김가영캐스터	일기예보 서울	"서울 첫 폭염경보, 내일도 전국 가마솥 더위"	24/08/02	1	44	0	0
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			
			//preparedStatement.setString(1, "mvc_board_seq.nextval");
			preparedStatement.setString(1, bName);
			preparedStatement.setString(2, bTitle);
			preparedStatement.setString(3, bContent);
			//preparedStatement.setString(5, "sysdate");
			//preparedStatement.setInt(6, 0);
			//mvc_board_seq.nextval랑 똑같은 번호로 맞추기 위해 currval 
			//preparedStatement.setString(7, "mvc_board_seq.currval");
			//preparedStatement.setInt(8, 0);
			//preparedStatement.setInt(9, 0);
			
			preparedStatement.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("JdbcDao write e.getMessage()"+e.getMessage());
		}
		finally {
			try {
				try {
					if(preparedStatement != null) {
						preparedStatement.close();
					}
					if(connection != null) {
						connection.close();
					}
				} catch (Exception e2) {
					System.out.println(e2.getMessage());
				}
			} catch (Exception e2) {
				e2.getMessage();
			}
		}
	}

	@Override
	public BDto reply_view(int strbId) {
		
		BDto dto = new BDto();
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "select * from mvc_board where bId=?";
		
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, strbId);
			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				int bId = resultSet.getInt("bId");
				String bName = resultSet.getString("bName");
				String bTitle = resultSet.getString("bTitle");
				String bContent = resultSet.getString("bContent");
				Timestamp bDate = resultSet.getTimestamp("bDate");
				int bHit = resultSet.getInt("bHit");
				int bGroup = resultSet.getInt("bGroup");
				int bStep = resultSet.getInt("bStep");
				int bIndent = resultSet.getInt("bIndent");
					
				dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
				
			}
			
		} catch (Exception e) {
			System.out.println("JdbcDao reply_view e.getMessage()"+e.getMessage());
		}
		finally {
			try {
				try {
					if(preparedStatement != null) {
						preparedStatement.close();
					}
					if(connection != null) {
						connection.close();
					}
				} catch (Exception e2) {
					System.out.println(e2.getMessage());
				}
			} catch (Exception e2) {
				e2.getMessage();
			}
		}
		return dto;
	}

	@Override
	public void reply(int bId, String bName, String bTitle, String bContent, int bGroup, int bStep, int bIndent) {
		replyShape(bGroup, bStep);
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		//String sql1 = "select * from mvc_board where bId=?";
		String sql2 = "insert into mvc_board (bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent)"
								+ "values (mvc_board_seq.nextval,?,?,?,sysdate,0,?,?,?)";
		
		try {
			//BDto bdto = new BDto();
			connection = getConnection();
			//preparedStatement = connection.prepareStatement(sql1);
			//preparedStatement.setInt(1, bId);
			
			//값을 가져왔기 때문에 쓸모없는 코드
			//if( bId != 1) {
			//bdto.setbId(bId);
			//bdto.setbName(bName);
			//bdto.setbTitle(bTitle);
			//bdto.setbContent(bContent);
			//bdto.setbGroup(bGroup);
			//bdto.setbStep(bStep);
			//bdto.setbIndent(bIndent);
				
			//preparedStatement.executeUpdate();
				

				System.out.println("******sql2 end");
				System.out.println("******bId*****"+bId);
			//}
			
			preparedStatement = connection.prepareStatement(sql2);
			preparedStatement.setString(1, bName);
			preparedStatement.setString(2, bTitle);
			preparedStatement.setString(3, bContent);
			preparedStatement.setInt(4, bGroup);
			preparedStatement.setInt(5, bStep+1);
			preparedStatement.setInt(6, bIndent+1);
			
			preparedStatement.executeUpdate();
			System.out.println("******sql3 end");
			
		} catch (Exception e) {
			System.out.println("JdbcDao reply e.getMessage()"+e.getMessage());
		}
		finally {
			try {
				try {
					if(preparedStatement != null) {
						preparedStatement.close();
					}
					if(connection != null) {
						connection.close();
					}
				} catch (Exception e2) {
					System.out.println(e2.getMessage());
				}
			} catch (Exception e2) {
				e2.getMessage();
			}
		}
	}

	//답변이 달려있는 원글에 또 댓글을 단다면 기존에 있던 댓글을 다음 번호로 밀어내고 새로 들어온 번호가 1번으로 들어간다 
	//해당 로직은 private으로 만들어야 한다 다른곳에서는 사용하지 않기 때문에 프라이빗으로 만든다 (같은 dao에서 쓰는 경우는 걍 프라이빗으로 만든다)
	private void replyShape (int bGroup, int bStep) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sql = "update mvc_board set bstep = bstep+1 where bgroup = ? and bstep > ?";
		try {
			
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, bGroup);
			System.out.println("bGroup >> "+bGroup);
			preparedStatement.setInt(2, bStep);
			System.out.println("bStep >> "+bStep);
			
			preparedStatement.executeUpdate();

			System.out.println("-----------replyShape end");
			System.out.println("bStep >> "+bStep);
			
		} catch (Exception e) {
			System.out.println("JdbcDao replyShape e.getMessage()"+e.getMessage());
		}
		finally {
			try {
				try {
					if(preparedStatement != null) {
						preparedStatement.close();
					}
					if(connection != null) {
						connection.close();
					}
				} catch (Exception e2) {
					System.out.println(e2.getMessage());
				}
			} catch (Exception e2) {
				e2.getMessage();
			}
		}
		//값을 리턴하기 위해 사용하는 코드가 아니라 값을 세팅하기 위해 사용
		//return bStep;
	}
	
}
