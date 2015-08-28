package au.edu.uq.smartass.web;

import java.util.List;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import au.edu.uq.smartass.web.jdbc.TemplatesDao;

public class StartPoint {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext app = new ClassPathXmlApplicationContext("app.xml");
		//BeanFactory app = new XmlBeanFactory(new FileSystemResource("app.xml"));
		
		TemplatesDao templ = (TemplatesDao) app.getBean("templatesDao");
		List<TemplatesItemModel> templates = templ.select();
		for(TemplatesItemModel t : templates)
			System.out.println(t.getName());
	}

}
