package todo.API;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		/*System.out.println("Hibernate tutorial start");

		Session session = HibernateSessionFactory.getSessionFactory().openSession();
		listsDAOImpl listDao = new listsDAOImpl();
		listDao.setSession(session);

		Transaction tx = session.beginTransaction();

		List<ListsEntity> lists = listDao.findAll();
		for (ListsEntity list : lists) {
			System.out.println(list);
		}*/

		SpringApplication.run(ApiApplication.class, args);
	}

}

