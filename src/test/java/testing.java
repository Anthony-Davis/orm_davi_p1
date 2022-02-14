import com.revature.exceptions.MissingAnnotationException;
import com.revature.repositories.Repository;
import com.revature.services.StatementCreator;
import com.revature.util.ReflectInfo;
import com.revature.util.TestModel;

import java.sql.SQLException;

import org.junit.Test;
import static org.junit.Assert.*;


public class testing {
	
	Repository<Object> repo = new Repository<>();
	TestModel tm = new TestModel();
	StatementCreator<TestModel> sc = new StatementCreator<>();
	
	
	@Test
	public void testInitialTable() throws MissingAnnotationException {
		TestModel tm = new TestModel();
		assertEquals( "create table if not exists test_model ( id serial primary key,  name VARCHAR(20), some_num DOUBLE precision, other_num INTEGER);" ,sc.buildInitialTable(tm));
	}
	
	@Test
	public void testCreateSC() {
		TestModel tm = new TestModel();
		assertEquals("insert into test_model (id,name,some_num,other_num) values (default,?,?,?) returning *;", sc.create(tm));
	}
	
	@Test
	public void testRepoAdd() throws SQLException, MissingAnnotationException {
		TestModel tm = new TestModel();
		repo.initializeTable(tm);
		
		tm.setId(1);
		tm.setName("name1");
		tm.setSomeNum(1.1);
		tm.setOtherNum(11);
		assertNotNull(repo.addItem(tm));
	}
	
	@Test
	public void testDeleteSC() {
		assertEquals( "delete from test_model where id = ?;", sc.delete(tm) );
	}
	
	@Test
	public void testRepoDelete() throws SQLException, MissingAnnotationException {
		repo.initializeTable(tm);
		
		tm.setId(1);
		tm.setName("name1");
		tm.setSomeNum(1.1);
		tm.setOtherNum(11);
		repo.addItem(tm);
		
		repo.deleteItem(1, tm);
		assertNull(repo.getItem(1, tm));
	}
	
	@Test
	public void testReadSC() {
		assertEquals("select * from test_model where id = ?;", sc.read(tm));
	}
	
	@Test
	public void testRepoGet() throws SQLException, MissingAnnotationException {
		repo.initializeTable(tm);
		
		tm.setId(1);
		tm.setName("name1");
		tm.setSomeNum(1.1);
		tm.setOtherNum(11);
		repo.addItem(tm);
		
		tm.setId(2);
		tm.setName("name2");
		tm.setSomeNum(2.2);
		tm.setOtherNum(22);
		repo.addItem(tm);
		assertNotNull(repo.getItem(2, tm));
	}
	
	@Test
	public void testReadAllSC() {
		assertEquals("select * from test_model;", sc.readAll(tm));
	}
	
	@Test
	public void testRepoGetAll() throws InstantiationException, IllegalAccessException, SQLException, MissingAnnotationException {
		repo.initializeTable(tm);
		tm.setId(1);
		tm.setName("name1");
		tm.setSomeNum(1.1);
		tm.setOtherNum(11);
		repo.addItem(tm);
		
		tm.setId(2);
		tm.setName("name2");
		tm.setSomeNum(2.2);
		tm.setOtherNum(22);
		repo.addItem(tm);
		assertNotNull(repo.getAll(tm));
	}
	
	@Test
	public void testUpdateSC() throws IllegalAccessException {
		TestModel up = new TestModel();
		up.setId(1);
		up.setName("Anthony");
		up.setSomeNum(5.55);
		up.setOtherNum(55);
		assertEquals("update test_model set name= 'Anthony',some_num= 5.55,other_num= 55 where id= 1;", sc.update(1, up));
	}
	
	@Test
	public void testRepoUpdate() throws IllegalAccessException, SQLException, MissingAnnotationException {
		
		repo.initializeTable(tm);
		tm.setId(1);
		tm.setName("name1");
		tm.setSomeNum(1.1);
		tm.setOtherNum(11);
		repo.addItem(tm);
		
		TestModel up = new TestModel();
		up.setId(1);
		up.setName("Anthony");
		up.setSomeNum(5.55);
		up.setOtherNum(55);
		
		repo.update(up.getId(), up);
		System.out.println( repo.getItem(1, tm) );
		assertEquals( "Anthony" , ReflectInfo.getFieldValues(repo.getItem(1, tm))[1].toString() );
	}
	
	
}
